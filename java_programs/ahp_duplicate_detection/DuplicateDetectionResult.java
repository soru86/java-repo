package ahp_duplicate_detection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DuplicateDetectionResult {

    private final List<CustomerRecord> uniqueRecords;
    private final List<DuplicateGroup> duplicateGroups;
    private final double threshold;
    private final int comparisonsEvaluated;
    private final double totalSimilarityScore;

    public DuplicateDetectionResult(
        List<CustomerRecord> uniqueRecords,
        List<DuplicateGroup> duplicateGroups,
        double threshold,
        int comparisonsEvaluated,
        double totalSimilarityScore
    ) {
        this.uniqueRecords = List.copyOf(uniqueRecords);
        this.duplicateGroups = List.copyOf(duplicateGroups);
        this.threshold = threshold;
        this.comparisonsEvaluated = comparisonsEvaluated;
        this.totalSimilarityScore = totalSimilarityScore;
    }

    public List<CustomerRecord> getUniqueRecords() {
        return uniqueRecords;
    }

    public List<DuplicateGroup> getDuplicateGroups() {
        return duplicateGroups;
    }

    public double getThreshold() {
        return threshold;
    }

    public int getComparisonsEvaluated() {
        return comparisonsEvaluated;
    }

    public double getAverageSimilarityScore() {
        return comparisonsEvaluated == 0 ? 0.0 : totalSimilarityScore / comparisonsEvaluated;
    }

    public int getDuplicateRecordCount() {
        int count = 0;
        for (DuplicateGroup group : duplicateGroups) {
            count += group.records().size();
        }
        return count;
    }

    public static final class DuplicateGroup {
        private final List<CustomerRecord> records;
        private final double strongestSimilarity;

        public DuplicateGroup(List<CustomerRecord> records, double strongestSimilarity) {
            this.records = Collections.unmodifiableList(new ArrayList<>(records));
            this.strongestSimilarity = strongestSimilarity;
        }

        public List<CustomerRecord> records() {
            return records;
        }

        public double strongestSimilarity() {
            return strongestSimilarity;
        }

        public CustomerRecord representative() {
            return records.isEmpty() ? null : records.get(0);
        }
    }
}




