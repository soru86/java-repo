package top_k_frequent_elements;

import java.util.Arrays;

public class TopKFrequentElementsMain {
    public static void main(String[] args) {
        TopKFrequentElementsSolution solution = new TopKFrequentElementsSolution();
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println("Top k frequent: " + Arrays.toString(solution.topKFrequent(nums, k)));
    }
}







