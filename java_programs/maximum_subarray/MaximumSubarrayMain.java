package maximum_subarray;

public class MaximumSubarrayMain {
    public static void main(String[] args) {
        MaximumSubarraySolution solution = new MaximumSubarraySolution();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int maxSum = solution.maxSubArray(nums);
        System.out.println("Maximum subarray sum: " + maxSum);
    }
}




