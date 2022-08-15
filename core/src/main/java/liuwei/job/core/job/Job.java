package liuwei.job.core.job;

/**
 * Job interface
 *
 * @author liuwei
 */
public interface Job {

    void execute(JobExecutionContext context);
}
