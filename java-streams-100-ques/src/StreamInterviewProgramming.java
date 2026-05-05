import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamInterviewProgramming {
        public static void main(String[] args) {

                // Question 1: Given a list of integers, find the k most frequent elements.

                // let's see how groupingBy works - It takes the first argument which is a
                // classifier function to classify the
                // elements of the stream. The input is T which is stream element and output is
                // K which is key of the Map.
                // n->n means the element itself is the key.

                // The second argument is a downstream collector which is applied to the values
                // associated with a given key.
                // It returns a Collector that accepts elements of type T, A -> Intermediate
                // accumulation type used internally
                // by the downstream collector and produces a result of type D.
                List<Integer> numbers = List.of(1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 5, 5, 5, 5, 5);
                int k = 2;
                numbers.stream()
                                // group and count occurrences
                                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                                // convert map to stream of map entry
                                .entrySet().stream()
                                // Sort entries by count in descending order
                                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                                // Take only top 'k' entries
                                .limit(k)
                                // Print result
                                .forEach(e -> System.out.println(e.getKey() + " appears " + e.getValue() + " times"));

                /**
                 * Output:
                 * 5 appears 5 times
                 * 3 appears 4 times
                 **/

                // Question 2: Find the second most frequent element
                List<Integer> numberList = List.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
                numberList.stream()
                                // group and count occurrences
                                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                                // Convert the Map into a Stream of Map.Entry<Integer, Long>
                                .entrySet().stream()
                                // Sort entries by frequency (value) in descending order
                                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                                // Skip the first entry (most frequent element)
                                .skip(1)
                                // Get the second entry (second most frequent element)
                                .findFirst()
                                // Print the result if present
                                .ifPresent(e -> System.out.println(
                                                "Second most frequent: " + e.getKey() + " appears " + e.getValue()
                                                                + " times"));

                /**
                 * Output:
                 * Second most frequent: 3 appears 3 times
                 **/

                // Question 3: Merge two list into one using flatmap
                List<Integer> list1 = List.of(1, 2, 3);
                List<Integer> list2 = List.of(4, 5, 6);
                // create a stream where each element is a list
                List<Integer> collect = Stream.of(list1, list2)
                                // for each element i.e. List<Integer>, it creates a stream of that list i.e.
                                // Stream<Integer>
                                // and then flatten those inner streams into one continuous stream
                                .flatMap(Collection::stream)
                                // Collect results into a List<Integer>
                                .toList();
                System.out.println(collect);

                /**
                 * Output:
                 * [1, 2, 3, 4, 5, 6]
                 **/

                /**
                 * // Top scorer student in maths:
                 * Optional<Student> topScorer = departments.stream()
                 * .flatMap(dept -> dept.getClasses().stream()) // Flatten Dept -> Classes
                 * .flatMap(cls -> cls.getStudents().stream()) // Flatten Class -> Students
                 * .max(Comparator.comparingInt(s -> s.getScores().get("maths"))); // Find max
                 * 
                 * // Top 3 scorers using Java Streams:
                 * List<Student> top3Scorers = departments.stream()
                 * .flatMap(dept -> dept.getClasses().stream())
                 * .flatMap(cls -> cls.getStudents().stream()) // Sort descending by maths score
                 * .sorted(Comparator.comparingInt((Student s) ->
                 * s.getScores().get("maths")).reversed())
                 * .limit(3) // Take only the first three
                 * .collect(Collectors.toList());
                 * 
                 * // Top 3 scorers by departments:
                 * Map<String, List<Student>> top3ScorersByDept = departments.stream()
                 * .collect(Collectors.toMap(
                 * Department::getName,
                 * dept -> dept.getClasses().stream()
                 * .flatMap(cls -> cls.getStudents().stream())
                 * .sorted(Comparator.comparingInt((Student s) -> s
                 * .getScores().get("maths")).reversed())
                 * .limit(3)
                 * .collect(Collectors.toList())));
                 */

                // Question 4: Find common elements from two lists using streams
                List<Integer> list3 = List.of(1, 2, 3);
                List<Integer> list4 = List.of(3, 4, 5);
                // create a stream of list3
                list3.stream()
                                // filter elements that are also in list4
                                .filter(list4::contains)
                                // print each common element
                                .forEach(System.out::println);

                /**
                 * Output:
                 * 3
                 **/

                // Question 5: Convert a list of strings to a map with string lengths as values
                List<String> strings = List.of("apple", "banana", "cherry");
                strings.stream()
                                // Collectors.toMap takes two functions - first function is for key and second
                                // function is for value
                                // If duplicate keys occur, toMap() throws IllegalStateException
                                .collect(Collectors.toMap(s -> s, String::length))
                                /*
                                 * .collect(Collectors.groupingBy(
                                 * word -> word, // Key: The string itself
                                 * Collectors.summingInt(String::length) // Value: The length
                                 * ));
                                 */
                                // Iterate through the map and print key-value pairs
                                .forEach((key, value) -> System.out.println(key + " has length " + value));

                /**
                 * Output:
                 * banana has length 6
                 * apple has length 5
                 * cherry has length 6
                 **/

                // Question 6: Calculate the sum, product, min and max of all integers in a list
                List<Integer> nums = List.of(1, 2, 3, 4);
                Integer reduceSum = nums.stream()
                                // reduce() is used when we want to combine all elements of a stream into a
                                // single value (e.g., sum,
                                // product, minimum, maximum, concatenation).
                                // The first argument is the identity value (1 for multiplication, 0 for sum).
                                // The second argument is a
                                // accumulator i.e. Function that combines the current result (a) with the next
                                // element (b)
                                // For identity version: Always return a value (but may be just the identity if
                                // stream is empty).
                                // No identity version: Return an Optional describing the reduced value, if any.
                                .reduce(0, Integer::sum);
                System.out.println(reduceSum);

                Integer reduceProduct = nums.stream()
                                .reduce(1, (a, b) -> a * b);
                System.out.println(reduceProduct);

                Integer reduceMax = nums.stream()
                                .reduce(Integer::max).orElse(Integer.MIN_VALUE);
                System.out.println(reduceMax);

                Integer reduceMin = nums.stream()
                                .reduce(Integer::min).orElse(Integer.MAX_VALUE);
                System.out.println(reduceMin);

                List<String> givenWords = List.of("Java", "Streams", "are", "powerful");
                String reduceString = givenWords.stream()
                                .reduce(String::concat).orElse("");
                System.out.println(reduceString);

                /**
                 * Output:
                 * 10
                 * 24
                 * 4
                 * 1
                 * JavaStreamsarepowerful
                 **/

                // Question 7: Filter out non-prime numbers using streams
                List<Integer> numList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                List<Integer> collect1 = numList.stream()
                                // we are excluding 1 because 1 is neither prime nor composite. No need to check
                                // divisors beyond sqrt(n)
                                // because if n has a factor greater than sqrt(n), it must also have one smaller
                                // than sqrt(n).
                                // Ensures no number in the range divides n evenly. if none divides n, then n is
                                // prime.
                                .filter(n -> n > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(n))
                                                .noneMatch(i -> n % i == 0))
                                .collect((Collectors.toList()));
                System.out.println(collect1);

                /**
                 * Output:
                 * [2, 3, 5, 7]
                 **/

                // Question 8: Partition strings by length: short (<5), medium (5-10), long
                // (>10)
                List<String> words = List.of("apple", "banana", "cherry", "date", "fig", "grapefruit", "kiwi");
                words.stream()
                                // Takes a classification function to decide the key which is either "short",
                                // "medium" or "long"
                                // Uses a downstream collector (second argument, optional). If not provided,
                                // defaults to Collectors
                                // .toList().
                                .collect(Collectors.groupingBy(word -> {
                                        if (word.length() < 5)
                                                return "short";
                                        else if (word.length() <= 10)
                                                return "medium";
                                        else
                                                return "long";
                                }))
                                .forEach((key, value) -> System.out.println(key + ": " + value));

                /**
                 * Output:
                 * short: [date, fig, kiwi]
                 * medium: [apple, banana, cherry, grapefruit]
                 */

                // Question 9: Find repeating characters in a string
                String input = "programming";
                // input.chars() returns an IntStream of Unicode code points (integers), not
                // char objects.
                // .mapToObj(c -> (char) c) converts each int code point to its corresponding
                // Character object and now we have
                // a Stream<Character>.
                List<Character> collect2 = input.chars().mapToObj(c -> (char) c)
                                // Groups characters by themselves (c -> c) and counts their occurrences.
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                                .entrySet().stream()
                                // Filters out characters that appear more than once (i.e., repeating
                                // characters).
                                .filter(e -> e.getValue() > 1)
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList());
                System.out.println(collect2);

                /**
                 * Output:
                 * [r, g, m]
                 */

                // Question 10: Find the first recurring element in a list
                List<Integer> nums1 = List.of(1, 2, 3, 4, 5, 3, 6, 7);
                nums1.stream()
                                // if the index of first occurrence is not equal to index of last occurrence, it
                                // means the element is
                                // repeating
                                .filter(n -> nums1.indexOf(n) != nums1.lastIndexOf(n))
                                .findFirst()
                                .ifPresent(System.out::println);

                /**
                 * Output:
                 * 3
                 */

                // Question 11: Find longest common prefix of a list of strings
                List<String> stringList = List.of("flower", "flow", "flight");
                String prefix = stringList.stream()
                                // reduce combine elements pair wise using the given lambda. Here we are
                                // comparing two strings
                                // flower and flow and the output string is flow. Then flow is compared with
                                // flight and the output
                                // string is fl which is the longest common prefix
                                .reduce((s1, s2) -> {
                                        int i = 0;
                                        while (i < s1.length() && i < s2.length() && s1.charAt(i) == s2.charAt(i)) {
                                                i++;
                                        }
                                        return s1.substring(0, i);
                                }).orElse("");
                System.out.println(prefix);

                /**
                 * Output:
                 * fl
                 */

                // Question 12: Create a map of initials to concatenated names
                List<String> names = List.of("Alice", "Anil", "Bob", "Charlie", "David", "Eve", "Frank");
                Map<Character, String> initialsMap = names.stream()
                                // Here the classifier function is name -> name.charAt(0) which extracts the
                                // first character of each
                                // name to be used as the key in the resulting map.
                                // The downstream collector is Collectors.joining(", ") which concatenates all
                                // names that share the same
                                // initial into a single string, separated by commas.
                                .collect(Collectors.groupingBy(name -> name.charAt(0), Collectors.joining(", ")));
                initialsMap.forEach((initial, nameList) -> System.out.println(initial + ": " + nameList));

                /**
                 * Output:
                 * A: Alice, Anil
                 * B: Bob
                 * C: Charlie
                 * D: David
                 * E: Eve
                 * F: Frank
                 */

                // Question 13: Find the longest word in a list of strings
                List<String> wordList = List.of("apple", "banana", "cherry", "date", "fig", "grapefruit", "kiwi");
                // convert the given list into Stream<String>
                wordList.stream()
                                // reduce takes a binary operator which takes two strings and returns the longer
                                // string
                                // and repeat this for all pairs of elements in the stream
                                .reduce((s1, s2) -> s1.length() >= s2.length() ? s1 : s2)
                                .ifPresent(System.out::println);

                /**
                 * Output:
                 * grapefruit
                 */

                // Question 14: Find the shortest word in a list of strings
                // Convert the list into a stream using wordList.stream()
                wordList.stream()
                                // reduce() takes a binary operator: a function that combines two elements into
                                // one.
                                // It compares two strings (s1 and s2) and returns the shorter one. Repeat for
                                // all pairs in the stream.
                                // Since no identity is provided, reduce() returns an Optional<String>.
                                // ifPresent() prints the shortest word if the list is not empty.
                                .reduce((s1, s2) -> s1.length() <= s2.length() ? s1 : s2)
                                .ifPresent(System.out::println);

                /**
                 * Output:
                 * fig
                 */

                // Question 15: Find the average length of strings in a list
                // convert List<String> into a Stream<String>
                wordList.stream()
                                // This will convert each string in the stream to its length (int) and produce
                                // an IntStream
                                .mapToInt(String::length)
                                // Calculate the average of the lengths in the IntStream. Returns an
                                // OptionalDouble.
                                .average()
                                // If the average is present (i.e., the list was not empty), print it.
                                .ifPresent(avg -> System.out.println("Average length: " + avg));

                /**
                 * Output:
                 * Average length: 5.428571428571429
                 */

                // Question 16: Find the sum of lengths of all strings in a list
                // Converts the List<String> into a Stream<String>
                int totalLength = wordList.stream()
                                // Maps each string to its length, producing an IntStream of lengths
                                .mapToInt(String::length)
                                //
                                .sum();
                System.out.println("Total length: " + totalLength);

                /**
                 * Output:
                 * Total length: 38
                 */

                // Question 17: Find the count of strings that start with a specific letter
                long count = wordList.stream()
                                // keeps only strings that start with the letter 'a'
                                .filter(s -> s.startsWith("a"))
                                // counts the number of elements in the filtered stream
                                .count();
                System.out.println("Total count: " + count);

                /**
                 * Output:
                 * Total count: 1
                 */

                // Question 18: Find the distinct strings in a list
                List<String> duplicatesList = List.of("apple", "banana", "apple", "cherry", "banana", "date");
                List<String> distinctList = duplicatesList.stream()
                                // distinct() filters out duplicate elements, keeping only unique ones
                                .distinct()
                                // Collects the unique elements into a new List<String>
                                .toList();
                System.out.println(distinctList);

                /**
                 * Output:
                 * [apple, banana, cherry, date]
                 */

                // Question 19: Find the second longest word in a list of strings
                // Converts the list of String into Stream<String>
                wordList.stream()
                                // Sort the strings in descending order based on their lengths
                                .sorted((s1, s2) -> Integer.compare(s2.length(), s1.length()))
                                // skips the first element (longest word)
                                .skip(1)
                                // retrieves the next element (second longest word) if it exists
                                .findFirst()
                                // prints the second longest word if present
                                .ifPresent(System.out::println);

                /**
                 * Output:
                 * banana
                 */

                // Question 20: Identify anagrams in a list of strings
                List<String> anagrams = List.of("listen", "silent", "enlist", "inlets", "google", "gooogle");
                // what is anagram? An anagram is a word or phrase formed by rearranging the
                // letters of a different word or
                // phrase, typically using all the original letters exactly once.
                Map<String, List<String>> anagramMap = anagrams.stream()
                                // here we need to do groupingBy because anagrams will have the same key when
                                // sorted
                                // And the downstream collector is Collectors.toList() which collects all
                                // anagrams into a list
                                .collect(Collectors.groupingBy(word -> {
                                        char[] chars = word.toCharArray();
                                        java.util.Arrays.sort(chars);
                                        return new String(chars);
                                }));
                // Print only those lists which have more than one anagram
                anagramMap.values().stream()
                                .filter(list -> list.size() > 1)
                                .forEach(System.out::println);

                /**
                 * Output:
                 * [listen, silent, enlist, inlets]
                 */

                // Question 21: Remove consecutive duplicates from a list using streams
                List<Integer> consecutiveList = List.of(1, 1, 2, 2, 3, 3, 3, 4, 4, 5);
                // Here we are creating an index stream from 0 to size of the list
                List<Integer> result = IntStream.range(0, consecutiveList.size())
                                // always keep the first element or keep the element if it is not equal to the
                                // previous element
                                .filter(i -> i == 0 || !consecutiveList.get(i).equals(consecutiveList.get(i - 1)))
                                .mapToObj(consecutiveList::get)
                                .toList();
                System.out.println(result);

                /**
                 * Output:
                 * [1, 2, 3, 4, 5]
                 */

                // Question 22: Group words by their length
                List<String> wordsList = List.of("apple", "banana", "cherry", "date", "fig", "grapefruit", "kiwi");
                // Converts the list into a Stream<String>
                Map<Integer, List<String>> collect3 = wordsList.stream()
                                // Groups the strings by their lengths (String::length) and collects them into
                                // lists
                                .collect(Collectors.groupingBy(String::length, Collectors.toList()));
                collect3.forEach((length, wordGroup) -> System.out.println(length + ": " + wordGroup));

                /**
                 * Output:
                 * 3: [fig]
                 * 4: [date, kiwi]
                 * 5: [apple]
                 * 6: [banana, cherry]
                 * 10: [grapefruit]
                 */

                // Question 23: Partition numbers into even and odd
                List<Integer> evenAndOddList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                Map<Boolean, List<Integer>> isEvenToListOfNumbersMapping = evenAndOddList.stream()
                                // partition divides elements into two groups based on a predicate (here n -> n
                                // % 2 == 0)
                                // true key will have even numbers and false key will have odd numbers
                                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
                isEvenToListOfNumbersMapping.forEach(
                                (isEven, numberedList) -> System.out.println(isEven + ": " + numberedList));

                /**
                 * Output:
                 * false: [1, 3, 5, 7, 9]
                 * true: [2, 4, 6, 8, 10]
                 */

                // Question 24: Group by length and count words in each group
                List<String> wordsList1 = List.of("apple", "banana", "cherry", "date", "fig", "grapefruit", "kiwi");
                Map<Integer, Long> lengthToCountMap = wordsList1.stream()
                                // groups words by their length and counts how many words fall into each group.
                                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
                lengthToCountMap.forEach((length, count1) -> System.out.println(length + ": " + count1));

                /**
                 * Output:
                 * 3: 1
                 * 4: 2
                 * 5: 1
                 * 6: 2
                 * 10: 1
                 */

                // Question 25: Batch process a large stream in fixed-size chunks (e.g., 100
                // items each)
                List<Integer> largeList = IntStream.rangeClosed(1, 1000).boxed().toList();
                int batchSize = 100;
                for (int i = 0; i < batchSize; i++) {
                        List<Integer> batch = largeList.stream()
                                        .skip(i * batchSize)
                                        .limit(batchSize)
                                        .toList();
                        if (batch.isEmpty()) {
                                break;
                        }
                        System.out.println("Processing batch " + (i + 1) + ": " + batch);
                }

                /**
                 * Output:
                 * Processing batch 1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
                 * 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
                 * 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54,
                 * 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73,
                 * 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92,
                 * 93, 94, 95, 96, 97, 98, 99, 100]
                 * Processing batch 2: [101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111,
                 * 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126,
                 * 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141,
                 * 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156,
                 * 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171,
                 * 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186,
                 * 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200]
                 * Processing batch 3: [201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211,
                 * 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226,
                 * 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241,
                 * 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256,
                 * 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271,
                 * 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286,
                 * 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300]
                 * Processing batch 4: [301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311,
                 * 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326,
                 * 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341,
                 * 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356,
                 * 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371,
                 * 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386,
                 * 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400]
                 * Processing batch 5: [401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411,
                 * 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426,
                 * 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441,
                 * 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456,
                 * 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471,
                 * 472, 473, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486,
                 * 487, 488, 489, 490, 491, 492, 493, 494, 495, 496, 497, 498, 499, 500]
                 * Processing batch 6: [501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511,
                 * 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526,
                 * 527, 528, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541,
                 * 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 556,
                 * 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 568, 569, 570, 571,
                 * 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586,
                 * 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597, 598, 599, 600]
                 * Processing batch 7: [601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611,
                 * 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626,
                 * 627, 628, 629, 630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 641,
                 * 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 654, 655, 656,
                 * 657, 658, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668, 669, 670, 671,
                 * 672, 673, 674, 675, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686,
                 * 687, 688, 689, 690, 691, 692, 693, 694, 695, 696, 697, 698, 699, 700]
                 * Processing batch 8: [701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711,
                 * 712, 713, 714, 715, 716, 717, 718, 719, 720, 721, 722, 723, 724, 725, 726,
                 * 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 738, 739, 740, 741,
                 * 742, 743, 744, 745, 746, 747, 748, 749, 750, 751, 752, 753, 754, 755, 756,
                 * 757, 758, 759, 760, 761, 762, 763, 764, 765, 766, 767, 768, 769, 770, 771,
                 * 772, 773, 774, 775, 776, 777, 778, 779, 780, 781, 782, 783, 784, 785, 786,
                 * 787, 788, 789, 790, 791, 792, 793, 794, 795, 796, 797, 798, 799, 800]
                 * Processing batch 9: [801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811,
                 * 812, 813, 814, 815, 816, 817, 818, 819, 820, 821, 822, 823, 824, 825, 826,
                 * 827, 828, 829, 830, 831, 832, 833, 834, 835, 836, 837, 838, 839, 840, 841,
                 * 842, 843, 844, 845, 846, 847, 848, 849, 850, 851, 852, 853, 854, 855, 856,
                 * 857, 858, 859, 860, 861, 862, 863, 864, 865, 866, 867, 868, 869, 870, 871,
                 * 872, 873, 874, 875, 876, 877, 878, 879, 880, 881, 882, 883, 884, 885, 886,
                 * 887, 888, 889, 890, 891, 892, 893, 894, 895, 896, 897, 898, 899, 900]
                 * Processing batch 10: [901, 902, 903, 904, 905, 906, 907, 908, 909, 910, 911,
                 * 912, 913, 914, 915, 916, 917, 918, 919, 920, 921, 922, 923, 924, 925, 926,
                 * 927, 928, 929, 930, 931, 932, 933, 934, 935, 936, 937, 938, 939, 940, 941,
                 * 942, 943, 944, 945, 946, 947, 948, 949, 950, 951, 952, 953, 954, 955, 956,
                 * 957, 958, 959, 960, 961, 962, 963, 964, 965, 966, 967, 968, 969, 970, 971,
                 * 972, 973, 974, 975, 976, 977, 978, 979, 980, 981, 982, 983, 984, 985, 986,
                 * 987, 988, 989, 990, 991, 992, 993, 994, 995, 996, 997, 998, 999, 1000]
                 */

                // Question 26: Create a time-windowed stream from timestamped events
                List<Event> events = List.of(
                                new Event(1620000000000L, "Login"),
                                new Event(1620000000100L, "Click"),
                                new Event(1620000060000L, "Purchase"), // + 1 min
                                new Event(1620000120000L, "Logout") // + 2 min
                );

                Map<Long, List<Event>> windows = events.stream()
                                // groupingBy classifier function divides events into time windows of 1 minute
                                // (60000 ms)
                                // e.getTimestamp() / 60000 gives the minute window for each event
                                // downstream collector is Collectors.toList() which collects events in the same
                                // window into a list
                                .collect(Collectors.groupingBy(e -> e.getTimestamp() / 60000)); // Group by minute
                windows.forEach((window, eventList) -> {
                        System.out.println(
                                        "Window starting at " + (window * 60000) + ": "
                                                        + eventList.stream().map(Event::getName).toList());
                });

                /**
                 * Output:
                 * Window starting at 1620000120000: [Logout]
                 * Window starting at 1620000060000: [Purchase]
                 * Window starting at 1620000000000: [Login, Click]
                 */

                // Question 27: Partition Strings Based on Uppercase Start
                List<String> mixedCaseWords = List.of("Apple", "banana", "Cherry", "date", "Elderberry", "fig",
                                "Grape");
                // partitioningBy is used when there are only two groups based on a predicate
                // Character.isUpperCase(word.charAt(0)) checks if the first character of the
                // word is uppercase
                // The result is a Map<Boolean, List<String>> where true key has words starting
                // with uppercase and false key
                // has words starting with lowercase
                mixedCaseWords.stream()
                                .collect(Collectors.partitioningBy(word -> Character.isUpperCase(word.charAt(0))))
                                .forEach((isUppercase, wordGroup) -> System.out
                                                .println(isUppercase + ": " + wordGroup));

                /**
                 * Output:
                 * false: [banana, date, fig]
                 * true: [Apple, Cherry, Elderberry, Grape]
                 */

                // Question 28: Partition Characters into Vowels & Consonants
                List<Character> characters = List.of('a', 'b', 'e', 'f', 'i', 'j', 'o', 'p', 'u', 'v');
                Map<Boolean, List<Character>> collect4 = characters.stream()
                                // here we are using partitioningBy because there are only two groups - vowels
                                // and consonants
                                // aeiou.indexOf(...) >= 0 checks if the character is a vowel (true) or
                                // consonant (false)
                                // return Map<Boolean, List<Character>>
                                .collect(Collectors
                                                .partitioningBy(ch -> "aeiou".indexOf(Character.toLowerCase(ch)) >= 0));
                collect4.forEach((isVowel, charGroup) -> System.out.println(isVowel + ": " + charGroup));

                /**
                 * Output:
                 * true: [a, e, i, o, u]
                 * false: [b, f, j, p, v]
                 */
        }

        static class Event {
                private final long timestamp;
                private final String name;

                Event(long timestamp, String name) {
                        this.timestamp = timestamp;
                        this.name = name;
                }

                public long getTimestamp() {
                        return timestamp;
                }

                public String getName() {
                        return name;
                }
        }
}
