/**
 * Reverse Array In Place
 * 
 * This program demonstrates how to reverse an array in place
 * without using additional space.
 */
public class ArrayReverser {
    
    /**
     * Reverse array in place using two pointers
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * @param arr the array to reverse
     */
    public static void reverseArray(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            // Swap elements
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            
            left++;
            right--;
        }
    }
    
    /**
     * Reverse array in place using XOR (without temp variable)
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * @param arr the array to reverse
     */
    public static void reverseArrayXOR(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            // Swap using XOR
            arr[left] = arr[left] ^ arr[right];
            arr[right] = arr[left] ^ arr[right];
            arr[left] = arr[left] ^ arr[right];
            
            left++;
            right--;
        }
    }
    
    /**
     * Reverse array in place recursively
     * Time Complexity: O(n)
     * Space Complexity: O(n) due to recursion stack
     * 
     * @param arr the array to reverse
     * @param left starting index
     * @param right ending index
     */
    public static void reverseArrayRecursive(int[] arr, int left, int right) {
        if (arr == null || left >= right) {
            return;
        }
        
        // Swap elements
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        
        // Recursive call
        reverseArrayRecursive(arr, left + 1, right - 1);
    }
    
    /**
     * Reverse a portion of the array
     * 
     * @param arr the array
     * @param start starting index
     * @param end ending index
     */
    public static void reverseSubArray(int[] arr, int start, int end) {
        if (arr == null || start < 0 || end >= arr.length || start >= end) {
            return;
        }
        
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            
            start++;
            end--;
        }
    }
}


