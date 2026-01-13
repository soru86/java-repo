package com.streams;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Advanced Operations with Java Streams API
 */
public class AdvancedOperations {

    // Employee class for examples
    public static class Employee {
        private String name;
        private String department;
        private double salary;

        public Employee(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }

        @Override
        public String toString() {
            return name + " (" + department + ") - $" + salary;
        }
    }

    // 89. Group Employees by Department and Calculate Average Salary
    public static Map<String, Double> groupByDepartmentAndAverageSalary(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)));
    }

    // 90. Find the Top N Highest-Paid Employees
    public static List<Employee> topNHighestPaid(List<Employee> employees, int n) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // 91. Implement Pagination Using Streams
    public static <T> List<T> paginate(List<T> list, int pageNumber, int pageSize) {
        int skip = pageNumber * pageSize;
        return list.stream()
                .skip(skip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    // 92. Chunk a List into Sublists Using Streams
    public static <T> List<List<T>> chunkList(List<T> list, int chunkSize) {
        return IntStream.range(0, (list.size() + chunkSize - 1) / chunkSize)
                .mapToObj(i -> list.subList(
                        i * chunkSize,
                        Math.min((i + 1) * chunkSize, list.size())))
                .collect(Collectors.toList());
    }

    // 93. Parallelize a CPU-Intensive Operation Using Streams
    public static List<Integer> parallelSquare(List<Integer> list) {
        return list.parallelStream()
                .map(n -> {
                    // Simulate CPU-intensive operation
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return n * n;
                })
                .collect(Collectors.toList());
    }

    // 94. Handle Exceptions in Streams
    public static List<Integer> safeParseIntegers(List<String> strings) {
        return strings.stream()
                .map(str -> {
                    try {
                        return Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 95. Use Custom Collectors to Calculate Statistics
    public static class Statistics {
        private long count;
        private double sum;
        private double min;
        private double max;
        private double average;

        public Statistics(long count, double sum, double min, double max, double average) {
            this.count = count;
            this.sum = sum;
            this.min = min;
            this.max = max;
            this.average = average;
        }

        @Override
        public String toString() {
            return String.format("Count: %d, Sum: %.2f, Min: %.2f, Max: %.2f, Avg: %.2f",
                    count, sum, min, max, average);
        }
    }

    public static Statistics calculateStatistics(List<Double> numbers) {
        return numbers.stream()
                .collect(Collector.of(
                        () -> new double[]{0, 0, Double.MAX_VALUE, Double.MIN_VALUE, 0}, // [count, sum, min, max, sumForAvg]
                        (acc, value) -> {
                            acc[0]++; // count
                            acc[1] += value; // sum
                            acc[2] = Math.min(acc[2], value); // min
                            acc[3] = Math.max(acc[3], value); // max
                        },
                        (acc1, acc2) -> {
                            acc1[0] += acc2[0];
                            acc1[1] += acc2[1];
                            acc1[2] = Math.min(acc1[2], acc2[2]);
                            acc1[3] = Math.max(acc1[3], acc2[3]);
                            return acc1;
                        },
                        acc -> new Statistics(
                                (long) acc[0],
                                acc[1],
                                acc[2] == Double.MAX_VALUE ? 0 : acc[2],
                                acc[3] == Double.MIN_VALUE ? 0 : acc[3],
                                acc[0] > 0 ? acc[1] / acc[0] : 0
                        )
                ));
    }

    // 96. Build a Custom Collector from Scratch
    public static <T> Collector<T, ?, List<T>> customToListCollector() {
        return Collector.of(
                ArrayList::new,           // Supplier
                List::add,                // Accumulator
                (list1, list2) -> {       // Combiner
                    list1.addAll(list2);
                    return list1;
                },
                Collector.Characteristics.IDENTITY_FINISH
        );
    }

    // 97. Combine Multiple Asynchronous Tasks
    public static CompletableFuture<List<String>> combineAsyncTasks(List<String> data) {
        CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() ->
                data.stream().map(String::toUpperCase).collect(Collectors.toList()));

        CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() ->
                data.stream().map(s -> s + "!").collect(Collectors.toList()));

        return future1.thenCombine(future2, (list1, list2) -> {
            return IntStream.range(0, Math.min(list1.size(), list2.size()))
                    .mapToObj(i -> list1.get(i) + " " + list2.get(i))
                    .collect(Collectors.toList());
        });
    }

    // 98. Process Large Datasets in Parallel
    public static Map<String, Long> processLargeDataset(List<String> dataset) {
        return dataset.parallelStream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(
                        s -> s.substring(0, Math.min(1, s.length())),
                        Collectors.counting()));
    }

    // 99. Filter and Transform Data Fetched from a Database (simulated)
    public static List<Employee> filterAndTransformEmployees(List<Employee> employees, 
                                                               String department, 
                                                               double minSalary) {
        return employees.stream()
                .filter(e -> e.getDepartment().equals(department))
                .filter(e -> e.getSalary() >= minSalary)
                .map(e -> new Employee(
                        e.getName().toUpperCase(),
                        e.getDepartment(),
                        e.getSalary() * 1.1)) // 10% raise
                .collect(Collectors.toList());
    }

    // 100. Parse and Validate JSON Payloads (simulated with simple validation)
    public static class JsonPayload {
        private String name;
        private int age;
        private String email;

        public JsonPayload(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public boolean isValid() {
            return name != null && !name.isEmpty() &&
                   age > 0 && age < 150 &&
                   email != null && email.contains("@");
        }

        @Override
        public String toString() {
            return String.format("{name: %s, age: %d, email: %s}", name, age, email);
        }
    }

    public static List<JsonPayload> parseAndValidateJson(List<JsonPayload> payloads) {
        return payloads.stream()
                .filter(JsonPayload::isValid)
                .collect(Collectors.toList());
    }

    // Test methods
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 5000),
                new Employee("Bob", "HR", 4000),
                new Employee("Charlie", "IT", 6000),
                new Employee("Diana", "HR", 4500),
                new Employee("Eve", "IT", 7000)
        );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);

        System.out.println("Group by dept and avg salary: " + groupByDepartmentAndAverageSalary(employees));
        System.out.println("Top 3 highest paid: " + topNHighestPaid(employees, 3));
        System.out.println("Paginate (page 1, size 3): " + paginate(numbers, 1, 3));
        System.out.println("Chunk (size 3): " + chunkList(numbers, 3));
        System.out.println("Statistics: " + calculateStatistics(doubles));
        System.out.println("Custom collector: " + numbers.stream().collect(customToListCollector()));
        System.out.println("Safe parse: " + safeParseIntegers(Arrays.asList("1", "2", "abc", "3")));
    }
}
