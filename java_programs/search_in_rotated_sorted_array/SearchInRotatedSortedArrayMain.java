package search_in_rotated_sorted_array;

public class SearchInRotatedSortedArrayMain {
    public static void main(String[] args) {
        SearchInRotatedSortedArraySolution solution = new SearchInRotatedSortedArraySolution();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        System.out.println("Index: " + solution.search(nums, target));
    }
}




