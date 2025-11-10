package unique_paths_ii;

public class UniquePathsIiMain {
    public static void main(String[] args) {
        UniquePathsIiSolution solution = new UniquePathsIiSolution();
        int[][] obstacleGrid = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        System.out.println("Unique paths with obstacles: " + solution.uniquePathsWithObstacles(obstacleGrid));
    }
}





