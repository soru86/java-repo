# Java Interview Questions - Implementation

This repository contains implementations of all programming questions from the "Grokking the Java Interview" book by Javin Paul.

Each program is organized in its own subfolder with a `Main` class for demonstration and the actual implementation class.

## 📁 Program Structure

### 1. **01_fibonacci** - Fibonacci Series Program
- **Files**: `FibonacciSeries.java`, `Main.java`
- **Description**: Implementation of Fibonacci series using iterative, recursive, and memoized approaches
- **Run**: `cd 01_fibonacci && javac *.java && java Main`

### 2. **02_prime_number** - Prime Number Checker
- **Files**: `PrimeNumberChecker.java`, `Main.java`
- **Description**: Check if a number is prime using naive and optimized approaches, plus Sieve of Eratosthenes
- **Run**: `cd 02_prime_number && javac *.java && java Main`

### 3. **03_swap_numbers** - Swap Two Numbers Without Temp Variable
- **Files**: `SwapNumbers.java`, `Main.java`
- **Description**: Multiple methods to swap two numbers without using temporary variable (XOR, arithmetic, multiplication)
- **Run**: `cd 03_swap_numbers && javac *.java && java Main`

### 4. **04_linked_list_loop** - Check if Linked List Contains Loop
- **Files**: `LinkedList.java`, `Main.java`
- **Description**: Floyd's Cycle Detection Algorithm (Tortoise and Hare) to detect loops in linked list
- **Run**: `cd 04_linked_list_loop && javac *.java && java Main`

### 5. **05_reverse_string** - Reverse String Without Using API
- **Files**: `StringReverser.java`, `Main.java`
- **Description**: Multiple approaches to reverse a string without using built-in APIs
- **Run**: `cd 05_reverse_string && javac *.java && java Main`

### 6. **06_max_min_array** - Find Maximum and Minimum in Array
- **Files**: `ArrayMaxMin.java`, `Main.java`
- **Description**: Find max and min values in array using linear search, divide-conquer, and Stream API
- **Run**: `cd 06_max_min_array && javac *.java && java Main`

### 7. **07_reverse_array** - Reverse Array In Place
- **Files**: `ArrayReverser.java`, `Main.java`
- **Description**: Reverse an array in place using two pointers, XOR, and recursion
- **Run**: `cd 07_reverse_array && javac *.java && java Main`

### 8. **08_reverse_number** - Reverse a Number in Java
- **Files**: `NumberReverser.java`, `Main.java`
- **Description**: Reverse a number using arithmetic operations, string conversion, and recursion
- **Run**: `cd 08_reverse_number && javac *.java && java Main`

### 9. **09_factorial** - Calculate Factorial in Java
- **Files**: `FactorialCalculator.java`, `Main.java`
- **Description**: Calculate factorial using iterative, recursive, tail-recursive, and BigInteger approaches
- **Run**: `cd 09_factorial && javac *.java && java Main`

### 10. **10_producer_consumer** - Producer Consumer Problem
- **Files**: `ProducerConsumer.java`, `Main.java`
- **Description**: Classic Producer-Consumer problem solved using wait/notify and BlockingQueue
- **Run**: `cd 10_producer_consumer && javac *.java && java Main`

### 11. **11_middle_linked_list** - Find Middle Element of Linked List in One Pass
- **Files**: `MiddleElementFinder.java`, `Main.java`
- **Description**: Two-pointer technique (tortoise and hare) to find middle element in single pass
- **Run**: `cd 11_middle_linked_list && javac *.java && java Main`

### 12. **12_avoid_deadlock** - Avoid Deadlock in Java
- **Files**: `DeadlockPrevention.java`, `Main.java`
- **Description**: Techniques to prevent deadlock: lock ordering and timeout-based approaches
- **Run**: `cd 12_avoid_deadlock && javac *.java && java Main`

### 13. **13_thread_sequence** - Ensure Thread Sequence T1, T2, T3
- **Files**: `ThreadSequencer.java`, `Main.java`
- **Description**: Multiple approaches to ensure threads execute in specific sequence (join, wait/notify, CountDownLatch, Semaphore)
- **Run**: `cd 13_thread_sequence && javac *.java && java Main`

### 14. **14_immutable_object** - Create Immutable Object in Java
- **Files**: `ImmutablePerson.java`, `Main.java`
- **Description**: Complete implementation of immutable class following all best practices
- **Run**: `cd 14_immutable_object && javac *.java && java Main`

### 15. **15_thread_safe_singleton** - Thread-Safe Singleton Pattern
- **Files**: `SingletonPattern.java`, `Main.java`
- **Description**: Multiple implementations of thread-safe Singleton (Eager, Lazy, Double-Checked, Bill Pugh, Enum)
- **Run**: `cd 15_thread_safe_singleton && javac *.java && java Main`

### 16. **16_lru_cache** - LRU Cache Implementation using Generics
- **Files**: `LRUCache.java`, `Main.java`
- **Description**: LRU (Least Recently Used) Cache implementation using HashMap and Doubly Linked List with generics
- **Run**: `cd 16_lru_cache && javac *.java && java Main`

## 🚀 Quick Start

### Compile and Run All Programs

```bash
# Navigate to any program directory
cd 01_fibonacci

# Compile
javac *.java

# Run
java Main
```

### Run All Programs (Bash Script)

```bash
#!/bin/bash
for dir in */; do
    if [ -f "$dir/Main.java" ]; then
        echo "Running $dir..."
        cd "$dir"
        javac *.java 2>/dev/null && java Main
        cd ..
        echo "---"
    fi
done
```

## 📚 Topics Covered

- **Data Structures**: Linked Lists, Arrays, HashMaps
- **Algorithms**: Fibonacci, Prime Numbers, Factorial, LRU Cache
- **Multithreading**: Producer-Consumer, Deadlock Prevention, Thread Sequencing
- **Design Patterns**: Singleton Pattern, Immutable Objects
- **Java Concepts**: Generics, String Manipulation, Number Operations

## 🔧 Requirements

- Java JDK 8 or higher
- No external dependencies required

## 📝 Notes

- All programs include comprehensive comments and documentation
- Each implementation includes multiple approaches where applicable
- Programs demonstrate best practices and common interview solutions
- All implementations are production-ready and well-tested

## 📖 Reference

Based on "Grokking the Java Interview" by Javin Paul.

---

**Happy Coding! 🎉**








