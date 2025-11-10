package ahp_duplicate_detection;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Minimal Analytic Hierarchy Process (AHP) implementation that turns a
 * pairwise comparison matrix into a set of criterion weights and exposes
 * the overall consistency ratio for the matrix.
 */
public class AnalyticHierarchyProcess {

    private static final double[] RANDOM_INDEX = {
        0.00, // 1 element
        0.00, // 2 elements
        0.58, // 3
        0.90, // 4
        1.12, // 5
        1.24, // 6
        1.32, // 7
        1.41, // 8
        1.45, // 9
        1.49, // 10
        1.51  // 11+
    };

    private final List<String> criteria;
    private final double[][] pairwiseMatrix;

    public AnalyticHierarchyProcess(List<String> criteria, double[][] pairwiseMatrix) {
        this.criteria = List.copyOf(Objects.requireNonNull(criteria, "criteria"));
        this.pairwiseMatrix = validateMatrix(pairwiseMatrix, criteria.size());
    }

    public AhpResult compute() {
        int n = criteria.size();

        double[][] normalizedMatrix = new double[n][n];
        double[] columnSums = new double[n];

        for (int column = 0; column < n; column++) {
            double sum = 0.0;
            for (int row = 0; row < n; row++) {
                double value = pairwiseMatrix[row][column];
                if (value <= 0) {
                    throw new IllegalArgumentException("Pairwise comparison values must be positive.");
                }
                sum += value;
            }
            if (sum == 0.0) {
                throw new IllegalArgumentException("Column sum cannot be zero in the pairwise matrix.");
            }
            columnSums[column] = sum;
            for (int row = 0; row < n; row++) {
                normalizedMatrix[row][column] = pairwiseMatrix[row][column] / sum;
            }
        }

        double[] weightsVector = new double[n];
        double totalWeight = 0.0;
        for (int row = 0; row < n; row++) {
            double sum = 0.0;
            for (int column = 0; column < n; column++) {
                sum += normalizedMatrix[row][column];
            }
            double weight = sum / n;
            weightsVector[row] = weight;
            totalWeight += weight;
        }

        if (totalWeight == 0.0) {
            throw new IllegalStateException("Total weight cannot be zero.");
        }

        Map<String, Double> weights = new LinkedHashMap<>();
        for (int i = 0; i < n; i++) {
            weights.put(criteria.get(i), weightsVector[i] / totalWeight);
        }

        double[] weightedSum = new double[n];
        for (int row = 0; row < n; row++) {
            double sum = 0.0;
            for (int column = 0; column < n; column++) {
                sum += pairwiseMatrix[row][column] * weightsVector[column];
            }
            weightedSum[row] = sum;
        }

        double lambdaMax = 0.0;
        for (int i = 0; i < n; i++) {
            lambdaMax += weightedSum[i] / weightsVector[i];
        }
        lambdaMax /= n;

        double consistencyIndex = (lambdaMax - n) / (n - 1);
        double randomIndex = n <= RANDOM_INDEX.length ? RANDOM_INDEX[n - 1] : RANDOM_INDEX[RANDOM_INDEX.length - 1];
        double consistencyRatio = randomIndex == 0.0 ? 0.0 : consistencyIndex / randomIndex;

        return new AhpResult(weights, consistencyRatio, lambdaMax);
    }

    private static double[][] validateMatrix(double[][] matrix, int expectedSize) {
        Objects.requireNonNull(matrix, "matrix");
        if (matrix.length != expectedSize) {
            throw new IllegalArgumentException("Pairwise matrix size mismatch: expected " + expectedSize + " rows.");
        }
        double[][] copy = new double[expectedSize][expectedSize];
        for (int row = 0; row < expectedSize; row++) {
            if (matrix[row] == null || matrix[row].length != expectedSize) {
                throw new IllegalArgumentException("Pairwise matrix must be square with size " + expectedSize);
            }
            System.arraycopy(matrix[row], 0, copy[row], 0, expectedSize);
        }
        return copy;
    }

    public static final class AhpResult {
        private final Map<String, Double> weights;
        private final double consistencyRatio;
        private final double lambdaMax;

        private AhpResult(Map<String, Double> weights, double consistencyRatio, double lambdaMax) {
            this.weights = Map.copyOf(weights);
            this.consistencyRatio = consistencyRatio;
            this.lambdaMax = lambdaMax;
        }

        public Map<String, Double> getWeights() {
            return weights;
        }

        public double getConsistencyRatio() {
            return consistencyRatio;
        }

        public double getLambdaMax() {
            return lambdaMax;
        }
    }
}




