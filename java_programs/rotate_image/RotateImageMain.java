package rotate_image;

import java.util.Arrays;

public class RotateImageMain {
    public static void main(String[] args) {
        RotateImageSolution solution = new RotateImageSolution();
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        solution.rotate(matrix);
        System.out.println("Rotated matrix:");
        Arrays.stream(matrix).map(Arrays::toString).forEach(System.out::println);
    }
}





