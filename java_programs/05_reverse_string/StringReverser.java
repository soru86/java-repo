/**
 * Reverse String Without Using API
 * 
 * This program demonstrates different ways to reverse a string
 * without using built-in Java API methods like StringBuilder.reverse()
 */
public class StringReverser {
    
    /**
     * Reverse string using character array
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverseUsingCharArray(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char[] chars = str.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            // Swap characters
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Reverse string using iterative approach with string concatenation
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverseUsingIteration(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }
        return reversed;
    }
    
    /**
     * Reverse string using recursive approach
     * Time Complexity: O(n)
     * Space Complexity: O(n) due to recursion stack
     * 
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverseUsingRecursion(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        return reverseUsingRecursion(str.substring(1)) + str.charAt(0);
    }
    
    /**
     * Reverse string using StringBuilder (manual implementation)
     * Building the reversed string character by character
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverseUsingStringBuilder(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
    
    /**
     * Reverse string using two pointers and character swapping
     * Similar to array reversal
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverseUsingTwoPointers(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char[] chars = str.toCharArray();
        int start = 0;
        int end = chars.length - 1;
        
        while (start < end) {
            chars[start] = (char)(chars[start] ^ chars[end]);
            chars[end] = (char)(chars[start] ^ chars[end]);
            chars[start] = (char)(chars[start] ^ chars[end]);
            
            start++;
            end--;
        }
        
        return new String(chars);
    }
}





