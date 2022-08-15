package liuwei.job.core.cron.descriptor;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class AbstractDescriptionBuilder {

    protected final char[] SpecialCharsMinusStar = new char[]{'/', '-', ','};

    public List<Integer> getSegmentDescription(String expression) {
        List descriptionList = new ArrayList();
        if (StringUtils.isEmpty(expression)) {
            descriptionList = new ArrayList();
        } else if ("*".equals(expression)) {
            descriptionList = getSTARDescription();
        } else if (!StringUtils.containsAny(expression, SpecialCharsMinusStar)) {
            int singleItemData = Integer.parseInt(getSingleItemDescription(expression));
            descriptionList.add(singleItemData);
        } else if (expression.contains("-")) {
            String[] segments = expression.split("-");
            descriptionList = getBetweenDescription(getSingleItemDescription(segments[0]), getSingleItemDescription(segments[1]));
        } else if (expression.contains(",")) {
            String[] segments = expression.split(",");
            for (String segment : segments) {
                int singleItemData = Integer.parseInt(getSingleItemDescription(getSingleItemDescription(segment)));
                descriptionList.add(singleItemData);

            }
        } else if (expression.contains("/")) {
            String[] segments = expression.split("/");
            descriptionList = getIntervalDescription(segments[1]);
        }
        return descriptionList;
    }

    protected abstract List getSTARDescription();

    protected abstract List getBetweenDescription(String initialLimit, String endLimit);

    protected abstract List getIntervalDescription(String expression);

    protected abstract String getSingleItemDescription(String expression);

    public String getStreamAsString(List listData) {
        return listData.stream().map(Object::toString).collect(Collectors.joining(" ")).toString();
    }


    List getValuesWithFixedFrequency(int frequency, int start, int endIncluded) {
        List frequencyList = IntStream.iterate(start, i -> i + frequency).limit(endIncluded + 1)
                .filter(i -> i <= endIncluded)
                .boxed()
                .collect(toList());

        return frequencyList;
    }

    abstract public String getHeaderDisplayString();

    public String getParsedDescriptionString(String expressionPart) {
        return StringUtils.rightPad(getHeaderDisplayString(), 15, " ") + getStreamAsString(getSegmentDescription(expressionPart));
    }
}
