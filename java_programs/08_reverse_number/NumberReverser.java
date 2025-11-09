/**
 * Reverse a Number in Java
 * 
 * This program demonstrates different ways to reverse a number.
 */
public class NumberReverser {
    
    /**
     * Reverse a number using arithmetic operations
     * Time Complexity: O(log10(n))
     * Space Complexity: O(1)
     * 
     * @param num the number to reverse
     * @return the reversed number
     */
    public static int reverseNumber(int num) {
        int reversed = 0;
        int original = num;
        
        // Handle negative numbers
        boolean isNegative = num < 0;
        if (isNegative) {
            num = Math.abs(num);
        }
        
        while (num > 0) {
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num = num / 10;
        }
        
        return isNegative ? -reversed : reversed;
    }
    
    /**
     * Reverse a number using String conversion
     * 
     * @param num the number to reverse
     * @return the reversed number
     */
    public static int reverseNumberUsingString(int num) {
        boolean isNegative = num < 0;
        String numStr = String.valueOf(Math.abs(num));
        
        StringBuilder reversed = new StringBuilder();
        for (int i = numStr.length() - 1; i >= 0; i--) {
            reversed.append(numStr.charAt(i));
        }
        
        int result = Integer.parseInt(reversed.toString());
        return isNegative ? -result : result;
    }
    
    /**
     * Reverse a number using recursion
     * 
     * @param num the number to reverse
     * @return the reversed number
     */
    public static int reverseNumberRecursive(int num) {
        return reverseNumberRecursiveHelper(num, 0);
    }
    
    private static int reverseNumberRecursiveHelper(int num, int reversed) {
        if (num == 0) {
            return reversed;
        }
        
        int digit = num % 10;
        return reverseNumberRecursiveHelper(num / 10, reversed * 10 + digit);
    }
    
    /**
     * Reverse a number and check if it's a palindrome
     * 
     * @param num the number to check
     * @return true if the number is a palindrome, false otherwise
     */
    public static boolean isPalindrome(int num) {
        return num == reverseNumber(num);
    }
    
    /**
     * Reverse a long number (for larger numbers)
     * 
     * @param num the long number to reverse
     * @return the reversed number
     */
    public static long reverseNumber(long num) {
        long reversed = 0;
        boolean isNegative = num < 0;
        if (isNegative) {
            num = Math.abs(num);
        }
        
        while (num > 0) {
            long digit = num % 10;
            reversed = reversed * 10 + digit;
            num = num / 10;
        }
        
        return isNegative ? -reversed : reversed;
    }
}


