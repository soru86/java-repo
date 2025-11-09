/**
 * Main class to demonstrate String Reversal program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Reverse String Without Using API ===");
        
        String[] testStrings = {
            "Hello",
            "World",
            "Java",
            "Programming",
            "Interview",
            "A",
            "",
            "12345",
            "Hello World"
        };
        
        for (String str : testStrings) {
            System.out.println("\nOriginal String: \"" + str + "\"");
            System.out.println("1. Using Char Array:        \"" + 
                StringReverser.reverseUsingCharArray(str) + "\"");
            System.out.println("2. Using Iteration:         \"" + 
                StringReverser.reverseUsingIteration(str) + "\"");
            System.out.println("3. Using Recursion:          \"" + 
                StringReverser.reverseUsingRecursion(str) + "\"");
            System.out.println("4. Using StringBuilder:      \"" + 
                StringReverser.reverseUsingStringBuilder(str) + "\"");
            System.out.println("5. Using Two Pointers (XOR): \"" + 
                StringReverser.reverseUsingTwoPointers(str) + "\"");
        }
        
        // Verify all methods produce same result
        System.out.println("\n=== Verification ===");
        String test = "TestString";
        String result1 = StringReverser.reverseUsingCharArray(test);
        String result2 = StringReverser.reverseUsingIteration(test);
        String result3 = StringReverser.reverseUsingRecursion(test);
        String result4 = StringReverser.reverseUsingStringBuilder(test);
        String result5 = StringReverser.reverseUsingTwoPointers(test);
        
        System.out.println("All methods produce same result: " + 
            (result1.equals(result2) && result2.equals(result3) && 
             result3.equals(result4) && result4.equals(result5)));
    }
}


