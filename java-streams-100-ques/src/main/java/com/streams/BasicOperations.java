package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Basic Operations with Java Streams API
 */
public class BasicOperations {

    // 1. Find the Sum of All Elements in a List
    public static int sumOfElements(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    // 2. Find the Product of All Elements in a List
    public static int productOfElements(List<Integer> list) {
        return list.stream()
                .reduce(1, (a, b) -> a * b);
    }

    // 3. Find the Average of All Elements in a List
    public static OptionalDouble averageOfElements(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    // 4. Find the Maximum Element in a List
    public static Optional<Integer> maxElement(List<Integer> list) {
        return list.stream()
                .max(Integer::compareTo);
    }

    // 5. Find the Minimum Element in a List
    public static Optional<Integer> minElement(List<Integer> list) {
        return list.stream()
                .min(Integer::compareTo);
    }

    // 6. Count the Number of Elements in a List
    public static long countElements(List<?> list) {
        return list.stream()
                .count();
    }

    // 7. Check if a List Contains a Specific Element
    public static boolean containsElement(List<?> list, Object element) {
        return list.stream()
                .anyMatch(e -> Objects.equals(e, element));
    }

    // 8. Filter Out Even Numbers from a List
    public static List<Integer> filterEvenNumbers(List<Integer> list) {
        return list.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
    }

    // 9. Filter Out Odd Numbers from a List
    public static List<Integer> filterOddNumbers(List<Integer> list) {
        return list.stream()
                .filter(n -> n % 2 != 0)
                .collect(Collectors.toList());
    }

    // 10. Convert a List of Strings to Uppercase
    public static List<String> convertToUppercase(List<String> list) {
        return list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    // 11. Convert a List of Integers to Their Squares
    public static List<Integer> convertToSquares(List<Integer> list) {
        return list.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    // 12. Find the First Element in a List
    public static <T> Optional<T> firstElement(List<T> list) {
        return list.stream()
                .findFirst();
    }

    // 13. Find the Last Element in a List
    public static <T> Optional<T> lastElement(List<T> list) {
        return list.stream()
                .reduce((first, second) -> second);
    }

    // 14. Check if All Elements in a List Satisfy a Condition
    public static boolean allElementsSatisfy(List<Integer> list, java.util.function.Predicate<Integer> condition) {
        return list.stream()
                .allMatch(condition);
    }

    // 15. Check if Any Element in a List Satisfies a Condition
    public static boolean anyElementSatisfies(List<Integer> list, java.util.function.Predicate<Integer> condition) {
        return list.stream()
                .anyMatch(condition);
    }

    // 16. Remove Duplicate Elements from a List
    public static <T> List<T> removeDuplicates(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    // Test methods
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 2, 3);
        List<String> strings = Arrays.asList("hello", "world", "java", "streams");

        System.out.println("Sum: " + sumOfElements(numbers));
        System.out.println("Product: " + productOfElements(numbers));
        System.out.println("Average: " + averageOfElements(numbers));
        System.out.println("Max: " + maxElement(numbers));
        System.out.println("Min: " + minElement(numbers));
        System.out.println("Count: " + countElements(numbers));
        System.out.println("Contains 3: " + containsElement(numbers, 3));
        System.out.println("Even numbers: " + filterEvenNumbers(numbers));
        System.out.println("Odd numbers: " + filterOddNumbers(numbers));
        System.out.println("Uppercase: " + convertToUppercase(strings));
        System.out.println("Squares: " + convertToSquares(numbers));
        System.out.println("First: " + firstElement(numbers));
        System.out.println("Last: " + lastElement(numbers));
        System.out.println("All > 0: " + allElementsSatisfy(numbers, n -> n > 0));
        System.out.println("Any > 4: " + anyElementSatisfies(numbers, n -> n > 4));
        System.out.println("No duplicates: " + removeDuplicates(numbers));
    }
}
