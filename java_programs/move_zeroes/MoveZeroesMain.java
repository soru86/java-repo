package move_zeroes;

import java.util.Arrays;

public class MoveZeroesMain {
    public static void main(String[] args) {
        MoveZeroesSolution solution = new MoveZeroesSolution();
        int[] nums = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums);
        System.out.println("After moving zeroes: " + Arrays.toString(nums));
    }
}







