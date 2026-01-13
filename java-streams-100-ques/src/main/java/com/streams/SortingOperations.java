package com.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Sorting Operations with Java Streams API
 */
public class SortingOperations {

    // 17. Sort a List of Integers in Ascending Order
    public static List<Integer> sortAscending(List<Integer> list) {
        return list.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    // 18. Sort a List of Integers in Descending Order
    public static List<Integer> sortDescending(List<Integer> list) {
        return list.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    // 19. Sort a List of Strings in Alphabetical Order
    public static List<String> sortAlphabetically(List<String> list) {
        return list.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    // 20. Sort a List of Strings by Their Length
    public static List<String> sortByLength(List<String> list) {
        return list.stream()
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.toList());
    }

    // Test methods
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date");

        System.out.println("Ascending: " + sortAscending(numbers));
        System.out.println("Descending: " + sortDescending(numbers));
        System.out.println("Alphabetical: " + sortAlphabetically(strings));
        System.out.println("By length: " + sortByLength(strings));
    }
}
