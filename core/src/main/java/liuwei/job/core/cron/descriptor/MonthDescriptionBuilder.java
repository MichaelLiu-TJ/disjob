package liuwei.job.core.cron.descriptor;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MonthDescriptionBuilder extends AbstractDescriptionBuilder {

    private String headerDisplayString ="Month";

    @Override
    protected List getSTARDescription() {
        List fullMonthDescription= new ArrayList();
        IntStream.rangeClosed(1, 12).forEach(
                val -> fullMonthDescription.add(val)
        );
        return fullMonthDescription;
    }

    @Override
    protected List getBetweenDescription(String initialLimit, String endLimit) {
        List betweenMonthDescription= new ArrayList();
        int startLimit= Integer.parseInt(initialLimit);
        int endList= Integer.parseInt(endLimit);
        IntStream.rangeClosed(startLimit, endList).forEach(
                val -> betweenMonthDescription.add(val)
        );
        return betweenMonthDescription;
    }

    @Override
    protected List getIntervalDescription(String expression) {
        int frequency= Integer.parseInt(expression);
        return getValuesWithFixedFrequency(frequency,1,12);
    }

    @Override
    protected String getSingleItemDescription(String expression) {
        int month= Integer.parseInt(expression);
        if(month>0 && month<=12)
            return expression;
        else
            throw new IllegalArgumentException("Month expression cannot be less than 1 or greater than 12");
    }

    @Override
    public String getHeaderDisplayString() {
        return headerDisplayString;
    }
}
