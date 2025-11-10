package ahp_duplicate_detection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

public class DuplicateDetector {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Map<String, Double> weights;
    private final Map<String, ToDoubleBiFunction<CustomerRecord, CustomerRecord>> similarityFunctions;

    public DuplicateDetector(Map<String, Double> weights) {
        this.weights = Map.copyOf(Objects.requireNonNull(weights, "weights"));
        this.similarityFunctions = buildSimilarityFunctions();
    }

    public DuplicateDetectionResult findUniqueRecords(List<CustomerRecord> records, double threshold) {
        List<CustomerRecord> copy = List.copyOf(records);
        int size = copy.size();
        if (size == 0) {
            return new DuplicateDetectionResult(List.of(), List.of(), threshold, 0, 0.0);
        }

        DisjointSet disjointSet = new DisjointSet(size);
        double similaritySum = 0.0;
        int comparisons = 0;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                double similarity = computeSimilarity(copy.get(i), copy.get(j));
                similaritySum += similarity;
                comparisons++;
                if (similarity >= threshold) {
                    disjointSet.union(i, j);
                }
            }
        }

        Map<Integer, List<Integer>> groupedIndices = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            int root = disjointSet.find(i);
            groupedIndices.computeIfAbsent(root, key -> new ArrayList<>()).add(i);
        }

        List<CustomerRecord> uniqueRecords = new ArrayList<>();
        List<DuplicateDetectionResult.DuplicateGroup> duplicateGroups = new ArrayList<>();

        for (List<Integer> indices : groupedIndices.values()) {
            List<CustomerRecord> groupRecords = new ArrayList<>();
            for (int index : indices) {
                groupRecords.add(copy.get(index));
            }
            if (!groupRecords.isEmpty()) {
                uniqueRecords.add(groupRecords.get(0));
                if (groupRecords.size() > 1) {
                    double strongestSimilarity = computeStrongestSimilarity(groupRecords);
                    duplicateGroups.add(new DuplicateDetectionResult.DuplicateGroup(groupRecords, strongestSimilarity));
                }
            }
        }

        return new DuplicateDetectionResult(uniqueRecords, duplicateGroups, threshold, comparisons, similaritySum);
    }

    private double computeSimilarity(CustomerRecord a, CustomerRecord b) {
        double score = 0.0;
        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            ToDoubleBiFunction<CustomerRecord, CustomerRecord> function = similarityFunctions.get(entry.getKey());
            if (function == null) {
                continue;
            }
            double weight = entry.getValue();
            if (weight <= 0) {
                continue;
            }
            double criterionScore = function.applyAsDouble(a, b);
            score += weight * criterionScore;
        }
        return score;
    }

    private Map<String, ToDoubleBiFunction<CustomerRecord, CustomerRecord>> buildSimilarityFunctions() {
        Map<String, ToDoubleBiFunction<CustomerRecord, CustomerRecord>> functions = new HashMap<>();
        functions.put("Email", DuplicateDetector::emailSimilarity);
        functions.put("Phone", DuplicateDetector::phoneSimilarity);
        functions.put("FirstName", DuplicateDetector::firstNameSimilarity);
        functions.put("LastName", DuplicateDetector::lastNameSimilarity);
        functions.put("PostalCode", DuplicateDetector::postalCodeSimilarity);
        functions.put("City", DuplicateDetector::citySimilarity);
        functions.put("CarMake", DuplicateDetector::carMakeSimilarity);
        functions.put("PurchaseDate", DuplicateDetector::purchaseDateSimilarity);
        return functions;
    }

    private double computeStrongestSimilarity(List<CustomerRecord> records) {
        double strongest = 0.0;
        for (int i = 0; i < records.size(); i++) {
            for (int j = i + 1; j < records.size(); j++) {
                double similarity = computeSimilarity(records.get(i), records.get(j));
                if (similarity > strongest) {
                    strongest = similarity;
                }
            }
        }
        return strongest;
    }

    private static double emailSimilarity(CustomerRecord a, CustomerRecord b) {
        String emailA = normalizeEmail(a.get("Email"));
        String emailB = normalizeEmail(b.get("Email"));
        if (emailA.isEmpty() || emailB.isEmpty()) {
            return 0.0;
        }
        if (emailA.equals(emailB)) {
            return 1.0;
        }

        String[] partsA = emailA.split("@", 2);
        String[] partsB = emailB.split("@", 2);
        double score = 0.0;
        if (partsA.length == 2 && partsB.length == 2) {
            if (partsA[1].equals(partsB[1])) {
                score += 0.3;
            }
            String localA = partsA[0];
            String localB = partsB[0];
            if (localA.equals(localB)) {
                score += 0.7;
            } else {
                String simplifiedA = localA.replaceAll("[^a-z]", "");
                String simplifiedB = localB.replaceAll("[^a-z]", "");
                if (!simplifiedA.isEmpty() && simplifiedA.equals(simplifiedB)) {
                    score += 0.6;
                } else {
                    score += normalizedLevenshtein(localA, localB) * 0.6;
                }
            }
        }
        return Math.min(1.0, score);
    }

    private static double phoneSimilarity(CustomerRecord a, CustomerRecord b) {
        String digitsA = a.get("Phone").replaceAll("\\D", "");
        String digitsB = b.get("Phone").replaceAll("\\D", "");
        if (digitsA.isEmpty() || digitsB.isEmpty()) {
            return 0.0;
        }
        if (digitsA.equals(digitsB)) {
            return 1.0;
        }
        int minLength = Math.min(digitsA.length(), digitsB.length());
        int matchedSuffix = longestCommonSuffix(digitsA, digitsB);
        if (matchedSuffix >= 7 && minLength >= 7) {
            return 0.7;
        }
        if (matchedSuffix >= 4 && minLength >= 4) {
            return 0.4;
        }
        return 0.0;
    }

    private static double firstNameSimilarity(CustomerRecord a, CustomerRecord b) {
        return nameSimilarityForValues(a.get("FirstName"), b.get("FirstName"));
    }

    private static double lastNameSimilarity(CustomerRecord a, CustomerRecord b) {
        return nameSimilarityForValues(a.get("LastName"), b.get("LastName"));
    }

    private static double nameSimilarityForValues(String first, String second) {
        String valueA = normalizeAlpha(first);
        String valueB = normalizeAlpha(second);
        if (valueA.isEmpty() || valueB.isEmpty()) {
            return 0.0;
        }
        if (valueA.equals(valueB)) {
            return 1.0;
        }
        if (valueA.charAt(0) == valueB.charAt(0)) {
            return 0.6;
        }
        return normalizedLevenshtein(valueA, valueB);
    }

    private static double citySimilarity(CustomerRecord a, CustomerRecord b) {
        String cityA = normalizeAlpha(a.get("City"));
        String cityB = normalizeAlpha(b.get("City"));
        if (cityA.isEmpty() || cityB.isEmpty()) {
            return 0.0;
        }
        if (cityA.equals(cityB)) {
            return 1.0;
        }
        return normalizedLevenshtein(cityA, cityB);
    }

    private static double postalCodeSimilarity(CustomerRecord a, CustomerRecord b) {
        String postalA = a.get("PostalCode").trim();
        String postalB = b.get("PostalCode").trim();
        if (postalA.isEmpty() || postalB.isEmpty()) {
            return 0.0;
        }
        if (postalA.equals(postalB)) {
            return 1.0;
        }
        if (postalA.length() >= 3 && postalB.length() >= 3
            && postalA.substring(0, 3).equals(postalB.substring(0, 3))) {
            return 0.5;
        }
        return 0.0;
    }

    private static double carMakeSimilarity(CustomerRecord a, CustomerRecord b) {
        String makeA = a.get("CarMake").trim().toLowerCase(Locale.ROOT);
        String makeB = b.get("CarMake").trim().toLowerCase(Locale.ROOT);
        if (makeA.isEmpty() || makeB.isEmpty()) {
            return 0.0;
        }
        return makeA.equals(makeB) ? 1.0 : 0.0;
    }

    private static double purchaseDateSimilarity(CustomerRecord a, CustomerRecord b) {
        LocalDate dateA = parseDate(a.get("PurchaseDate"));
        LocalDate dateB = parseDate(b.get("PurchaseDate"));
        if (dateA == null || dateB == null) {
            return 0.0;
        }
        long days = Math.abs(dateA.toEpochDay() - dateB.toEpochDay());
        if (days == 0) {
            return 1.0;
        }
        if (days <= 3) {
            return 0.8;
        }
        if (days <= 7) {
            return 0.6;
        }
        if (days <= 30) {
            return 0.3;
        }
        return 0.0;
    }

    private static LocalDate parseDate(String value) {
        try {
            if (value == null || value.isBlank()) {
                return null;
            }
            return LocalDate.parse(value.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    private static String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeAlpha(String value) {
        if (value == null) {
            return "";
        }
        String normalized = value.replaceAll("[^A-Za-z]", "").toLowerCase(Locale.ROOT);
        return normalized.trim();
    }

    private static int longestCommonSuffix(String a, String b) {
        int count = 0;
        int indexA = a.length() - 1;
        int indexB = b.length() - 1;
        while (indexA >= 0 && indexB >= 0 && a.charAt(indexA) == b.charAt(indexB)) {
            count++;
            indexA--;
            indexB--;
        }
        return count;
    }

    private static double normalizedLevenshtein(String a, String b) {
        if (a == null || b == null) {
            return 0.0;
        }
        if (a.isEmpty() && b.isEmpty()) {
            return 1.0;
        }
        int distance = levenshteinDistance(a, b);
        int maxLength = Math.max(a.length(), b.length());
        if (maxLength == 0) {
            return 1.0;
        }
        double similarity = 1.0 - ((double) distance / maxLength);
        return Math.max(0.0, Math.min(1.0, similarity));
    }

    private static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = min(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[a.length()][b.length()];
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}

