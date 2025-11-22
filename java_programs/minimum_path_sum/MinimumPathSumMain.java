package minimum_path_sum;

public class MinimumPathSumMain {
    public static void main(String[] args) {
        MinimumPathSumSolution solution = new MinimumPathSumSolution();
        int[][] grid = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        System.out.println("Minimum path sum: " + solution.minPathSum(grid));
    }
}







