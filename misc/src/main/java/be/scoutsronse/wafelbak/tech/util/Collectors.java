package be.scoutsronse.wafelbak.tech.util;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Collections.reverse;
import static java.util.function.Function.identity;
import static java.util.stream.Collector.of;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Collectors {

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException("Merge exception...");
        };
    }

    public static <T> Collector<T, ?, List<T>> toReversedList() {
        return collectingAndThen(toList(), list -> {
            reverse(list);
            return list;
        });
    }

    public static <E extends Enum<E>> Collector<E, ?, EnumSet<E>> toEnumSet(Class<E> enumm) {
        return java.util.stream.Collectors.toCollection(() -> EnumSet.noneOf(enumm));
    }

    public static <K, U> Collector<U, ?, Map<K, U>> toMapByKey(Function<U, K> keyFunction) {
        return java.util.stream.Collectors.toMap(keyFunction, Function.<U>identity());
    }

    public static <M extends Map<K, U>, K, U> Collector<U, ?, M> toMapByKey(Function<U, K> keyFunction, Supplier<M> mapSupplier) {
        return java.util.stream.Collectors.toMap(keyFunction, identity(), throwingMerger(), mapSupplier);
    }

    public static <M extends Map<K, U>, K, U, T> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends U> valueFunction, Supplier<M> mapSupplier) {
        return java.util.stream.Collectors.toMap(keyFunction, valueFunction, throwingMerger(), mapSupplier);
    }

    public static <T> Collector<T, ?, List<T>> minBy(Comparator<? super T> comparator) {
        return maxBy(comparator.reversed());
    }

    public static <T> Collector<T, ?, List<T>> maxBy(Comparator<? super T> comparator) {
        return of(
                ArrayList::new,
                (list, t) -> {
                    int c;
                    if (list.isEmpty() || (c = comparator.compare(t, list.get(0))) == 0) {
                        list.add(t);
                    } else if (c > 0) {
                        list.clear();
                        list.add(t);
                    }
                },
                (list1, list2) -> {
                    if (list1.isEmpty()) {
                        return list2;
                    }
                    if (list2.isEmpty()) {
                        return list1;
                    }
                    int r = comparator.compare(list1.get(0), list2.get(0));
                    if (r < 0) {
                        return list2;
                    } else if (r > 0) {
                        return list1;
                    } else {
                        list1.addAll(list2);
                        return list1;
                    }
                });
    }
}