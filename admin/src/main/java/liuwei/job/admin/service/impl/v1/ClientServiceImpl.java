package liuwei.job.admin.service.impl.v1;

import liuwei.job.admin.service.ClientService;
import liuwei.job.core.common.StatusCode;
import liuwei.job.core.exception.RegisterJobException;
import liuwei.job.core.model.ExecutorClient;
import liuwei.job.core.model.ExecutorClientJob;
import liuwei.job.core.model.Job;
import liuwei.job.core.model.JobGroup;
import liuwei.job.core.model.emnu.JobExecutionRouteStrategy;
import liuwei.job.core.model.emnu.JobType;
import liuwei.job.core.model.emnu.ScheduleType;
import liuwei.job.core.ro.RegisterClientJobRO;
import liuwei.job.core.ro.RegisterClientRO;
import liuwei.job.core.ro.RegisterJobRO;
import liuwei.job.repository.ExecutorClientJobRepository;
import liuwei.job.repository.ExecutorClientRepository;
import liuwei.job.repository.JobGroupRepository;
import liuwei.job.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Consumer;

@Service
public class ClientServiceImpl implements ClientService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobGroupRepository jobGroupRepository;

    @Autowired
    private ExecutorClientRepository executorClientRepository;

    @Autowired
    private ExecutorClientJobRepository executorClientJobRepository;

    @Override
    public String registerClient(RegisterClientRO registerClientRO) {
        try {
            ExecutorClient executorClient = new ExecutorClient();

            executorClient.setHttpIP(registerClientRO.getIp());
            executorClient.setHttpPort(registerClientRO.getHttpPort());
            executorClient.setCreateTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
            executorClient.setCreateUser(1); // TODO in real product, user must be get from session.
            executorClient.setSerialString(UUID.randomUUID().toString());
            executorClientRepository.save(executorClient);

            return executorClient.getSerialString();
        } catch (Exception e) {
            logger.info("", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public String registerJavaJob(RegisterJobRO registerJobRO) throws RegisterJobException {
        try {
            long jobGroupId = updateJobGroup(registerJobRO);

            Job job = new Job();
            job.setJobName(registerJobRO.getJob());
            job.setJobType(JobType.JAVA);
            job.setScheduleType(ScheduleType.CRON);
            job.setScheduleConf(registerJobRO.getCronExpStr());
            job.setRouteStrategy(JobExecutionRouteStrategy.RANDOM);
            job.setCreateUser(1);// TODO in real product, user must be get from session.

            job.setJobGroupID(jobGroupId);
            jobRepository.save(job);
            return "Register JOB SUCCESS";
        } catch (Exception e) {
            logger.info("", e);
            throw new RegisterJobException(e);
        }
    }

    @Override
    public StatusCode registerClientJob(RegisterClientJobRO registerClientJobRO) {
        if (StringUtils.isEmpty(registerClientJobRO.getClientSerialString())) {
            return StatusCode.REGISTER_CLIENT_JOB_FAILED_NO_CLIENT_SERIAL;
        }

        List<ExecutorClient> clients = executorClientRepository.findBySerialString(registerClientJobRO.getClientSerialString());
        if (CollectionUtils.isEmpty(clients)) {
            return StatusCode.REGISTER_CLIENT_JOB_FAILED_NOT_FIND_CLIENT;
        }

        Optional<Job> job = jobRepository.findById(registerClientJobRO.getJobId());

        if (!job.isPresent()) {
            return StatusCode.REGISTER_CLIENT_JOB_FAILED_NOT_FIND_JOB;
        }

        List<ExecutorClientJob> executorClientJobs = new ArrayList<>(clients.size());
        clients.forEach(executorClient -> {
            ExecutorClientJob executorClientJob = new ExecutorClientJob();
            executorClientJob.setJobId(job.get().getId());
            executorClientJob.setExecutorClientId(executorClient.getId());
            executorClientJobs.add(executorClientJob);
        });

        executorClientJobRepository.saveAll(executorClientJobs);
        return StatusCode.SUCCESSFUL;
    }

    /**
     * @param registerJobRO
     * @return
     */
    private long updateJobGroup(RegisterJobRO registerJobRO) {
        List<JobGroup> jobGroups = jobGroupRepository.findByGroupName(registerJobRO.getJobGroup());
        JobGroup jobGroup = null;
        if (CollectionUtils.isEmpty(jobGroups)) {
            JobGroup newJobGroup = new JobGroup();
            newJobGroup.setGroupName(registerJobRO.getJobGroup());
            jobGroup = jobGroupRepository.save(newJobGroup);

        } else {
            jobGroup = jobGroups.get(0);
        }
        return jobGroup.getId();
    }

}
