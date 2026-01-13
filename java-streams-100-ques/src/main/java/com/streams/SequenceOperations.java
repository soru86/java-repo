package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Sequence Generation Operations with Java Streams API
 */
public class SequenceOperations {

    // 81. Generate the Fibonacci Sequence Using Streams
    public static List<Long> generateFibonacci(int n) {
        return Stream.iterate(new long[]{0, 1}, fib -> new long[]{fib[1], fib[0] + fib[1]})
                .limit(n)
                .map(fib -> fib[0])
                .collect(Collectors.toList());
    }

    // 82. Generate a List of Random Numbers Using Streams
    public static List<Integer> generateRandomNumbers(int count, int min, int max) {
        Random random = new Random();
        return random.ints(count, min, max)
                .boxed()
                .collect(Collectors.toList());
    }

    // Test methods
    public static void main(String[] args) {
        System.out.println("Fibonacci (10): " + generateFibonacci(10));
        System.out.println("Random numbers (10, 1-100): " + generateRandomNumbers(10, 1, 100));
    }
}
