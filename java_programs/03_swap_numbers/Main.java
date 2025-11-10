/**
 * Main class to demonstrate Swap Numbers program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Swap Two Numbers Without Temp Variable ===");
        
        int a = 10, b = 20;
        System.out.println("\nOriginal values: a = " + a + ", b = " + b);
        
        // Method 1: Using XOR (Recommended)
        System.out.println("\n1. Using XOR (Recommended):");
        int[] result1 = SwapNumbers.swapUsingXOR(a, b);
        System.out.println("   After swap: a = " + result1[0] + ", b = " + result1[1]);
        
        // Method 2: Using Arithmetic
        System.out.println("\n2. Using Arithmetic (Addition/Subtraction):");
        int[] result2 = SwapNumbers.swapUsingArithmetic(a, b);
        System.out.println("   After swap: a = " + result2[0] + ", b = " + result2[1]);
        
        // Method 3: Using Multiplication
        System.out.println("\n3. Using Multiplication/Division:");
        int[] result3 = SwapNumbers.swapUsingMultiplication(a, b);
        if (result3 != null) {
            System.out.println("   After swap: a = " + result3[0] + ", b = " + result3[1]);
        }
        
        // Method 4: Using Wrapper Class
        System.out.println("\n4. Using Wrapper Class:");
        SwapNumbers.NumberWrapper wrapperA = new SwapNumbers.NumberWrapper(a);
        SwapNumbers.NumberWrapper wrapperB = new SwapNumbers.NumberWrapper(b);
        System.out.println("   Before swap: a = " + wrapperA.value + ", b = " + wrapperB.value);
        SwapNumbers.swapUsingWrapper(wrapperA, wrapperB);
        System.out.println("   After swap: a = " + wrapperA.value + ", b = " + wrapperB.value);
        
        // Test with different values
        System.out.println("\n=== Testing with different values ===");
        int x = 5, y = 7;
        System.out.println("Original: x = " + x + ", y = " + y);
        int[] swapped = SwapNumbers.swapUsingXOR(x, y);
        System.out.println("Swapped: x = " + swapped[0] + ", y = " + swapped[1]);
    }
}






