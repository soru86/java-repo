package longest_increasing_subsequence;

public class LongestIncreasingSubsequenceMain {
    public static void main(String[] args) {
        LongestIncreasingSubsequenceSolution solution = new LongestIncreasingSubsequenceSolution();
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Length of LIS: " + solution.lengthOfLIS(nums));
    }
}

