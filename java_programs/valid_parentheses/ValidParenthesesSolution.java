package valid_parentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class ValidParenthesesSolution {
    private static final Map<Character, Character> PAIRS = Map.of(
        ')', '(',
        ']', '[',
        '}', '{'
    );

    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : s.toCharArray()) {
            if (PAIRS.containsValue(ch)) {
                stack.push(ch);
            } else if (PAIRS.containsKey(ch)) {
                if (stack.isEmpty() || stack.pop() != PAIRS.get(ch)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }
}





