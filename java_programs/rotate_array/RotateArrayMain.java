package rotate_array;

import java.util.Arrays;

public class RotateArrayMain {
    public static void main(String[] args) {
        RotateArraySolution solution = new RotateArraySolution();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        solution.rotate(nums, 3);
        System.out.println("Rotated array: " + Arrays.toString(nums));
    }
}





