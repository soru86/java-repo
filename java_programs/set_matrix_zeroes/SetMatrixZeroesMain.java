package set_matrix_zeroes;

import java.util.Arrays;

public class SetMatrixZeroesMain {
    public static void main(String[] args) {
        SetMatrixZeroesSolution solution = new SetMatrixZeroesSolution();
        int[][] matrix = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
        solution.setZeroes(matrix);
        System.out.println("After setting zeroes:");
        Arrays.stream(matrix).map(Arrays::toString).forEach(System.out::println);
    }
}




