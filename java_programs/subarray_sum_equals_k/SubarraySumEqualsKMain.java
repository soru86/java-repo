package subarray_sum_equals_k;

public class SubarraySumEqualsKMain {
    public static void main(String[] args) {
        SubarraySumEqualsKSolution solution = new SubarraySumEqualsKSolution();
        int[] nums = {1, 1, 1};
        int k = 2;
        System.out.println("Number of subarrays: " + solution.subarraySum(nums, k));
    }
}




