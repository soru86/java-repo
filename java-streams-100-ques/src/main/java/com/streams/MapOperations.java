package com.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Map Operations with Java Streams API
 */
public class MapOperations {

    // 83. Convert a Map to a List of Keys Using Streams
    public static <K, V> List<K> mapKeysToList(Map<K, V> map) {
        return map.keySet().stream()
                .collect(Collectors.toList());
    }

    // 84. Convert a Map to a List of Values Using Streams
    public static <K, V> List<V> mapValuesToList(Map<K, V> map) {
        return map.values().stream()
                .collect(Collectors.toList());
    }

    // 85. Sort a Map by Keys Using Streams
    public static <K extends Comparable<? super K>, V> Map<K, V> sortMapByKeys(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    // 86. Sort a Map by Values Using Streams
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValues(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    // 87. Convert a List of Objects to a Map Using Streams
    public static class Employee {
        private int id;
        private String name;
        private String department;

        public Employee(int id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDepartment() { return department; }

        @Override
        public String toString() {
            return name + " (" + department + ")";
        }
    }

    public static Map<Integer, Employee> listToMap(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));
    }

    // 88. Convert a List of Objects to a Map with Duplicate Keys Handling
    public static Map<String, List<Employee>> listToMapWithDuplicates(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    // Test methods
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("three", 3);
        map.put("one", 1);
        map.put("two", 2);

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "IT"),
                new Employee(2, "Bob", "HR"),
                new Employee(3, "Charlie", "IT"),
                new Employee(4, "Diana", "HR")
        );

        System.out.println("Keys: " + mapKeysToList(map));
        System.out.println("Values: " + mapValuesToList(map));
        System.out.println("Sorted by keys: " + sortMapByKeys(map));
        System.out.println("Sorted by values: " + sortMapByValues(map));
        System.out.println("List to Map: " + listToMap(employees));
        System.out.println("List to Map (duplicates): " + listToMapWithDuplicates(employees));
    }
}
