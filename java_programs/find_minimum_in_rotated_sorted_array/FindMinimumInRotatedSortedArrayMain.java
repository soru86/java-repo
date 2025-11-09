package find_minimum_in_rotated_sorted_array;

public class FindMinimumInRotatedSortedArrayMain {
    public static void main(String[] args) {
        FindMinimumInRotatedSortedArraySolution solution = new FindMinimumInRotatedSortedArraySolution();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Minimum: " + solution.findMin(nums));
    }
}

