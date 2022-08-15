package liuwei.job.core.cron.descriptor;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SecondsDescriptionBuilder extends AbstractDescriptionBuilder {


    private String headerDisplayString = "Seconds";

    @Override
    protected List getSTARDescription() {
        List fullSecondDescription = new ArrayList();
        IntStream.range(0, 60).forEach(
                val -> fullSecondDescription.add(val)
        );
        return fullSecondDescription;
    }

    @Override
    protected List getBetweenDescription(String initialLimit, String endLimit) {
        List betweenSecondDescription = new ArrayList();
        int startLimit = Integer.parseInt(initialLimit);
        int endList = Integer.parseInt(endLimit);
        IntStream.rangeClosed(startLimit, endList).forEach(
                val -> betweenSecondDescription.add(val)
        );
        return betweenSecondDescription;
    }

    @Override
    protected List getIntervalDescription(String expression) {
        int frequency = Integer.parseInt(expression);
        return getValuesWithFixedFrequency(frequency, 0, 59);
    }

    @Override
    protected String getSingleItemDescription(String expression) {
        int month = Integer.parseInt(expression);
        if (month >= 0 && month < 60)
            return expression;
        else
            throw new IllegalArgumentException("Seconds expression cannot be less than 0 or greater than 59");
    }

    @Override
    public String getHeaderDisplayString() {
        return headerDisplayString;
    }
}
