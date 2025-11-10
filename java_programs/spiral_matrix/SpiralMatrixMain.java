package spiral_matrix;

public class SpiralMatrixMain {
    public static void main(String[] args) {
        SpiralMatrixSolution solution = new SpiralMatrixSolution();
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Spiral order: " + solution.spiralOrder(matrix));
    }
}




