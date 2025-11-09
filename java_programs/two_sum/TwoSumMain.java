package two_sum;

import java.util.Arrays;

public class TwoSumMain {
    public static void main(String[] args) {
        TwoSumSolution solution = new TwoSumSolution();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] indices = solution.twoSum(nums, target);
        System.out.println("Indices: " + Arrays.toString(indices));
    }
}

