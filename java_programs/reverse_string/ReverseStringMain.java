package reverse_string;

import java.util.Arrays;

public class ReverseStringMain {
    public static void main(String[] args) {
        ReverseStringSolution solution = new ReverseStringSolution();
        char[] input = {'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(input);
        System.out.println("Reversed: " + Arrays.toString(input));
    }
}




