package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * String Operations with Java Streams API
 */
public class StringOperations {

    // 55. Reverse a String Using Streams
    public static String reverseString(String str) {
        return new StringBuilder(str)
                .reverse()
                .toString();
    }

    // Alternative using streams
    public static String reverseStringStream(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            Collections.reverse(list);
                            return list.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining());
                        }));
    }

    // 56. Find the First Non-Repeated Character in a String
    public static Optional<Character> firstNonRepeatedChar(String str) {
        Map<Character, Long> charCount = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> charCount.get(c) == 1)
                .findFirst();
    }

    // 57. Find the First Repeated Character in a String
    public static Optional<Character> firstRepeatedChar(String str) {
        Set<Character> seen = new HashSet<>();
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !seen.add(c))
                .findFirst();
    }

    // 58. Check if a String is a Palindrome
    public static boolean isPalindrome(String str) {
        String cleaned = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        return IntStream.range(0, cleaned.length() / 2)
                .allMatch(i -> cleaned.charAt(i) == cleaned.charAt(cleaned.length() - 1 - i));
    }

    // 59. Find All Anagrams of a String from a List
    public static List<String> findAnagrams(String target, List<String> words) {
        String sortedTarget = sortString(target);
        return words.stream()
                .filter(word -> sortString(word).equals(sortedTarget))
                .collect(Collectors.toList());
    }

    private static String sortString(String str) {
        return str.chars()
                .sorted()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    // 60. Find the Longest Palindrome in a List of Strings
    public static Optional<String> longestPalindrome(List<String> list) {
        return list.stream()
                .filter(StringOperations::isPalindrome)
                .max(Comparator.comparing(String::length));
    }

    // 61. Find the Shortest Palindrome in a List of Strings
    public static Optional<String> shortestPalindrome(List<String> list) {
        return list.stream()
                .filter(StringOperations::isPalindrome)
                .min(Comparator.comparing(String::length));
    }

    // 62. Find the Longest Word in a String
    public static Optional<String> longestWord(String str) {
        return Arrays.stream(str.split("\\s+"))
                .max(Comparator.comparing(String::length));
    }

    // 63. Find the Shortest Word in a String
    public static Optional<String> shortestWord(String str) {
        return Arrays.stream(str.split("\\s+"))
                .min(Comparator.comparing(String::length));
    }

    // 64. Find the Number of Words in a String
    public static long numberOfWords(String str) {
        return Arrays.stream(str.trim().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .count();
    }

    // 65. Remove All Vowels from a String
    public static String removeVowels(String str) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !vowels.contains(c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // 66. Remove All Consonants from a String
    public static String removeConsonants(String str) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> vowels.contains(c) || !Character.isLetter(c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // 67. Remove All Digits from a String
    public static String removeDigits(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !Character.isDigit(c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // 68. Remove All Special Characters from a String
    public static String removeSpecialCharacters(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetterOrDigit)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // 69. Extract All Digits from a String and Sum Them
    public static int extractAndSumDigits(String str) {
        return str.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .sum();
    }

    // 70. Extract All Words from a String and Count Their Occurrences
    public static Map<String, Long> extractAndCountWords(String str) {
        return Arrays.stream(str.toLowerCase().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }

    // 71. Extract All Unique Words from a String
    public static Set<String> extractUniqueWords(String str) {
        return Arrays.stream(str.toLowerCase().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toSet());
    }

    // 72. Extract All Palindromic Words from a String
    public static List<String> extractPalindromicWords(String str) {
        return Arrays.stream(str.split("\\s+"))
                .filter(StringOperations::isPalindrome)
                .collect(Collectors.toList());
    }

    // 73. Extract All Words Starting with a Specific Letter
    public static List<String> extractWordsStartingWith(String str, char letter) {
        return Arrays.stream(str.split("\\s+"))
                .filter(word -> !word.isEmpty() && Character.toLowerCase(word.charAt(0)) == Character.toLowerCase(letter))
                .collect(Collectors.toList());
    }

    // 74. Extract All Words Ending with a Specific Letter
    public static List<String> extractWordsEndingWith(String str, char letter) {
        return Arrays.stream(str.split("\\s+"))
                .filter(word -> !word.isEmpty() && 
                        Character.toLowerCase(word.charAt(word.length() - 1)) == Character.toLowerCase(letter))
                .collect(Collectors.toList());
    }

    // Test methods
    public static void main(String[] args) {
        String text = "hello world java streams programming";
        List<String> words = Arrays.asList("listen", "silent", "enlist", "hello", "world");

        System.out.println("Reverse: " + reverseString("hello"));
        System.out.println("First non-repeated: " + firstNonRepeatedChar("hello"));
        System.out.println("First repeated: " + firstRepeatedChar("hello"));
        System.out.println("Is palindrome: " + isPalindrome("racecar"));
        System.out.println("Anagrams: " + findAnagrams("listen", words));
        System.out.println("Longest word: " + longestWord(text));
        System.out.println("Shortest word: " + shortestWord(text));
        System.out.println("Number of words: " + numberOfWords(text));
        System.out.println("Remove vowels: " + removeVowels("hello world"));
        System.out.println("Extract and sum digits: " + extractAndSumDigits("hello123world456"));
        System.out.println("Extract unique words: " + extractUniqueWords(text));
        System.out.println("Words starting with 'h': " + extractWordsStartingWith(text, 'h'));
    }
}
