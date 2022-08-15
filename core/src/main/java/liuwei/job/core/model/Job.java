package liuwei.job.core.model;

import liuwei.job.core.model.emnu.JobExecutionRouteStrategy;
import liuwei.job.core.model.emnu.JobTriggerStatus;
import liuwei.job.core.model.emnu.JobType;
import liuwei.job.core.model.emnu.ScheduleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_job")
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String jobName;

    private long jobGroupID;

    private JobType jobType;

    private String jobDesc;

    private long userID;

    private JobExecutionRouteStrategy routeStrategy;

    private ScheduleType scheduleType;
    private String scheduleConf;

    private long executorGroupID;
    private String executorParam;
    private int failedRetryCount;

    private int maxFailedRetryCount;

    private JobTriggerStatus triggerStatus;

    private Timestamp lastTriggerTime;
    private Timestamp nextTriggerTime;
    private Timestamp createTime;

    private Timestamp updateTime;

    private long createUser;

    private long updateUser;
}
