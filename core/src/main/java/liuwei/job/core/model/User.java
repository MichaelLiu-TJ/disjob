package liuwei.job.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_user")
@Getter
@Setter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String password;

    private Timestamp lastLoginTime;
    private int retryTimes;

    private Timestamp createTime;
    private Timestamp updateTime;
    private long createUser;
    private long updateUser;
}
