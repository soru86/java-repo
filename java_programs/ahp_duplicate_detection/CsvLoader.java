package ahp_duplicate_detection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CsvLoader {

    private CsvLoader() {
    }

    public static CsvDataset load(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("CSV file is empty: " + path);
            }
            List<String> headers = parseCsvLine(headerLine);
            if (headers.isEmpty()) {
                throw new IOException("CSV header is empty: " + path);
            }

            List<CustomerRecord> records = new ArrayList<>();
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                List<String> values = parseCsvLine(line);
                if (values.size() != headers.size()) {
                    throw new IOException("Line " + lineNumber + " has " + values.size()
                        + " columns, expected " + headers.size());
                }
                Map<String, String> map = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    map.put(headers.get(i), values.get(i));
                }
                records.add(new CustomerRecord(map));
            }
            return new CsvDataset(headers, records);
        }
    }

    public static void write(Path path, CsvDataset dataset) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(String.join(",", dataset.headers()));
            writer.newLine();
            for (CustomerRecord record : dataset.records()) {
                List<String> values = new ArrayList<>();
                for (String header : dataset.headers()) {
                    values.add(escape(record.get(header)));
                }
                writer.write(String.join(",", values));
                writer.newLine();
            }
        }
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim();
        boolean needsQuotes = trimmed.contains(",") || trimmed.contains("\"") || trimmed.contains("\n");
        String escaped = trimmed.replace("\"", "\"\"");
        return needsQuotes ? "\"" + escaped + "\"" : escaped;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    current.append('\"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString());
        return tokens;
    }

    public record CsvDataset(List<String> headers, List<CustomerRecord> records) {
        public CsvDataset {
            headers = List.copyOf(headers);
            records = List.copyOf(records);
        }

        public int size() {
            return records.size();
        }
    }
}

