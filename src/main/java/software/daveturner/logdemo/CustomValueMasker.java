package software.daveturner.logdemo;

import com.fasterxml.jackson.core.*;
import net.logstash.logback.mask.*;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class CustomValueMasker implements ValueMasker {
    private final Pattern multilinePattern = Pattern.compile("\\\"email\\\"\\s*:\\s*\\\"(.*?)\\\"", Pattern.MULTILINE);
    private final List<String> maskPatterns = new ArrayList<>();

    private String maskMessage(String message) {

        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);
        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> sb.setCharAt(i, 'x'));
                }
            });
        }
        return sb.toString();
    }

    @Override
    public Object mask(JsonStreamContext context, Object value) {
        if (value instanceof CharSequence) {
            return maskMessage((String) value);
        }
        return value;
    }
}
