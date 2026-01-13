package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Occurrence Counting Operations with Java Streams API
 */
public class OccurrenceOperations {

    // 48. Count the Occurrences of Each Element in a List
    public static <T> Map<T, Long> countOccurrences(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }

    // 49. Count the Occurrences of Each Character in a String
    public static Map<Character, Long> countCharacterOccurrences(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    // 50. Count the Occurrences of Each Word in a String
    public static Map<String, Long> countWordOccurrences(String str) {
        return Arrays.stream(str.toLowerCase().split("\\s+"))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }

    // 51. Count the Occurrences of Each Vowel in a String
    public static Map<Character, Long> countVowelOccurrences(String str) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(vowels::contains)
                .collect(Collectors.groupingBy(Character::toLowerCase, Collectors.counting()));
    }

    // 52. Count the Occurrences of Each Digit in a String
    public static Map<Character, Long> countDigitOccurrences(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isDigit)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    // 53. Find the Most Frequent Element in a List
    public static <T> Optional<T> mostFrequent(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // 54. Find the Least Frequent Element in a List
    public static <T> Optional<T> leastFrequent(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // Test methods
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        String text = "hello world java streams";

        System.out.println("Element occurrences: " + countOccurrences(numbers));
        System.out.println("Character occurrences: " + countCharacterOccurrences(text));
        System.out.println("Word occurrences: " + countWordOccurrences(text));
        System.out.println("Vowel occurrences: " + countVowelOccurrences(text));
        System.out.println("Digit occurrences: " + countDigitOccurrences("hello123world456"));
        System.out.println("Most frequent: " + mostFrequent(numbers));
        System.out.println("Least frequent: " + leastFrequent(numbers));
    }
}
