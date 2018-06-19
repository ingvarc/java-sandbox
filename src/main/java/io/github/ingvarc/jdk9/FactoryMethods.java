package io.github.ingvarc.jdk9;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class to experiment with new factory methods of collections.
 * <p>
 * The intention to have so many overloaded methods is because every var-args method call implicitly creates an array.
 * Having the overloaded methods avoid unnecessary object creation and the garbage collection overhead thereof.
 * <p>
 * The collections created using factory methods are immutable and changing an element,
 * adding new elements or removing an element throws UnsupportedOperationException.
 * <p>
 * No null element allowed. In the case of List and Set, no elements can be null.
 * In the case of a Map, neither keys nor values can be null. Passing null argument throws a NullPointerException.
 * <p>
 * The instances created by factory methods are value based.
 * This means that factories are free to create a new instance or return an existing instance.
 */
public class FactoryMethods {

    public static void main(String[] args) {

        Set.of("Java", "nine", "factory", "methods");
        // Set.of("Java", "nine", "factory").add("methods"); // throws UnsupportedOperationException
        // Set.of("Java", "nine", "factory").remove("factory"); // throws UnsupportedOperationException
        // Set.of("Java", "nine", null); // throws NullPointerException

        List.of("Java", "nine", "factory", "methods");
        // List.of("Java", "nine", "factory").add("methods"); // throws UnsupportedOperationException
        // List.of("Java", "nine", "factory").remove("factory"); // throws UnsupportedOperationException
        // List.of("Java", "nine", null); // throws NullPointerException

        Map.of("Java", "eight", "JDK", "nine", "JRE", "ten");
        // Map.of("Java", "eight", "JDK", "nine", "JRE", "ten").remove("Java"); // throws UnsupportedOperationException
        // Map.of("Java", "eight", "JDK", "nine").put("JRE", "ten"); // throws UnsupportedOperationException
        // Map.of("Java", "eight", "JDK", "nine", "JRE", null); //  throws NullPointerException
        // Map.of("Java", "eight", "JDK", "nine", null, "ten"); //  throws NullPointerException

        Map.ofEntries(
                Map.entry("Java", "eight"),
                Map.entry("JDK", "nine"),
                Map.entry("JRE", "ten")
        );
    }
}
