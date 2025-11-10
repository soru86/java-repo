package roman_to_integer;

import java.util.Map;

public class RomanToIntegerSolution {
    private static final Map<Character, Integer> VALUES = Map.of(
        'I', 1,
        'V', 5,
        'X', 10,
        'L', 50,
        'C', 100,
        'D', 500,
        'M', 1000
    );

    public int romanToInt(String s) {
        int total = 0;
        int previousValue = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int currentValue = VALUES.get(s.charAt(i));
            if (currentValue < previousValue) {
                total -= currentValue;
            } else {
                total += currentValue;
                previousValue = currentValue;
            }
        }
        return total;
    }
}




