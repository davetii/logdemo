package software.daveturner.logdemo;

import ch.qos.logback.classic.*;
import ch.qos.logback.classic.spi.*;

import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class MaskingPatternLayout extends PatternLayout {
    private Pattern multilinePattern;
    private final List<String> maskPatterns = new ArrayList<>();

    public void addMaskPattern(String maskPattern) {
        maskPatterns.add(maskPattern);
        multilinePattern = Pattern.compile(maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return maskMessage(super.doLayout(event));
    }

    private String maskMessage(String message) {
        if (multilinePattern == null) {
            return message;
        }

        String m = new String(message.getBytes(), StandardCharsets.US_ASCII);
        StringBuilder sb = new StringBuilder(m);

        Matcher matcher = multilinePattern.matcher(sb);
        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> sb.setCharAt(i, '*'));
                }
            });
        }
        return sb.toString();
    }
}
