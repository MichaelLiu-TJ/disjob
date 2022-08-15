package liuwei.job.core.cron.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class HoursDescriptionBuilder extends AbstractDescriptionBuilder {


    private String headerDisplayString ="Hour";

    @Override
    protected List getSTARDescription() {
        List fullHourDescription= new ArrayList();
        IntStream.range(0, 24).forEach(
                val -> fullHourDescription.add(val)
        );
        return fullHourDescription;
    }

    @Override
    protected List getBetweenDescription(String initialLimit, String endLimit) {
        List betweenHourDescription= new ArrayList();
        int startLimit= Integer.parseInt(initialLimit);
        int endList= Integer.parseInt(endLimit);
        IntStream.rangeClosed(startLimit, endList).forEach(
                val -> betweenHourDescription.add(val)
        );
        return betweenHourDescription;
    }

    @Override
    protected List getIntervalDescription(String expression) {
        int frequency= Integer.parseInt(expression);
        return getValuesWithFixedFrequency(frequency,0,23);
    }

    @Override
    protected String getSingleItemDescription(String expression) {
        int hour= Integer.parseInt(expression);
        if(hour>=0 && hour<24)
            return expression;
        else
            throw new IllegalArgumentException("Hour expression cannot be less than 0 or greater than 23");

    }
    @Override
    public String getHeaderDisplayString() {
        return headerDisplayString;
    }

}
