package roman_to_integer;

public class RomanToIntegerMain {
    public static void main(String[] args) {
        RomanToIntegerSolution solution = new RomanToIntegerSolution();
        String roman = "MCMXCIV";
        int value = solution.romanToInt(roman);
        System.out.println(roman + " -> " + value);
    }
}







