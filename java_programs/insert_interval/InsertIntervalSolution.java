package insert_interval;

import java.util.ArrayList;
import java.util.List;

public class InsertIntervalSolution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        int[] merged = newInterval.clone();
        while (i < intervals.length && intervals[i][0] <= merged[1]) {
            merged[0] = Math.min(merged[0], intervals[i][0]);
            merged[1] = Math.max(merged[1], intervals[i][1]);
            i++;
        }
        result.add(merged);
        while (i < intervals.length) {
            result.add(intervals[i]);
            i++;
        }
        return result.toArray(new int[result.size()][]);
    }
}

