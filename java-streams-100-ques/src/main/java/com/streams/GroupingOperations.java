package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Grouping and Partitioning Operations with Java Streams API
 */
public class GroupingOperations {

    // 37. Group a List of Strings by Their Length
    public static Map<Integer, List<String>> groupByLength(List<String> list) {
        return list.stream()
                .collect(Collectors.groupingBy(String::length));
    }

    // 38. Group a List of Objects by a Specific Attribute
    public static class Person {
        private String name;
        private String department;
        private double salary;

        public Person(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }

        @Override
        public String toString() {
            return name + " (" + department + ")";
        }
    }

    public static Map<String, List<Person>> groupByDepartment(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment));
    }

    // 39. Partition a List of Integers into Even and Odd Numbers
    public static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> list) {
        return list.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }

    // Test methods
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Person> persons = Arrays.asList(
                new Person("Alice", "IT", 5000),
                new Person("Bob", "HR", 4000),
                new Person("Charlie", "IT", 6000),
                new Person("Diana", "HR", 4500)
        );

        System.out.println("Group by length: " + groupByLength(strings));
        System.out.println("Group by department: " + groupByDepartment(persons));
        System.out.println("Partition even/odd: " + partitionEvenOdd(numbers));
    }
}
