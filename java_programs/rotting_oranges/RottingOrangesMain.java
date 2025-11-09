package rotting_oranges;

public class RottingOrangesMain {
    public static void main(String[] args) {
        RottingOrangesSolution solution = new RottingOrangesSolution();
        int[][] grid = {
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
        };
        System.out.println("Minutes to rot all: " + solution.orangesRotting(grid));
    }
}

