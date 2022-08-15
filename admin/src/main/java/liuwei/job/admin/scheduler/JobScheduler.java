package liuwei.job.admin.scheduler;

import liuwei.job.core.model.JobGroup;
import liuwei.job.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class JobScheduler {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ExecutorClientRepository executorClientRepository;

    @Autowired
    private ExecutorGroupExecutorsRepository executorGroupExecutorsRepository;

    @Autowired
    private ExecutorGroupRepository executorGroupRepository;

    @Autowired
    private JobGroupRepository jobGroupRepository;


    @PostConstruct
    private void postConstruct() {
        new Thread(this::scheduleJob).start();
    }

    private void scheduleJob() {
        ExecutorService scheduleJobService = Executors.newFixedThreadPool(10);

        Iterable<JobGroup> jobGroupList = jobGroupRepository.findAll();
        jobGroupList.forEach(new Consumer<JobGroup>() {
            @Override
            public void accept(JobGroup jobGroup) {

            }
        });

    }

}
