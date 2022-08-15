package liuwei.job.core.cron;


import liuwei.job.core.cron.descriptor.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CronExpDescriptor {

    final private SecondsDescriptionBuilder secondsDescriptionBuilder;
    final private MinutesDescriptionBuilder minutesDescriptionBuilder;
    final private HoursDescriptionBuilder hoursDescriptionBuilder;
    final private DayOfMonthDescriptionBuilder dayOfMonthDescriptionBuilder;
    final private DayOfWeekDescriptionBuilder dayOfWeekDescriptionBuilder;
    final private MonthDescriptionBuilder monthDescriptionBuilder;

    public CronExpDescriptor() {
        secondsDescriptionBuilder = new SecondsDescriptionBuilder();
        minutesDescriptionBuilder = new MinutesDescriptionBuilder();
        hoursDescriptionBuilder = new HoursDescriptionBuilder();
        dayOfMonthDescriptionBuilder = new DayOfMonthDescriptionBuilder();
        dayOfWeekDescriptionBuilder = new DayOfWeekDescriptionBuilder();
        monthDescriptionBuilder = new MonthDescriptionBuilder();
    }

    public String getFullDescription(String expression) {
        String[] expressionParts;
        String description = "";
        try {
            expressionParts = ExpressionParser.parseExpression(expression);
            description = getSecondsDescription(expressionParts) + System.lineSeparator() +
                    getMinutesDescription(expressionParts) + System.lineSeparator() +
                    getHoursDescription(expressionParts) + System.lineSeparator() +
                    getDayOfMonthDescription(expressionParts) + System.lineSeparator() +
                    getMonthDescription(expressionParts) + System.lineSeparator() +
                    getDayOfWeekDescription(expressionParts) + System.lineSeparator();
        } catch (Exception e) {
            description = e.getMessage();
            System.out.println("Exception parsing expression ");
        }

        return description;
    }

    public Date nextTriggerDate(String expression) {
        String[] expressionParts;
        try {
            Calendar c = Calendar.getInstance();

            int second = c.get(Calendar.SECOND);
            int minute = c.get(Calendar.MINUTE);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            expressionParts = ExpressionParser.parseExpression(expression);
            List<Integer> seconds = getSecondsDescriptionBuilder().getSegmentDescription(expressionParts[0]);
            List<Integer> minutes = getMinutesDescriptionBuilder().getSegmentDescription(expressionParts[1]);
            List<Integer> hours = getHoursDescriptionBuilder().getSegmentDescription(expressionParts[2]);
            List<Integer> dayOfMonths = getDayOfMonthDescriptionBuilder().getSegmentDescription(expressionParts[3]);
            List<Integer> months = getMonthDescriptionBuilder().getSegmentDescription(expressionParts[4]);
            List<Integer> dayOfWeeks = getDayOfWeekDescriptionBuilder().getSegmentDescription(expressionParts[5]);


            Optional<Integer> firstSecond = seconds.stream().filter(integer -> integer >= second).findFirst();
            Optional<Integer> firstMinute = minutes.stream().filter(integer -> integer >= minute).findFirst();
            Optional<Integer> firstHour = hours.stream().filter(integer -> integer >= hour).findFirst();
            Optional<Integer> firstDayOfMonth = dayOfMonths.stream().filter(integer -> integer >= dayOfMonth).findFirst();
            Optional<Integer> firstMonth = months.stream().filter(integer -> integer >= month).findFirst();
            Optional<Integer> firstDayOfWeek = dayOfWeeks.stream().filter(integer -> integer >= dayOfWeek).findFirst();


            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.SECOND, firstSecond.orElse(0));
            c2.set(Calendar.MINUTE, firstMinute.orElse(0));
            c2.set(Calendar.HOUR_OF_DAY, firstHour.orElse(0));
            if (firstDayOfWeek.isPresent()) {
                c2.set(Calendar.DAY_OF_WEEK, firstDayOfWeek.get());
            } else {
                c2.set(Calendar.DAY_OF_MONTH, firstDayOfMonth.orElse(1));
            }

            c2.set(Calendar.MONTH, firstMonth.orElse(0));


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(c2.getTime()));

            System.out.println("1");
        } catch (Exception e) {
            System.out.println("Exception parsing expression ");
        }
        return null;
    }

    private String getSecondsDescription(String[] expressionParts) {
        return secondsDescriptionBuilder.getParsedDescriptionString(expressionParts[0]);
    }

    private String getMinutesDescription(String[] expressionParts) {
        return minutesDescriptionBuilder.getParsedDescriptionString(expressionParts[1]);
    }

    private String getHoursDescription(String[] expressionParts) {
        return hoursDescriptionBuilder.getParsedDescriptionString(expressionParts[2]);
    }


    private String getDayOfMonthDescription(String[] expressionParts) {
        return dayOfMonthDescriptionBuilder.getParsedDescriptionString(expressionParts[3]);
    }

    private String getMonthDescription(String[] expressionParts) {
        return monthDescriptionBuilder.getParsedDescriptionString(expressionParts[4]);
    }

    private String getDayOfWeekDescription(String[] expressionParts) {
        return dayOfWeekDescriptionBuilder.getParsedDescriptionString(expressionParts[5]);
    }

    public SecondsDescriptionBuilder getSecondsDescriptionBuilder() {
        return secondsDescriptionBuilder;
    }

    public MinutesDescriptionBuilder getMinutesDescriptionBuilder() {
        return minutesDescriptionBuilder;
    }

    public HoursDescriptionBuilder getHoursDescriptionBuilder() {
        return hoursDescriptionBuilder;
    }

    public DayOfMonthDescriptionBuilder getDayOfMonthDescriptionBuilder() {
        return dayOfMonthDescriptionBuilder;
    }

    public DayOfWeekDescriptionBuilder getDayOfWeekDescriptionBuilder() {
        return dayOfWeekDescriptionBuilder;
    }

    public MonthDescriptionBuilder getMonthDescriptionBuilder() {
        return monthDescriptionBuilder;
    }
}
