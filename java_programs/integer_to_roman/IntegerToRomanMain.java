package integer_to_roman;

public class IntegerToRomanMain {
    public static void main(String[] args) {
        IntegerToRomanSolution solution = new IntegerToRomanSolution();
        int number = 1994;
        String roman = solution.intToRoman(number);
        System.out.println(number + " -> " + roman);
    }
}







