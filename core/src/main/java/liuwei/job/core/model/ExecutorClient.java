package liuwei.job.core.model;


import liuwei.job.core.model.emnu.ExecutorClientStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_executor_client")
@Getter
@Setter
public class ExecutorClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String serialString;

    private String name;

    private String httpIP;
    private int httpPort;

    private String jmxAddress;

    private String rpcAddress;

    private ExecutorClientStatus healthCheckStatus;

    private int healthCheckFailedCount;

    private Timestamp lastNotAvailableTime;

    private Timestamp createTime;

    private Timestamp updateTime;

    private long createUser;

    private long updateUser;

}
