package liuwei.job.core.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterJobRO {

    private String jobGroup;
    private String job;
    private String clazzName;
    private String cronExpStr;
    private String allowConcurrent;
}
