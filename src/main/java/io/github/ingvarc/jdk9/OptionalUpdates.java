package io.github.ingvarc.jdk9;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Several new methods in Optional class.
 */
public class OptionalUpdates {

    public static void main(String[] args) {
        Optional<String> someValue = Optional.of("some value");
        Optional<String> defaultValue = Optional.of("default value");

        someValue.or(() -> defaultValue); // "some value"


        List<Optional<String>> optionals = List.of(
                Optional.of("Java"),
                Optional.of("nine"),
                Optional.empty(),
                Optional.of("Optional"),
                Optional.of("JDK9"),
                Optional.empty(),
                Optional.of("Updates")
        );

        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        // successCounter 5, onEmptyOptionalCounter 2
        optionals.stream()
                .forEach(v -> v.ifPresentOrElse(x -> successCounter.incrementAndGet(), onEmptyOptionalCounter::incrementAndGet));

        optionals.stream()
                .forEach(System.out::println); // optionals in list

        optionals.stream()
                .map(o -> o.orElse(null))
                .forEach(System.out::println); // All Optional values or null

        optionals.stream()
                .flatMap(Optional::stream)
                .forEach(System.out::println); // Only present Optional values
    }
}
