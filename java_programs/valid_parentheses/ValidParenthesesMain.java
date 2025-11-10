package valid_parentheses;

public class ValidParenthesesMain {
    public static void main(String[] args) {
        ValidParenthesesSolution solution = new ValidParenthesesSolution();
        String input = "{[()]}";
        System.out.println("Input: " + input);
        System.out.println("Is valid: " + solution.isValid(input));
    }
}




