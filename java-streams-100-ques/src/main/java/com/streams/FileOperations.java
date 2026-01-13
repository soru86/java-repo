package com.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File Operations with Java Streams API
 */
public class FileOperations {

    // 75. Find the Number of Lines in a File
    public static long numberOfLines(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.count();
        }
    }

    // 76. Find the Number of Characters in a File
    public static long numberOfCharacters(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.mapToLong(String::length).sum();
        }
    }

    // 77. Find the Number of Words in a File
    public static long numberOfWords(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .count();
        }
    }

    // 78. Find the Number of Unique Words in a File
    public static long numberOfUniqueWords(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.flatMap(line -> Arrays.stream(line.toLowerCase().split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .distinct()
                    .count();
        }
    }

    // 79. Process a CSV File and Calculate Aggregate Statistics
    public static class CSVStats {
        private long totalRows;
        private long totalColumns;
        private Map<String, Long> columnCounts;

        public CSVStats(long totalRows, long totalColumns, Map<String, Long> columnCounts) {
            this.totalRows = totalRows;
            this.totalColumns = totalColumns;
            this.columnCounts = columnCounts;
        }

        @Override
        public String toString() {
            return String.format("Rows: %d, Columns: %d, Column Counts: %s", 
                    totalRows, totalColumns, columnCounts);
        }
    }

    public static CSVStats processCSV(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            List<String> lineList = lines.collect(Collectors.toList());
            if (lineList.isEmpty()) {
                return new CSVStats(0, 0, Collections.emptyMap());
            }

            long totalRows = lineList.size();
            String[] headers = lineList.get(0).split(",");
            long totalColumns = headers.length;

            Map<String, Long> columnCounts = lineList.stream()
                    .skip(1)
                    .flatMap(line -> {
                        String[] values = line.split(",");
                        return IntStream.range(0, Math.min(values.length, headers.length))
                                .mapToObj(i -> headers[i]);
                    })
                    .collect(Collectors.groupingBy(h -> h, Collectors.counting()));

            return new CSVStats(totalRows - 1, totalColumns, columnCounts);
        }
    }

    // 80. Find the Top N Most Frequent Words in a Text File
    public static List<Map.Entry<String, Long>> topNFrequentWords(String filePath, int n) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.flatMap(line -> Arrays.stream(line.toLowerCase().split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(n)
                    .collect(Collectors.toList());
        }
    }

    // Test methods
    public static void main(String[] args) {
        // Note: These methods require actual file paths to test
        System.out.println("File operations ready. Provide file paths to test.");
    }
}
