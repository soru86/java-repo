package minimum_size_subarray_sum;

public class MinimumSizeSubarraySumMain {
    public static void main(String[] args) {
        MinimumSizeSubarraySumSolution solution = new MinimumSizeSubarraySumSolution();
        int target = 7;
        int[] nums = {2, 3, 1, 2, 4, 3};
        System.out.println("Minimum length: " + solution.minSubArrayLen(target, nums));
    }
}







