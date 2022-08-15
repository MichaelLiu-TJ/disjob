package liuwei.job.core.cron.descriptor;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MinutesDescriptionBuilder extends AbstractDescriptionBuilder {


    private String headerDisplayString ="Minutes";

    @Override
    protected List getSTARDescription() {
        List fullMinuteDescription= new ArrayList();
        IntStream.range(0, 60).forEach(
                val -> fullMinuteDescription.add(val)
        );
        return fullMinuteDescription;
    }

    @Override
    protected List getBetweenDescription(String initialLimit, String endLimit) {
        List betweenMinuteDescription= new ArrayList();
        int startLimit= Integer.parseInt(initialLimit);
        int endList= Integer.parseInt(endLimit);
        IntStream.rangeClosed(startLimit, endList).forEach(
                val -> betweenMinuteDescription.add(val)
        );
        return betweenMinuteDescription;
    }

    @Override
    protected List getIntervalDescription(String expression) {
        int frequency= Integer.parseInt(expression);
        return getValuesWithFixedFrequency(frequency,0,59);
    }

    @Override
    protected String getSingleItemDescription(String expression) {
        int month= Integer.parseInt(expression);
        if(month>=0 && month<60)
            return expression;
        else
            throw new IllegalArgumentException("Minutes expression cannot be less than 0 or greater than 59");
    }

    @Override
    public String getHeaderDisplayString() {
        return headerDisplayString;
    }
}
