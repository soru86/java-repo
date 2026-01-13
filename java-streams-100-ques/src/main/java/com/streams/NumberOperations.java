package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Number Operations with Java Streams API
 */
public class NumberOperations {

    // 21. Find the Sum of Digits of a Number
    public static int sumOfDigits(int number) {
        return String.valueOf(number)
                .chars()
                .map(Character::getNumericValue)
                .sum();
    }

    // 22. Find the Factorial of a Number
    public static long factorial(int n) {
        return IntStream.rangeClosed(1, n)
                .reduce(1, (a, b) -> a * b);
    }

    // 23. Find the Second-Largest Element in a List
    public static Optional<Integer> secondLargest(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted(Collections.reverseOrder())
                .skip(1)
                .findFirst();
    }

    // 24. Find the Second-Smallest Element in a List
    public static Optional<Integer> secondSmallest(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted()
                .skip(1)
                .findFirst();
    }

    // 25. Find the Longest String in a List
    public static Optional<String> longestString(List<String> list) {
        return list.stream()
                .max(Comparator.comparing(String::length));
    }

    // 26. Find the Shortest String in a List
    public static Optional<String> shortestString(List<String> list) {
        return list.stream()
                .min(Comparator.comparing(String::length));
    }

    // 27. Find the Sum of Squares of All Elements in a List
    public static int sumOfSquares(List<Integer> list) {
        return list.stream()
                .mapToInt(n -> n * n)
                .sum();
    }

    // 28. Find the Sum of Cubes of All Elements in a List
    public static int sumOfCubes(List<Integer> list) {
        return list.stream()
                .mapToInt(n -> n * n * n)
                .sum();
    }

    // 29. Find the Sum of All Prime Numbers in a List
    public static int sumOfPrimes(List<Integer> list) {
        return list.stream()
                .filter(NumberOperations::isPrime)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        return IntStream.rangeClosed(2, (int) Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
    }

    // 30. Find the Sum of All Fibonacci Numbers in a List
    public static int sumOfFibonacciNumbers(List<Integer> list) {
        Set<Integer> fibonacciSet = generateFibonacciSet(list.stream().mapToInt(Integer::intValue).max().orElse(0));
        return list.stream()
                .filter(fibonacciSet::contains)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static Set<Integer> generateFibonacciSet(int max) {
        Set<Integer> set = new HashSet<>();
        int a = 0, b = 1;
        while (a <= max) {
            set.add(a);
            int temp = a + b;
            a = b;
            b = temp;
        }
        return set;
    }

    // 31. Find the Sum of All Even-Indexed Elements in a List
    public static <T extends Number> double sumOfEvenIndexed(List<T> list) {
        return IntStream.range(0, list.size())
                .filter(i -> i % 2 == 0)
                .mapToDouble(i -> list.get(i).doubleValue())
                .sum();
    }

    // 32. Find the Sum of All Odd-Indexed Elements in a List
    public static <T extends Number> double sumOfOddIndexed(List<T> list) {
        return IntStream.range(0, list.size())
                .filter(i -> i % 2 != 0)
                .mapToDouble(i -> list.get(i).doubleValue())
                .sum();
    }

    // 33. Find the Sum of All Elements Greater Than a Given Value
    public static int sumGreaterThan(List<Integer> list, int value) {
        return list.stream()
                .filter(n -> n > value)
                .mapToInt(Integer::intValue)
                .sum();
    }

    // 34. Find the Standard Deviation of a List of Numbers
    public static double standardDeviation(List<Double> list) {
        double mean = list.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        
        double variance = list.stream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .average()
                .orElse(0.0);
        
        return Math.sqrt(variance);
    }

    // 35. Find the Median of a List of Numbers
    public static OptionalDouble median(List<Double> list) {
        List<Double> sorted = list.stream()
                .sorted()
                .collect(Collectors.toList());
        
        int size = sorted.size();
        if (size == 0) return OptionalDouble.empty();
        
        if (size % 2 == 0) {
            double mid1 = sorted.get(size / 2 - 1);
            double mid2 = sorted.get(size / 2);
            return OptionalDouble.of((mid1 + mid2) / 2.0);
        } else {
            return OptionalDouble.of(sorted.get(size / 2));
        }
    }

    // 36. Find the Mode of a List of Numbers
    public static Optional<Integer> mode(List<Integer> list) {
        return list.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // Test methods
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);

        System.out.println("Sum of digits (123): " + sumOfDigits(123));
        System.out.println("Factorial (5): " + factorial(5));
        System.out.println("Second largest: " + secondLargest(numbers));
        System.out.println("Second smallest: " + secondSmallest(numbers));
        System.out.println("Longest string: " + longestString(strings));
        System.out.println("Shortest string: " + shortestString(strings));
        System.out.println("Sum of squares: " + sumOfSquares(numbers));
        System.out.println("Sum of cubes: " + sumOfCubes(numbers));
        System.out.println("Sum of primes: " + sumOfPrimes(numbers));
        System.out.println("Sum of even-indexed: " + sumOfEvenIndexed(numbers));
        System.out.println("Sum of odd-indexed: " + sumOfOddIndexed(numbers));
        System.out.println("Sum > 5: " + sumGreaterThan(numbers, 5));
        System.out.println("Standard deviation: " + standardDeviation(doubles));
        System.out.println("Median: " + median(doubles));
        System.out.println("Mode: " + mode(Arrays.asList(1, 2, 2, 3, 3, 3, 4)));
    }
}
