package next_permutation;

import java.util.Arrays;

public class NextPermutationMain {
    public static void main(String[] args) {
        NextPermutationSolution solution = new NextPermutationSolution();
        int[] nums = {1, 2, 3};
        solution.nextPermutation(nums);
        System.out.println("Next permutation: " + Arrays.toString(nums));
    }
}




