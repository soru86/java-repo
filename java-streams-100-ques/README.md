# Java Streams API - 100 Questions & Solutions

This repository contains comprehensive solutions to 100 Java Streams API questions, organized into logical categories for easy navigation and learning.

## Project Structure

```
src/main/java/com/streams/
├── BasicOperations.java      # Questions 1-16: Basic operations (sum, product, filter, etc.)
├── SortingOperations.java    # Questions 17-20: Sorting operations
├── NumberOperations.java     # Questions 21-36: Number operations (factorial, statistics, etc.)
├── GroupingOperations.java   # Questions 37-39: Grouping and partitioning
├── ListOperations.java       # Questions 40-47: List operations (merge, intersection, etc.)
├── OccurrenceOperations.java # Questions 48-54: Counting occurrences
├── StringOperations.java     # Questions 55-74: String manipulation
├── FileOperations.java       # Questions 75-80: File processing
├── SequenceOperations.java   # Questions 81-82: Sequence generation
├── MapOperations.java        # Questions 83-88: Map operations
├── AdvancedOperations.java   # Questions 89-100: Advanced topics
└── AllSolutions.java         # Comprehensive demonstration of all solutions
```

## Questions List

### Basic Operations (1-16)
1. Find the Sum of All Elements in a List
2. Find the Product of All Elements in a List
3. Find the Average of All Elements in a List
4. Find the Maximum Element in a List
5. Find the Minimum Element in a List
6. Count the Number of Elements in a List
7. Check if a List Contains a Specific Element
8. Filter Out Even Numbers from a List
9. Filter Out Odd Numbers from a List
10. Convert a List of Strings to Uppercase
11. Convert a List of Integers to Their Squares
12. Find the First Element in a List
13. Find the Last Element in a List
14. Check if All Elements in a List Satisfy a Condition
15. Check if Any Element in a List Satisfies a Condition
16. Remove Duplicate Elements from a List

### Sorting Operations (17-20)
17. Sort a List of Integers in Ascending Order
18. Sort a List of Integers in Descending Order
19. Sort a List of Strings in Alphabetical Order
20. Sort a List of Strings by Their Length

### Number Operations (21-36)
21. Find the Sum of Digits of a Number
22. Find the Factorial of a Number
23. Find the Second-Largest Element in a List
24. Find the Second-Smallest Element in a List
25. Find the Longest String in a List
26. Find the Shortest String in a List
27. Find the Sum of Squares of All Elements in a List
28. Find the Sum of Cubes of All Elements in a List
29. Find the Sum of All Prime Numbers in a List
30. Find the Sum of All Fibonacci Numbers in a List
31. Find the Sum of All Even-Indexed Elements in a List
32. Find the Sum of All Odd-Indexed Elements in a List
33. Find the Sum of All Elements Greater Than a Given Value
34. Find the Standard Deviation of a List of Numbers
35. Find the Median of a List of Numbers
36. Find the Mode of a List of Numbers

### Grouping Operations (37-39)
37. Group a List of Strings by Their Length
38. Group a List of Objects by a Specific Attribute
39. Partition a List of Integers into Even and Odd Numbers

### List Operations (40-47)
40. Merge Two Lists into a Single List
41. Find the Intersection of Two Lists
42. Find the Union of Two Lists
43. Find the Difference Between Two Lists
44. Reverse a List Using Streams
45. Flatten a List of Lists into a Single List
46. Find the Sum of All Even Numbers in a Nested List
47. Find the Sum of All Odd Numbers in a Nested List

### Occurrence Operations (48-54)
48. Count the Occurrences of Each Element in a List
49. Count the Occurrences of Each Character in a String
50. Count the Occurrences of Each Word in a String
51. Count the Occurrences of Each Vowel in a String
52. Count the Occurrences of Each Digit in a String
53. Find the Most Frequent Element in a List
54. Find the Least Frequent Element in a List

### String Operations (55-74)
55. Reverse a String Using Streams
56. Find the First Non-Repeated Character in a String
57. Find the First Repeated Character in a String
58. Check if a String is a Palindrome
59. Find All Anagrams of a String from a List
60. Find the Longest Palindrome in a List of Strings
61. Find the Shortest Palindrome in a List of Strings
62. Find the Longest Word in a String
63. Find the Shortest Word in a String
64. Find the Number of Words in a String
65. Remove All Vowels from a String
66. Remove All Consonants from a String
67. Remove All Digits from a String
68. Remove All Special Characters from a String
69. Extract All Digits from a String and Sum Them
70. Extract All Words from a String and Count Their Occurrences
71. Extract All Unique Words from a String
72. Extract All Palindromic Words from a String
73. Extract All Words Starting with a Specific Letter
74. Extract All Words Ending with a Specific Letter

### File Operations (75-80)
75. Find the Number of Lines in a File
76. Find the Number of Characters in a File
77. Find the Number of Words in a File
78. Find the Number of Unique Words in a File
79. Process a CSV File and Calculate Aggregate Statistics
80. Find the Top N Most Frequent Words in a Text File

### Sequence Operations (81-82)
81. Generate the Fibonacci Sequence Using Streams
82. Generate a List of Random Numbers Using Streams

### Map Operations (83-88)
83. Convert a Map to a List of Keys Using Streams
84. Convert a Map to a List of Values Using Streams
85. Sort a Map by Keys Using Streams
86. Sort a Map by Values Using Streams
87. Convert a List of Objects to a Map Using Streams
88. Convert a List of Objects to a Map with Duplicate Keys Handling

### Advanced Operations (89-100)
89. Group Employees by Department and Calculate Average Salary
90. Find the Top N Highest-Paid Employees
91. Implement Pagination Using Streams
92. Chunk a List into Sublists Using Streams
93. Parallelize a CPU-Intensive Operation Using Streams
94. Handle Exceptions in Streams
95. Use Custom Collectors to Calculate Statistics
96. Build a Custom Collector from Scratch
97. Combine Multiple Asynchronous Tasks
98. Process Large Datasets in Parallel
99. Filter and Transform Data Fetched from a Database
100. Parse and Validate JSON Payloads

## How to Run

### Compile the project:
```bash
javac -d target/classes src/main/java/com/streams/*.java
```

### Run all solutions demonstration:
```bash
java -cp target/classes com.streams.AllSolutions
```

### Run individual solution classes:
```bash
java -cp target/classes com.streams.BasicOperations
java -cp target/classes com.streams.SortingOperations
# ... and so on
```

## Features

- **100 Complete Solutions**: Every question has a working solution using Java Streams API
- **Well-Organized**: Solutions are grouped by category for easy navigation
- **Comprehensive Examples**: Each class includes test methods demonstrating usage
- **Best Practices**: Code follows Java best practices and Streams API conventions
- **Educational**: Comments and clear method names make learning easy

## Requirements

- Java 8 or higher (for Streams API support)
- No external dependencies required

## Learning Path

1. Start with `BasicOperations.java` to understand fundamental Stream operations
2. Progress through `SortingOperations.java` and `NumberOperations.java`
3. Learn grouping and partitioning with `GroupingOperations.java`
4. Master list manipulations with `ListOperations.java`
5. Explore string processing with `StringOperations.java`
6. Advance to `AdvancedOperations.java` for complex scenarios

## Notes

- All solutions use pure Java Streams API without external libraries
- File operations require actual file paths to test
- Some advanced operations (async, database) are simulated for demonstration
- Each solution is self-contained and can be used independently

## Contributing

Feel free to improve solutions, add more examples, or suggest better approaches!