package liuwei.job.core.model;

import liuwei.job.core.model.emnu.FinishedStatus;
import liuwei.job.core.model.emnu.JobExecutionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_job_execution_detail")
@Getter
@Setter
public class JobExecutionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long jobId;

    private String jobExecutionName;

    private Timestamp startExecutionTime;
    private Timestamp endExecutionTime;

    private JobExecutionStatus executionStatus;
    private FinishedStatus finishedStatus;
    private Timestamp createTime;

    private Timestamp updateTime;

    private long createUser;

    private long updateUser;
}
