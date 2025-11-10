/**
 * Main class to demonstrate Max/Min Array program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Find Maximum and Minimum in Array ===");
        
        int[] arr1 = {3, 5, 1, 8, 2, 9, 4, 7, 6};
        System.out.println("\nArray: " + java.util.Arrays.toString(arr1));
        
        int[] result1 = ArrayMaxMin.findMaxMin(arr1);
        System.out.println("Method 1 - Linear Search: Min = " + result1[0] + ", Max = " + result1[1]);
        
        int[] result2 = ArrayMaxMin.findMaxMinDivideConquer(arr1, 0, arr1.length - 1);
        System.out.println("Method 2 - Divide & Conquer: Min = " + result2[0] + ", Max = " + result2[1]);
        
        int[] result3 = ArrayMaxMin.findMaxMinStream(arr1);
        System.out.println("Method 3 - Stream API: Min = " + result3[0] + ", Max = " + result3[1]);
        
        System.out.println("\nIndividual Methods:");
        System.out.println("Min only: " + ArrayMaxMin.findMin(arr1));
        System.out.println("Max only: " + ArrayMaxMin.findMax(arr1));
        
        // Test with different arrays
        System.out.println("\n=== Testing with different arrays ===");
        
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("\nSorted ascending: " + java.util.Arrays.toString(arr2));
        int[] result4 = ArrayMaxMin.findMaxMin(arr2);
        System.out.println("Min = " + result4[0] + ", Max = " + result4[1]);
        
        int[] arr3 = {5, 4, 3, 2, 1};
        System.out.println("\nSorted descending: " + java.util.Arrays.toString(arr3));
        int[] result5 = ArrayMaxMin.findMaxMin(arr3);
        System.out.println("Min = " + result5[0] + ", Max = " + result5[1]);
        
        int[] arr4 = {42};
        System.out.println("\nSingle element: " + java.util.Arrays.toString(arr4));
        int[] result6 = ArrayMaxMin.findMaxMin(arr4);
        System.out.println("Min = " + result6[0] + ", Max = " + result6[1]);
        
        int[] arr5 = {-5, -2, -8, -1, -9};
        System.out.println("\nNegative numbers: " + java.util.Arrays.toString(arr5));
        int[] result7 = ArrayMaxMin.findMaxMin(arr5);
        System.out.println("Min = " + result7[0] + ", Max = " + result7[1]);
    }
}





