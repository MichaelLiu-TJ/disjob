package liuwei.job.core.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_executor_client_job")
@Getter
@Setter
public class ExecutorClientJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long jobId;

    private long executorClientId;
    private Timestamp createTime;

    private Timestamp updateTime;

    private long createUser;

    private long updateUser;
}
