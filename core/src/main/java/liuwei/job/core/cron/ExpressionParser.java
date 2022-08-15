package liuwei.job.core.cron;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public class ExpressionParser {

    public static String[] parseExpression(String expression) throws ParseException {
        String[] parsed;
        expression = StringUtils.normalizeSpace(expression);

        if (StringUtils.isEmpty(expression)) {
            throw new IllegalArgumentException("expression empty exception");
        }
        String[] expressionParts = expression.split(" ");
        if (expressionParts.length == 6) {
            parsed = expressionParts;
        } else {
            throw new IllegalArgumentException("expression having more or less  than expected arguments");
        }

        normaliseExpression(parsed);

        return parsed;
    }

    private static void normaliseExpression(String[] expressionParts) {

        // Convert 0/, 1/ to */
        expressionParts[0] = expressionParts[0].startsWith("0/") ? expressionParts[0].replace("0/", "*/") : expressionParts[0]; // seconds
        expressionParts[1] = expressionParts[1].startsWith("0/") ? expressionParts[1].replace("0/", "*/") : expressionParts[1]; // minutes
        expressionParts[2] = expressionParts[2].startsWith("0/") ? expressionParts[2].replace("0/", "*/") : expressionParts[2]; // hours
        expressionParts[3] = expressionParts[3].startsWith("1/") ? expressionParts[3].replace("1/", "*/") : expressionParts[3]; // day of month
        expressionParts[4] = expressionParts[4].startsWith("1/") ? expressionParts[4].replace("1/", "*/") : expressionParts[4]; // Month
        expressionParts[5] = expressionParts[5].startsWith("1/") ? expressionParts[5].replace("1/", "*/") : expressionParts[5]; // Day of week

        // convert */1 to *
        for (int i = 0; i < expressionParts.length; i++) {
            if ("*/1".equals(expressionParts[i])) {
                expressionParts[i] = "*";
            }
        }
    }

}
