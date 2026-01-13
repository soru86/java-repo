package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * List Operations with Java Streams API
 */
public class ListOperations {

    // 40. Merge Two Lists into a Single List
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }

    // 41. Find the Intersection of Two Lists
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(set2::contains)
                .distinct()
                .collect(Collectors.toList());
    }

    // 42. Find the Union of Two Lists
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    // 43. Find the Difference Between Two Lists
    public static <T> List<T> difference(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(e -> !set2.contains(e))
                .collect(Collectors.toList());
    }

    // 44. Reverse a List Using Streams
    public static <T> List<T> reverseList(List<T> list) {
        /*
         * return list.stream()
         * .collect(Collectors.collectingAndThen(
         * Collectors.toList(),
         * l -> {
         * Collections.reverse(l);
         * return l;
         * }));
         */
        return IntStream.range(0, list.size()).mapToObj(i -> list.get(list.size() - 1 - i))
                .collect(Collectors.toList());
    }

    // 45. Flatten a List of Lists into a Single List
    public static <T> List<T> flatten(List<List<T>> listOfLists) {
        return listOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // 46. Find the Sum of All Even Numbers in a Nested List
    public static int sumEvenInNestedList(List<List<Integer>> nestedList) {
        return nestedList.stream()
                .flatMap(List::stream)
                .filter(n -> n % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();
    }

    // 47. Find the Sum of All Odd Numbers in a Nested List
    public static int sumOddInNestedList(List<List<Integer>> nestedList) {
        return nestedList.stream()
                .flatMap(List::stream)
                .filter(n -> n % 2 != 0)
                .mapToInt(Integer::intValue)
                .sum();
    }

    // Test methods
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9));

        System.out.println("Merge: " + mergeLists(list1, list2));
        System.out.println("Intersection: " + intersection(list1, list2));
        System.out.println("Union: " + union(list1, list2));
        System.out.println("Difference: " + difference(list1, list2));
        System.out.println("Reverse: " + reverseList(list1));
        System.out.println("Flatten: " + flatten(nestedList));
        System.out.println("Sum even in nested: " + sumEvenInNestedList(nestedList));
        System.out.println("Sum odd in nested: " + sumOddInNestedList(nestedList));
    }
}
