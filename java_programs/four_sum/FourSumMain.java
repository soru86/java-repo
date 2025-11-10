package four_sum;

public class FourSumMain {
    public static void main(String[] args) {
        FourSumSolution solution = new FourSumSolution();
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;
        System.out.println("Quadruplets: " + solution.fourSum(nums, target));
    }
}





