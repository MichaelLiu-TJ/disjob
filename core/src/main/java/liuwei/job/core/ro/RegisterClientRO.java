package liuwei.job.core.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterClientRO {
    private String ip;

    private int httpPort;
    private int jmxPort;
    private int rpcPort;
}
