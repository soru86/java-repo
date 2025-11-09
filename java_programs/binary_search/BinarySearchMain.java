package binary_search;

public class BinarySearchMain {
    public static void main(String[] args) {
        BinarySearchSolution solution = new BinarySearchSolution();
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        System.out.println("Index: " + solution.search(nums, target));
    }
}

