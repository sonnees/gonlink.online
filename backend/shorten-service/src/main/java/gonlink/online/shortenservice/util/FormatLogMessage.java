package gonlink.online.shortenservice.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FormatLogMessage {
    public static String formatLogMessage(String className, String method, String message, Object... params) {
        return String.format("[%s#%s] %s %s",
                className,
                method,
                message,
                Arrays.stream(params)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "))
        );
    }
}
