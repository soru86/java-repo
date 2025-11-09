package insert_interval;

import java.util.Arrays;

public class InsertIntervalMain {
    public static void main(String[] args) {
        InsertIntervalSolution solution = new InsertIntervalSolution();
        int[][] intervals = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};
        int[][] result = solution.insert(intervals, newInterval);
        System.out.println("After insertion: " + Arrays.deepToString(result));
    }
}

