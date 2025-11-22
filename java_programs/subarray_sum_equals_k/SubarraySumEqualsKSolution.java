package subarray_sum_equals_k;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsKSolution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCounts = new HashMap<>();
        prefixCounts.put(0, 1);
        int sum = 0;
        int total = 0;
        for (int num : nums) {
            sum += num;
            total += prefixCounts.getOrDefault(sum - k, 0);
            prefixCounts.put(sum, prefixCounts.getOrDefault(sum, 0) + 1);
        }
        return total;
    }
}







