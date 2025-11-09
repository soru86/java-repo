package number_of_islands;

public class NumberOfIslandsMain {
    public static void main(String[] args) {
        NumberOfIslandsSolution solution = new NumberOfIslandsSolution();
        char[][] grid = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        System.out.println("Number of islands: " + solution.numIslands(grid));
    }
}

