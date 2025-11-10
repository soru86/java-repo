package search_insert_position;

public class SearchInsertPositionMain {
    public static void main(String[] args) {
        SearchInsertPositionSolution solution = new SearchInsertPositionSolution();
        int[] nums = {1, 3, 5, 6};
        int target = 5;
        System.out.println("Insert position: " + solution.searchInsert(nums, target));
    }
}




