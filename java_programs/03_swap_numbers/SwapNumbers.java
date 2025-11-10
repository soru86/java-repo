/**
 * Swap Two Numbers Without Using Temp Variable
 * 
 * This program demonstrates different ways to swap two numbers
 * without using a temporary variable.
 */
public class SwapNumbers {
    
    /**
     * Swap using arithmetic addition and subtraction
     * Note: Can cause overflow for large numbers
     * 
     * @param a first number
     * @param b second number
     * @return array with swapped values [b, a]
     */
    public static int[] swapUsingArithmetic(int a, int b) {
        a = a + b;
        b = a - b;
        a = a - b;
        return new int[]{a, b};
    }
    
    /**
     * Swap using XOR bitwise operation (Recommended)
     * This is the best approach as it doesn't cause overflow
     * 
     * @param a first number
     * @param b second number
     * @return array with swapped values [b, a]
     */
    public static int[] swapUsingXOR(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        return new int[]{a, b};
    }
    
    /**
     * Swap using multiplication and division
     * Note: Can cause overflow and division by zero issues
     * 
     * @param a first number
     * @param b second number
     * @return array with swapped values [b, a], null if b is 0
     */
    public static int[] swapUsingMultiplication(int a, int b) {
        if (b == 0) {
            return null; // Cannot divide by zero
        }
        a = a * b;
        b = a / b;
        a = a / b;
        return new int[]{a, b};
    }
    
    /**
     * Swap using a wrapper class to demonstrate pass-by-reference concept
     * Note: Java is pass-by-value, but we can use wrapper objects
     */
    public static class NumberWrapper {
        int value;
        
        public NumberWrapper(int value) {
            this.value = value;
        }
    }
    
    public static void swapUsingWrapper(NumberWrapper a, NumberWrapper b) {
        a.value = a.value ^ b.value;
        b.value = a.value ^ b.value;
        a.value = a.value ^ b.value;
    }
}





