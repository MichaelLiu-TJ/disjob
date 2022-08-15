package liuwei.job.core.model.emnu;

/**
 * Job schedule type
 * <p>
 * onetime: the job only success run once time
 * cron: schedule the job by cron expression
 * </p>
 */
public enum ScheduleType {
    ONETIME, CRON
}
