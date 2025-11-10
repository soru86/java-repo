/**
 * Find Maximum and Minimum Number in Array
 * 
 * This program demonstrates different approaches to find the maximum
 * and minimum values in an array.
 */
public class ArrayMaxMin {
    
    /**
     * Find maximum and minimum using linear search (single pass)
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * @param arr the array to search
     * @return an array containing [min, max]
     */
    public static int[] findMaxMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int min = arr[0];
        int max = arr[0];
        
        // Compare in pairs to reduce comparisons
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            } else if (arr[i] > max) {
                max = arr[i];
            }
        }
        
        return new int[]{min, max};
    }
    
    /**
     * Find maximum and minimum using divide and conquer approach
     * Time Complexity: O(n)
     * Space Complexity: O(log n) due to recursion
     * 
     * @param arr the array to search
     * @param low starting index
     * @param high ending index
     * @return an array containing [min, max]
     */
    public static int[] findMaxMinDivideConquer(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        // Base case: single element
        if (low == high) {
            return new int[]{arr[low], arr[low]};
        }
        
        // Base case: two elements
        if (high == low + 1) {
            if (arr[low] < arr[high]) {
                return new int[]{arr[low], arr[high]};
            } else {
                return new int[]{arr[high], arr[low]};
            }
        }
        
        // Divide
        int mid = (low + high) / 2;
        int[] left = findMaxMinDivideConquer(arr, low, mid);
        int[] right = findMaxMinDivideConquer(arr, mid + 1, high);
        
        // Conquer
        int min = Math.min(left[0], right[0]);
        int max = Math.max(left[1], right[1]);
        
        return new int[]{min, max};
    }
    
    /**
     * Find maximum and minimum using Stream API (Java 8+)
     * 
     * @param arr the array to search
     * @return an array containing [min, max]
     */
    public static int[] findMaxMinStream(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int min = java.util.Arrays.stream(arr).min().orElseThrow();
        int max = java.util.Arrays.stream(arr).max().orElseThrow();
        
        return new int[]{min, max};
    }
    
    /**
     * Find only maximum value
     * 
     * @param arr the array to search
     * @return the maximum value
     */
    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    /**
     * Find only minimum value
     * 
     * @param arr the array to search
     * @return the minimum value
     */
    public static int findMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
}





