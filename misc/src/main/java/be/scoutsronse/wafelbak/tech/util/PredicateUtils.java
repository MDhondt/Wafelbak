package be.scoutsronse.wafelbak.tech.util;

import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.copyOfRange;

public class PredicateUtils {

    public static <T> Predicate<T> alwaysTrue() {
        return t -> true;
    }

    public static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }

    public static <T> Predicate<T> or(Predicate<T>... predicates) {
        if (predicates.length == 0) {
            return alwaysTrue();
        } else if (predicates.length == 1) {
            return predicates[0];
        } else {
            return predicates[0].or(or(copyOfRange(predicates, 1, predicates.length)));
        }
    }

    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        if (predicates.length == 0) {
            return alwaysTrue();
        } else if (predicates.length == 1) {
            return predicates[0];
        } else {
            return predicates[0].and(and(copyOfRange(predicates, 1, predicates.length)));
        }
    }

    public static <T, R> Predicate<T> compose(Predicate<R> predicate, Function<T, R> function) {
        return t -> predicate.test(function.apply(t));
    }
}