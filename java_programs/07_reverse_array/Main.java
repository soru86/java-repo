/**
 * Main class to demonstrate Array Reversal program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Reverse Array In Place ===");
        
        // Test Case 1: Basic array reversal
        System.out.println("\n1. Basic Array Reversal:");
        int[] arr1 = {1, 2, 3, 4, 5};
        System.out.println("Original: " + java.util.Arrays.toString(arr1));
        ArrayReverser.reverseArray(arr1);
        System.out.println("Reversed: " + java.util.Arrays.toString(arr1));
        
        // Test Case 2: Using XOR method
        System.out.println("\n2. Array Reversal using XOR:");
        int[] arr2 = {10, 20, 30, 40, 50, 60};
        System.out.println("Original: " + java.util.Arrays.toString(arr2));
        ArrayReverser.reverseArrayXOR(arr2);
        System.out.println("Reversed: " + java.util.Arrays.toString(arr2));
        
        // Test Case 3: Using recursive method
        System.out.println("\n3. Array Reversal using Recursion:");
        int[] arr3 = {100, 200, 300, 400};
        System.out.println("Original: " + java.util.Arrays.toString(arr3));
        ArrayReverser.reverseArrayRecursive(arr3, 0, arr3.length - 1);
        System.out.println("Reversed: " + java.util.Arrays.toString(arr3));
        
        // Test Case 4: Reverse subarray
        System.out.println("\n4. Reverse Subarray (from index 1 to 4):");
        int[] arr4 = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Original: " + java.util.Arrays.toString(arr4));
        ArrayReverser.reverseSubArray(arr4, 1, 4);
        System.out.println("After reversing subarray [1-4]: " + java.util.Arrays.toString(arr4));
        
        // Test Case 5: Single element
        System.out.println("\n5. Single Element Array:");
        int[] arr5 = {42};
        System.out.println("Original: " + java.util.Arrays.toString(arr5));
        ArrayReverser.reverseArray(arr5);
        System.out.println("Reversed: " + java.util.Arrays.toString(arr5));
        
        // Test Case 6: Empty array
        System.out.println("\n6. Empty Array:");
        int[] arr6 = {};
        System.out.println("Original: " + java.util.Arrays.toString(arr6));
        ArrayReverser.reverseArray(arr6);
        System.out.println("Reversed: " + java.util.Arrays.toString(arr6));
    }
}


