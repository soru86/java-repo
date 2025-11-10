/**
 * Main class to demonstrate Number Reversal program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Reverse a Number in Java ===");
        
        int[] testNumbers = {12345, 98765, 12321, -12345, 100, 0, 1, 999};
        
        for (int num : testNumbers) {
            System.out.println("\nOriginal Number: " + num);
            System.out.println("Method 1 (Arithmetic):     " + NumberReverser.reverseNumber(num));
            System.out.println("Method 2 (String):          " + NumberReverser.reverseNumberUsingString(num));
            System.out.println("Method 3 (Recursive):      " + NumberReverser.reverseNumberRecursive(num));
            System.out.println("Is Palindrome:             " + NumberReverser.isPalindrome(num));
        }
        
        // Test with long numbers
        System.out.println("\n=== Testing with Long Numbers ===");
        long[] longNumbers = {123456789L, 987654321L};
        for (long num : longNumbers) {
            System.out.println("\nOriginal Number: " + num);
            System.out.println("Reversed: " + NumberReverser.reverseNumber(num));
        }
        
        // Special cases
        System.out.println("\n=== Special Cases ===");
        System.out.println("Reverse of 0: " + NumberReverser.reverseNumber(0));
        System.out.println("Reverse of -123: " + NumberReverser.reverseNumber(-123));
        System.out.println("Reverse of 1000: " + NumberReverser.reverseNumber(1000));
    }
}





