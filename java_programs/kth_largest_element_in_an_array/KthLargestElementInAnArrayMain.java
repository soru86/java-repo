package kth_largest_element_in_an_array;

public class KthLargestElementInAnArrayMain {
    public static void main(String[] args) {
        KthLargestElementInAnArraySolution solution = new KthLargestElementInAnArraySolution();
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        System.out.println(k + "th largest: " + solution.findKthLargest(nums, k));
    }
}





