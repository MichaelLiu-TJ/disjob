package liuwei.job.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_executor_group_executors")
@Getter
@Setter
public class ExecutorGroupExecutors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long executorGroupID;

    private long executorClientID;
    private Timestamp createTime;

    private Timestamp updateTime;

    private long createUser;

    private long updateUser;
}
