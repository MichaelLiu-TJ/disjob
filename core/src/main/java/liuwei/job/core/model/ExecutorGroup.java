package liuwei.job.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_executor_group")
@Getter
@Setter
public class ExecutorGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "groupName")
    private String groupName;

    @Column(name = "description")
    private String description;
    private Timestamp createTime;

    private Timestamp updateTime;

    private long createUser;

    private long updateUser;
}
