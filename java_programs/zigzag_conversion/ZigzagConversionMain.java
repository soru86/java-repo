package zigzag_conversion;

public class ZigzagConversionMain {
    public static void main(String[] args) {
        ZigzagConversionSolution solution = new ZigzagConversionSolution();
        String input = "PAYPALISHIRING";
        String converted = solution.convert(input, 3);
        System.out.println("Converted string: " + converted);
    }
}




