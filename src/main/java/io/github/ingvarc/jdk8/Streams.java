package io.github.ingvarc.jdk8;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The class to experiment with Stream API.
 */
public class Streams {

    static int invocations = 0;

    public static void main(String... args) {

        Stream.of("Foo", "Bar", "Baz")
                .findFirst()
                .orElse(""); // "Foo"

        Stream.of("Foo", "Bar", "Baz")
                .filter((s) -> s.startsWith("B"))
                .collect(Collectors.toList()); // "Bar", "Baz"

        Stream.of("Foo", "Bar", "Baz")
                .map(String::length)
                .collect(Collectors.toList()); // 3, 3, 3

        Stream.of("Foo", "BarBar", "BazBazBaz")
                .map(String::length)
                .reduce((left, right) -> (left > right ? left : right))
                .orElse(0); // 9

        Stream.of("Foo Bar Baz")
                .flatMap((element) -> Arrays.stream(element.split(" ")))
                .collect(Collectors.toList()); // "Foo", "Bar", "Baz"

        Stream.of("Foo", "Bar", "Baz", "Baz", "Foo", "Bar")
                .distinct()
                .collect(Collectors.toList()); // "Foo", "Bar", "Baz"

        Stream.of("Foo", "Bar", "Baz")
                .sorted(String::compareTo)
                .collect(Collectors.toList()); // "Bar", "Baz", "Foo"

        IntStream.range(0, 1000)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new)); // 0, 1, 2, 3, ...

        IntStream.range(0, 1000)
                .boxed()
                .collect(Collectors.toList()); // // 0, 1, 2, 3, ...

        IntStream.range(0, 1000)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), (x) -> x % 4)).get(296).equals(0);

        StringBuilder stringBuilder = new StringBuilder();
        Stream.of("Foo", "Bar", "Baz").forEach(stringBuilder::append); // "FooBarBaz"

        // lazy
        Stream<String> stream = Stream.of("Foo", "Marco", "Bar", "Polo", "Baz")
                .filter((s) -> {
                    invocations++;
                    return s.length() == 3;
                });

        Iterator<String> iterator = stream.iterator();
        iterator.next(); // invocations == 1
        iterator.next();
        iterator.next(); // invocations == 3

        // eager
        invocations = 0;
        List<String> aList = Stream.of("Foo", "Marco", "Bar", "Polo", "Baz")
                .filter((s) -> {
                    invocations++;
                    return s.length() == 3;
                })
                .collect(Collectors.toList()); // aList.size() == 3; invocations == 5

        invocations = 0;
        IntStream.range(1, 1000)
                .filter(x -> {
                    invocations++;
                    return (x % 42) == 0;
                })
                .findFirst().getAsInt(); // 42, invocations == 42

        invocations = 0;
        IntStream.range(1, 1000)
                .parallel()
                .filter(x -> {
                    invocations++;
                    return (x % 42) == 0;
                })
                .findFirst().getAsInt(); // 42, invocations == ? since parallel streams can evaluate more

        IntStream.range(1, 1000)
                .unordered()
                .parallel()
                .filter(x -> {
                    invocations++;
                    return (x % 42) == 0;
                })
                .findFirst().getAsInt(); // ? since unordered streams don't have any particular order, ? since parallel streams can evaluate more

        List<Integer> intList = new ArrayList<>(1_000_000);
        for (int i = 1; i <= 1_000_000; i++) {
            intList.add(i);
        }
        Collections.shuffle(intList);

        intList.stream().reduce(Math::max).get(); // 1_000_000
        intList.parallelStream().reduce(Math::max).get(); // 1_000_000

        Stream<Integer> streamOfInt = Stream.<Integer>builder().add(1).add(2).build();
        IntStream intStream = IntStream.builder().add(1).add(2).add(3).build();

        Stream<Integer> mergedStream = Stream.concat(streamOfInt, Stream.<Integer>builder().add(3).build());

        DoubleStream.generate(Math::random)
                .limit(1_000_000)
                .average()
                .getAsDouble();

        IntStream.range(0, 1000)
                .sum();

        IntStream.range(0, 1000)
                .reduce((r1, r2) -> r2 + r1)
                .getAsInt();
    }
}