package io.github.ingvarc.jdk8;

/**
 * The use case of default methods.
 */
public class DefaultMethods {

    public static void main(String[] args) {
        new NewClass().legacyMethod(); // "Legacy method in NewClass"
        new NewClass().newMethod(); // "New method in NewClass"

        new LegacyClass().legacyMethod(); // "Legacy method in LegacyClass"
        new LegacyClass().newMethod(); // "Default method in LegacyInterface"
    }

    public interface LegacyInterface {
        String legacyMethod();

        default String newMethod() {
            return "Default method in LegacyInterface";
        }
    }

    public static class LegacyClass implements LegacyInterface {
        @Override
        public String legacyMethod() {
            return "Legacy method in LegacyClass";
        }
    }

    public static class NewClass implements LegacyInterface {
        @Override
        public String legacyMethod() {
            return "Legacy method in NewClass";
        }

        @Override
        public String newMethod() {
            return "New method in NewClass";
        }
    }
}
