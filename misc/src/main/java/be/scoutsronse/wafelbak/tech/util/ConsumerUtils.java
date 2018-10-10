package be.scoutsronse.wafelbak.tech.util;

import java.util.Objects;
import java.util.function.Consumer;

import static java.util.Arrays.stream;

public class ConsumerUtils {

    public static <T> Consumer<T> combine(Consumer<? super T>... consumers) {
        return stream(consumers).filter(Objects::nonNull).reduce(t -> {}, Consumer::andThen, Consumer::andThen);
    }
}