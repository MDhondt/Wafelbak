package be.scoutsronse.wafelbak.tech.stacktrace;

import java.util.function.Predicate;

import static java.lang.Class.forName;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.stream;

public class StackTraceUtils {

    public static boolean calledBy(final Class<?> clazz) {
        return stackTraceElementMatching(matching(clazz));
    }

    private static boolean stackTraceElementMatching(Predicate<StackTraceElement> predicate) {
        return stream(currentThread().getStackTrace()).anyMatch(predicate);
    }

    private static Predicate<StackTraceElement> matching(final Class<?> clazz) {
        return stackTraceElement -> {
            try {
                return forName(stackTraceElement.getClassName()).isAssignableFrom(clazz);
            } catch (ClassNotFoundException e) {
                return false;
            }
        };
    }
}