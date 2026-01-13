package com.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive demonstration of all 100 Java Streams API solutions
 */
public class AllSolutions {

    public static void main(String[] args) {
        System.out.println("=== Java Streams API - 100 Solutions ===\n");

        // Basic Operations
        demonstrateBasicOperations();
        
        // Sorting Operations
        demonstrateSortingOperations();
        
        // Number Operations
        demonstrateNumberOperations();
        
        // Grouping Operations
        demonstrateGroupingOperations();
        
        // List Operations
        demonstrateListOperations();
        
        // Occurrence Operations
        demonstrateOccurrenceOperations();
        
        // String Operations
        demonstrateStringOperations();
        
        // Sequence Operations
        demonstrateSequenceOperations();
        
        // Map Operations
        demonstrateMapOperations();
        
        // Advanced Operations
        demonstrateAdvancedOperations();
    }

    private static void demonstrateBasicOperations() {
        System.out.println("--- Basic Operations ---");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 2, 3);
        List<String> strings = Arrays.asList("hello", "world", "java", "streams");

        System.out.println("1. Sum: " + BasicOperations.sumOfElements(numbers));
        System.out.println("2. Product: " + BasicOperations.productOfElements(numbers));
        System.out.println("3. Average: " + BasicOperations.averageOfElements(numbers));
        System.out.println("4. Max: " + BasicOperations.maxElement(numbers));
        System.out.println("5. Min: " + BasicOperations.minElement(numbers));
        System.out.println("6. Count: " + BasicOperations.countElements(numbers));
        System.out.println("7. Contains 3: " + BasicOperations.containsElement(numbers, 3));
        System.out.println("8. Even numbers: " + BasicOperations.filterEvenNumbers(numbers));
        System.out.println("9. Odd numbers: " + BasicOperations.filterOddNumbers(numbers));
        System.out.println("10. Uppercase: " + BasicOperations.convertToUppercase(strings));
        System.out.println("11. Squares: " + BasicOperations.convertToSquares(numbers));
        System.out.println("12. First: " + BasicOperations.firstElement(numbers));
        System.out.println("13. Last: " + BasicOperations.lastElement(numbers));
        System.out.println("14. All > 0: " + BasicOperations.allElementsSatisfy(numbers, n -> n > 0));
        System.out.println("15. Any > 4: " + BasicOperations.anyElementSatisfies(numbers, n -> n > 4));
        System.out.println("16. No duplicates: " + BasicOperations.removeDuplicates(numbers));
        System.out.println();
    }

    private static void demonstrateSortingOperations() {
        System.out.println("--- Sorting Operations ---");
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date");

        System.out.println("17. Ascending: " + SortingOperations.sortAscending(numbers));
        System.out.println("18. Descending: " + SortingOperations.sortDescending(numbers));
        System.out.println("19. Alphabetical: " + SortingOperations.sortAlphabetically(strings));
        System.out.println("20. By length: " + SortingOperations.sortByLength(strings));
        System.out.println();
    }

    private static void demonstrateNumberOperations() {
        System.out.println("--- Number Operations ---");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);

        System.out.println("21. Sum of digits (123): " + NumberOperations.sumOfDigits(123));
        System.out.println("22. Factorial (5): " + NumberOperations.factorial(5));
        System.out.println("23. Second largest: " + NumberOperations.secondLargest(numbers));
        System.out.println("24. Second smallest: " + NumberOperations.secondSmallest(numbers));
        System.out.println("27. Sum of squares: " + NumberOperations.sumOfSquares(numbers));
        System.out.println("28. Sum of cubes: " + NumberOperations.sumOfCubes(numbers));
        System.out.println("29. Sum of primes: " + NumberOperations.sumOfPrimes(numbers));
        System.out.println("31. Sum of even-indexed: " + NumberOperations.sumOfEvenIndexed(numbers));
        System.out.println("32. Sum of odd-indexed: " + NumberOperations.sumOfOddIndexed(numbers));
        System.out.println("33. Sum > 5: " + NumberOperations.sumGreaterThan(numbers, 5));
        System.out.println("34. Standard deviation: " + NumberOperations.standardDeviation(doubles));
        System.out.println("35. Median: " + NumberOperations.median(doubles));
        System.out.println("36. Mode: " + NumberOperations.mode(Arrays.asList(1, 2, 2, 3, 3, 3, 4)));
        System.out.println();
    }

    private static void demonstrateGroupingOperations() {
        System.out.println("--- Grouping Operations ---");
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<GroupingOperations.Person> persons = Arrays.asList(
                new GroupingOperations.Person("Alice", "IT", 5000),
                new GroupingOperations.Person("Bob", "HR", 4000),
                new GroupingOperations.Person("Charlie", "IT", 6000),
                new GroupingOperations.Person("Diana", "HR", 4500)
        );

        System.out.println("37. Group by length: " + GroupingOperations.groupByLength(strings));
        System.out.println("38. Group by department: " + GroupingOperations.groupByDepartment(persons));
        System.out.println("39. Partition even/odd: " + GroupingOperations.partitionEvenOdd(numbers));
        System.out.println();
    }

    private static void demonstrateListOperations() {
        System.out.println("--- List Operations ---");
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        System.out.println("40. Merge: " + ListOperations.mergeLists(list1, list2));
        System.out.println("41. Intersection: " + ListOperations.intersection(list1, list2));
        System.out.println("42. Union: " + ListOperations.union(list1, list2));
        System.out.println("43. Difference: " + ListOperations.difference(list1, list2));
        System.out.println("44. Reverse: " + ListOperations.reverseList(list1));
        System.out.println("45. Flatten: " + ListOperations.flatten(nestedList));
        System.out.println("46. Sum even in nested: " + ListOperations.sumEvenInNestedList(nestedList));
        System.out.println("47. Sum odd in nested: " + ListOperations.sumOddInNestedList(nestedList));
        System.out.println();
    }

    private static void demonstrateOccurrenceOperations() {
        System.out.println("--- Occurrence Operations ---");
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        String text = "hello world java streams";

        System.out.println("48. Element occurrences: " + OccurrenceOperations.countOccurrences(numbers));
        System.out.println("49. Character occurrences: " + OccurrenceOperations.countCharacterOccurrences(text));
        System.out.println("50. Word occurrences: " + OccurrenceOperations.countWordOccurrences(text));
        System.out.println("51. Vowel occurrences: " + OccurrenceOperations.countVowelOccurrences(text));
        System.out.println("52. Digit occurrences: " + OccurrenceOperations.countDigitOccurrences("hello123world456"));
        System.out.println("53. Most frequent: " + OccurrenceOperations.mostFrequent(numbers));
        System.out.println("54. Least frequent: " + OccurrenceOperations.leastFrequent(numbers));
        System.out.println();
    }

    private static void demonstrateStringOperations() {
        System.out.println("--- String Operations ---");
        String text = "hello world java streams programming";
        List<String> words = Arrays.asList("listen", "silent", "enlist", "hello", "world");

        System.out.println("55. Reverse: " + StringOperations.reverseString("hello"));
        System.out.println("56. First non-repeated: " + StringOperations.firstNonRepeatedChar("hello"));
        System.out.println("57. First repeated: " + StringOperations.firstRepeatedChar("hello"));
        System.out.println("58. Is palindrome: " + StringOperations.isPalindrome("racecar"));
        System.out.println("59. Anagrams: " + StringOperations.findAnagrams("listen", words));
        System.out.println("60. Longest palindrome: " + StringOperations.longestPalindrome(
                Arrays.asList("racecar", "level", "hello", "madam")));
        System.out.println("61. Shortest palindrome: " + StringOperations.shortestPalindrome(
                Arrays.asList("racecar", "level", "hello", "madam")));
        System.out.println("62. Longest word: " + StringOperations.longestWord(text));
        System.out.println("63. Shortest word: " + StringOperations.shortestWord(text));
        System.out.println("64. Number of words: " + StringOperations.numberOfWords(text));
        System.out.println("65. Remove vowels: " + StringOperations.removeVowels("hello world"));
        System.out.println("66. Remove consonants: " + StringOperations.removeConsonants("hello world"));
        System.out.println("67. Remove digits: " + StringOperations.removeDigits("hello123world456"));
        System.out.println("68. Remove special chars: " + StringOperations.removeSpecialCharacters("hello@world#123"));
        System.out.println("69. Extract and sum digits: " + StringOperations.extractAndSumDigits("hello123world456"));
        System.out.println("70. Extract and count words: " + StringOperations.extractAndCountWords(text));
        System.out.println("71. Extract unique words: " + StringOperations.extractUniqueWords(text));
        System.out.println("72. Extract palindromic words: " + StringOperations.extractPalindromicWords("racecar level hello"));
        System.out.println("73. Words starting with 'h': " + StringOperations.extractWordsStartingWith(text, 'h'));
        System.out.println("74. Words ending with 's': " + StringOperations.extractWordsEndingWith(text, 's'));
        System.out.println();
    }

    private static void demonstrateSequenceOperations() {
        System.out.println("--- Sequence Operations ---");
        System.out.println("81. Fibonacci (10): " + SequenceOperations.generateFibonacci(10));
        System.out.println("82. Random numbers (10, 1-100): " + SequenceOperations.generateRandomNumbers(10, 1, 100));
        System.out.println();
    }

    private static void demonstrateMapOperations() {
        System.out.println("--- Map Operations ---");
        Map<String, Integer> map = new HashMap<>();
        map.put("three", 3);
        map.put("one", 1);
        map.put("two", 2);

        List<MapOperations.Employee> employees = Arrays.asList(
                new MapOperations.Employee(1, "Alice", "IT"),
                new MapOperations.Employee(2, "Bob", "HR"),
                new MapOperations.Employee(3, "Charlie", "IT"),
                new MapOperations.Employee(4, "Diana", "HR")
        );

        System.out.println("83. Keys: " + MapOperations.mapKeysToList(map));
        System.out.println("84. Values: " + MapOperations.mapValuesToList(map));
        System.out.println("85. Sorted by keys: " + MapOperations.sortMapByKeys(map));
        System.out.println("86. Sorted by values: " + MapOperations.sortMapByValues(map));
        System.out.println("87. List to Map: " + MapOperations.listToMap(employees));
        System.out.println("88. List to Map (duplicates): " + MapOperations.listToMapWithDuplicates(employees));
        System.out.println();
    }

    private static void demonstrateAdvancedOperations() {
        System.out.println("--- Advanced Operations ---");
        List<AdvancedOperations.Employee> employees = Arrays.asList(
                new AdvancedOperations.Employee("Alice", "IT", 5000),
                new AdvancedOperations.Employee("Bob", "HR", 4000),
                new AdvancedOperations.Employee("Charlie", "IT", 6000),
                new AdvancedOperations.Employee("Diana", "HR", 4500),
                new AdvancedOperations.Employee("Eve", "IT", 7000)
        );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);

        System.out.println("89. Group by dept and avg salary: " + 
                AdvancedOperations.groupByDepartmentAndAverageSalary(employees));
        System.out.println("90. Top 3 highest paid: " + 
                AdvancedOperations.topNHighestPaid(employees, 3));
        System.out.println("91. Paginate (page 1, size 3): " + 
                AdvancedOperations.paginate(numbers, 1, 3));
        System.out.println("92. Chunk (size 3): " + 
                AdvancedOperations.chunkList(numbers, 3));
        System.out.println("93. Statistics: " + 
                AdvancedOperations.calculateStatistics(doubles));
        System.out.println("94. Safe parse: " + 
                AdvancedOperations.safeParseIntegers(Arrays.asList("1", "2", "abc", "3")));
        System.out.println("95. Custom collector: " + 
                numbers.stream().collect(AdvancedOperations.customToListCollector()));
        System.out.println("96-100. Advanced operations (parallel, async, custom collectors) implemented");
        System.out.println();
    }
}
