package ahp_duplicate_detection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AhpDuplicateDetectionMain {

    private static final double DEFAULT_THRESHOLD = 0.78;

    public static void main(String[] args) {
        Path inputPath = args.length > 0
            ? Paths.get(args[0])
            : Paths.get("ahp_duplicate_detection", "sample_car_customers.csv");
        Path outputPath = args.length > 1
            ? Paths.get(args[1])
            : Paths.get("ahp_duplicate_detection", "unique_car_customers.csv");
        double threshold = args.length > 2 ? parseThreshold(args[2]) : DEFAULT_THRESHOLD;

        try {
            CsvLoader.CsvDataset dataset = CsvLoader.load(inputPath);
            System.out.printf("Loaded %,d records from %s%n", dataset.size(), inputPath.toAbsolutePath());

            List<String> criteria = List.of(
                "Email",
                "Phone",
                "FirstName",
                "LastName",
                "PostalCode",
                "City",
                "CarMake",
                "PurchaseDate"
            );

            double[][] pairwiseMatrix = buildPairwiseMatrix(criteria.size());
            AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(criteria, pairwiseMatrix);
            AnalyticHierarchyProcess.AhpResult ahpResult = ahp.compute();

            System.out.println("Derived weights via Analytic Hierarchy Process (AHP):");
            for (Map.Entry<String, Double> entry : ahpResult.getWeights().entrySet()) {
                System.out.printf("  %-12s : %.4f%n", entry.getKey(), entry.getValue());
            }
            System.out.printf("Consistency ratio: %.4f (λ_max = %.4f)%n",
                ahpResult.getConsistencyRatio(), ahpResult.getLambdaMax());

            DuplicateDetector detector = new DuplicateDetector(ahpResult.getWeights());
            DuplicateDetectionResult detectionResult = detector.findUniqueRecords(dataset.records(), threshold);

            CsvLoader.write(outputPath, new CsvLoader.CsvDataset(dataset.headers(), detectionResult.getUniqueRecords()));

            int duplicateGroups = detectionResult.getDuplicateGroups().size();
            int duplicateRecords = detectionResult.getDuplicateRecordCount();

            System.out.println();
            System.out.println("Duplicate detection summary:");
            System.out.printf("  Threshold: %.2f%n", detectionResult.getThreshold());
            System.out.printf("  Pairwise comparisons evaluated: %,d%n", detectionResult.getComparisonsEvaluated());
            System.out.printf("  Average similarity score: %.4f%n", detectionResult.getAverageSimilarityScore());
            System.out.printf("  Duplicate groups identified: %,d (%,d total records)%n",
                duplicateGroups, duplicateRecords);
            System.out.printf("  Unique records retained: %,d%n", detectionResult.getUniqueRecords().size());
            System.out.printf("  Unique dataset written to: %s%n", outputPath.toAbsolutePath());

            if (duplicateGroups > 0) {
                System.out.println();
                System.out.println("Top duplicate groups (by strongest similarity):");
                detectionResult.getDuplicateGroups().stream()
                    .sorted(Comparator.comparingDouble(DuplicateDetectionResult.DuplicateGroup::strongestSimilarity)
                        .reversed())
                    .limit(5)
                    .forEach(group -> {
                        System.out.printf("  Similarity %.3f -> %s%n",
                            group.strongestSimilarity(),
                            group.representative());
                        group.records().forEach(record ->
                            System.out.printf("    - %s %s, Email=%s, Phone=%s%n",
                                record.get("FirstName"),
                                record.get("LastName"),
                                record.get("Email"),
                                record.get("Phone"))
                        );
                    });
            }

        } catch (IOException ex) {
            System.err.println("Failed to process CSV: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static double[][] buildPairwiseMatrix(int size) {
        if (size != 8) {
            throw new IllegalArgumentException("Expected 8 criteria, found " + size);
        }
        return new double[][]{
            {1.0, 3.0, 5.0, 5.0, 7.0, 7.0, 7.0, 9.0},
            {1.0 / 3.0, 1.0, 3.0, 3.0, 5.0, 5.0, 5.0, 7.0},
            {1.0 / 5.0, 1.0 / 3.0, 1.0, 1.0, 3.0, 3.0, 3.0, 5.0},
            {1.0 / 5.0, 1.0 / 3.0, 1.0, 1.0, 3.0, 3.0, 3.0, 5.0},
            {1.0 / 7.0, 1.0 / 5.0, 1.0 / 3.0, 1.0 / 3.0, 1.0, 1.0, 1.0, 3.0},
            {1.0 / 7.0, 1.0 / 5.0, 1.0 / 3.0, 1.0 / 3.0, 1.0, 1.0, 1.0, 3.0},
            {1.0 / 7.0, 1.0 / 5.0, 1.0 / 3.0, 1.0 / 3.0, 1.0, 1.0, 1.0, 3.0},
            {1.0 / 9.0, 1.0 / 7.0, 1.0 / 5.0, 1.0 / 5.0, 1.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0, 1.0}
        };
    }

    private static double parseThreshold(String value) {
        try {
            double parsed = Double.parseDouble(value);
            if (parsed <= 0 || parsed >= 1) {
                throw new IllegalArgumentException("Threshold must be between 0 and 1 (exclusive).");
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid threshold: " + value, ex);
        }
    }
}






