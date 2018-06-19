package io.github.ingvarc.jdk10;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Experiments with Local-Variable Type Inference.
 */
public class LocalVariableTypeInference {


    // Type Inference can only be used for local variables, NOT for class fields
    // private var number = 125;     // DOESN'T compile


    // type inference cannot be used for method parameters
    // void greeting(var name) {        // DOESN'T compile
    //      System.out.println("Hello, my name is " + name);
    // }


    // var is allowed as variable and method name
    private static void var() {
        var var = "var";
    }


    // var is not allowed as class name
    // private class var{}  // DOESN'T compile


    // var cannot be used in a method signature
    // private var getString() {
    //      return "foo";
    // }


    public static void main(String... args) throws IOException {

        // simple type inference
        var number = 125;
        var text = "some text";
        var character = 'A';

        System.out.println(number);
        System.out.println(text);
        System.out.println(character);


        // multiple variables
        int foo = 0, bar = 1;
        // var foo = 0, bar = 1;   // DOESN'T COMPILE


        // `var` is not allowed as an element type of an array
        int intArray[] = new int[0];
        // var intArray[] = new int[0];     // DOESN'T COMPILE


        // `var` is not allowed with array initializer
        long[] longArray = {1L, 2L, 3L};
        // var longArray = { 1L, 2L, 3L };  // DOESN'T COMPILE


        // compiler infers ArrayList<String> instead of List<String>
        var strings = new ArrayList<String>();
        // so this is a compile error
        // strings = new LinkedList<String>();  // DOESN'T COMPILE


        // type inference from return type
        var name = getName();
        var age = getAge();

        System.out.println(name);
        System.out.println(age);


        // type inference from parameterised type
        var authors = new ArrayList<Author>();
        authors.add(new Author("Walter Isaacson", 65));
        authors.add(new Author("Joanne Rowling", 52));


        // type inference in loops
        for (var author : authors) {
            System.out.println(author.getName() + " " + author.getAge());
        }

        var numbers = List.of("one", "two", "three");
        for (String aNumber : numbers) {
            System.out.println(aNumber);
        }


        // type inference in try-with-resources statement
        try (var file = new FileInputStream(new File("non-existent-file"))) {
            new BufferedReader(new InputStreamReader(file))
                    .lines()
                    .forEach(System.out::println);
        } catch (IOException ex) {
            System.out.println("There's actually no `non-existent-file`");
        }


        // CharSequence & Comparable comparableCharSequence = getComparableCharSequence("s");
        var comparableCharSequence = getComparableCharSequence("The Force will be with you. Always.");
        System.out.println(comparableCharSequence.length());
        System.out.println(comparableCharSequence.compareTo(""));


        // type inference with non-denotable types
        Object luke = new Object() {
            String name = "Luke Skywalker";
            int age = 53;
        };
        // System.out.println(luke.name + " " + luke.age);     // DOESN'T COMPILE

        var han = new Object() {
            String name = "Han Solo";
            int age = 63;
        };
        System.out.println(han.name + " " + han.age);


        // Illegal or impractical use of type inference

        // type inference variables must be initialized straight away
        // var name;        // DOESN'T COMPILE
        // name = "Luke";   // DOESN'T COMPILE

        // Lambdas must always declare an explicit type
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        // var add = (x, y) -> x + y;                               // DOESN'T COMPILE
        // var nameSupplier = () -> "Luke";                         // DOESN'T COMPILE
        // var nameFetcher = LocalVariableTypeInference::getName;   // DOESN'T COMPILE

        String message = "The Force will be with you. Always.";
        Function<String, String> quotify = m -> "'" + message + "'";
        // var quotify = m -> "'" + message + "'";  // DOESN'T COMPILE


        // diamond operator influence
        List<Author> fantasyAuthors = new ArrayList<>();    // fantasyAuthors<Author>
        var fictionAuthors = new ArrayList<>();             // fictionAuthors<Object>
    }

    private static <T extends CharSequence & Comparable<T>> T getComparableCharSequence(T text) {
        return text;
    }

    private static String getName() {
        return "Luke Skywalker";
    }

    private static int getAge() {
        return 53;
    }

    private static class Author {
        private String name;
        private int age;

        Author(String name, int age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }
    }
}