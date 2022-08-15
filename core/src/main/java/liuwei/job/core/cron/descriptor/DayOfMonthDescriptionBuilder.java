package liuwei.job.core.cron.descriptor;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DayOfMonthDescriptionBuilder extends AbstractDescriptionBuilder {

    String headerDisplayString ="Day of month";
    @Override
    protected List getSTARDescription() {
        List fullDayOfMonthDescription= new ArrayList();
        IntStream.rangeClosed(1, 31).forEach(
                val -> fullDayOfMonthDescription.add(val)
        );
        return fullDayOfMonthDescription;
    }

    @Override
    protected List getBetweenDescription(String initialLimit, String endLimit) {
        List betweenDOMDescription= new ArrayList();
        int startLimit= Integer.parseInt(initialLimit);
        int endList= Integer.parseInt(endLimit);
        IntStream.rangeClosed(startLimit, endList).forEach(
                val -> betweenDOMDescription.add(val)
        );
        return betweenDOMDescription;
    }

    @Override
    protected List getIntervalDescription(String expression) {
        int frequency= Integer.parseInt(expression);
        return getValuesWithFixedFrequency(frequency,1,31);
    }

    @Override
    protected String getSingleItemDescription(String expression) {
        int dayOfMonth= Integer.parseInt(expression);
        if(dayOfMonth>0 && dayOfMonth<=31)
            return expression;
        else
            throw new IllegalArgumentException("Day Of month cannot be less than 0 or greater than 31");
    }

    @Override
    public String getHeaderDisplayString() {
        return headerDisplayString;
    }


}
