package sliding_window_maximum;

import java.util.Arrays;

public class SlidingWindowMaximumMain {
    public static void main(String[] args) {
        SlidingWindowMaximumSolution solution = new SlidingWindowMaximumSolution();
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println("Sliding window maximums: " + Arrays.toString(solution.maxSlidingWindow(nums, k)));
    }
}





