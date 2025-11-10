package merge_intervals;

import java.util.Arrays;

public class MergeIntervalsMain {
    public static void main(String[] args) {
        MergeIntervalsSolution solution = new MergeIntervalsSolution();
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merged = solution.merge(intervals);
        System.out.println("Merged intervals: " + Arrays.deepToString(merged));
    }
}




