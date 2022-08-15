package liuwei.job.admin.service.impl.v1;

import liuwei.job.core.cron.CronExpression;
import liuwei.job.core.model.ExecutorClient;
import liuwei.job.core.model.emnu.ExecutorClientStatus;
import liuwei.job.repository.ExecutorClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ClientHealthCheckServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(ClientHealthCheckServiceImpl.class);

    @Autowired
    private ExecutorClientRepository executorClientRepository;

    @Autowired
    private RestTemplate healthCheckRestTemplate;

    @Value("${app.healthCheckCronExpression}")
    private String healthCheckCronExpression;

    @PostConstruct
    private void postConstruct() {
        new Thread(() -> {
            try {
                executorClientsHealthCheck();
            } catch (ParseException e) {
                logger.warn("Execute client health check failed", e);
            }
        }).start();
    }

    private void executorClientsHealthCheck() throws ParseException {
        ExecutorService healthCheckThreadPool = Executors.newFixedThreadPool(10);
        ScheduledExecutorService schedulerService = Executors.newSingleThreadScheduledExecutor();

        CronExpression healthCron;
        try {
            healthCron = new CronExpression(healthCheckCronExpression);
        } catch (ParseException e) {
            logger.info("parase cron exception failed", e);
            healthCron = new CronExpression("0/30 * * * * ?");
        }


        while (true) {
            Date nextTime = null;

            try {
                nextTime = healthCron.getTimeAfter(Calendar.getInstance().getTime());
            } catch (Exception e) {
                logger.info("Get next health check task schedule time failed");
            }
            if (nextTime != null) {
                try {
                    schedulerService.schedule(() -> {
                        try {
                            if (tryHealthCheckTaskLock()) {
                                List<ExecutorClient> executorClients = executorClientRepository.findByHealthCheckFailedCountLessThan(3);
                                if (!CollectionUtils.isEmpty(executorClients)) {
                                    List<Future<ExecutorClient>> futures = new ArrayList<>(executorClients.size());
                                    executorClients.forEach(executorClient -> futures.add(healthCheckThreadPool.submit(new ClientHealthCheckTask(executorClient))));

                                    futures.forEach(future -> {
                                        ExecutorClient executorClient = null;
                                        try {
                                            executorClient = future.get();

                                            switch (executorClient.getHealthCheckStatus()) {
                                                case AVAILABLE:
                                                    executorClientRepository.updateHealthCheckSuccess(executorClient.getHealthCheckStatus(),
                                                            executorClient.getId());
                                                    break;
                                                case NOT_AVAILABLE:
                                                    executorClientRepository.updateHealthCheckFailed(executorClient.getHealthCheckStatus(),
                                                            executorClient.getHealthCheckFailedCount(),
                                                            executorClient.getId());
                                                    break;
                                                default:
                                                    // DO nothing
                                            }
                                        } catch (Exception e) {
                                            if (executorClient != null) {
                                                logger.info("Health check failed", e);
                                            }
                                        }
                                    });
                                } else {
                                    logger.info("No executor client need to do health check.");
                                }
                            } else {
                                logger.info("try lock failed");
                            }
                        } catch (Exception e) {
                            logger.info("");
                        } finally {
                            releaseHealthCheckLock();
                        }
                        return null;
                    }, nextTime.getTime() - Calendar.getInstance().getTime().getTime(), TimeUnit.MILLISECONDS).get();
                } catch (Exception e) {
                    logger.info("Health", e);
                }
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    logger.info("Thread sleep interrupted", e);
                }
            }
        }
    }

    private boolean tryHealthCheckTaskLock() {
        // TODO try lock health check task, return true means lock success, else means failed, may other admin is doing health
        return true;
    }

    private void releaseHealthCheckLock() {
        // TODO release lock health check task
    }

    static class ClientHealthCheckTask implements Callable<ExecutorClient> {

        private ExecutorClient executorClient;

        public ClientHealthCheckTask(ExecutorClient executorClient) {
            this.executorClient = executorClient;
        }

        @Override
        public ExecutorClient call() {

            try {
                if (isClientHealth()) {
                    executorClient.setHealthCheckStatus(ExecutorClientStatus.AVAILABLE);
                    executorClient.setHealthCheckFailedCount(0);
                } else {
                    logger.info("Executor client IP:{} port:{} health check failed", executorClient.getHttpIP(), executorClient.getHttpPort());
                    setHealthCheckFailed();
                }
            } catch (Exception e) {
                logger.info("Executor client IP:{} port:{} health check failed", executorClient.getHttpIP(), executorClient.getHttpPort(), e);
                setHealthCheckFailed();
            }
            return executorClient;
        }

        private void setHealthCheckFailed() {
            executorClient.setHealthCheckStatus(ExecutorClientStatus.NOT_AVAILABLE);
            executorClient.setHealthCheckFailedCount(executorClient.getHealthCheckFailedCount() + 1);
            executorClient.setLastNotAvailableTime(new Timestamp(new Date().getTime()));
        }

        private boolean isClientHealth() {
            // TODO call client healthCheck interface. http code 200 means available , else means not available
            return true;
        }
    }

}
