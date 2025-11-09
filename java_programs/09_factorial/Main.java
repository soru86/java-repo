/**
 * Main class to demonstrate Factorial Calculator program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Calculate Factorial in Java ===");
        
        int[] testNumbers = {0, 1, 2, 3, 4, 5, 10, 15, 20};
        
        System.out.println("\nFactorial Calculation Results:");
        System.out.println("n\tIterative\tRecursive\tTail Recursive\tMemoized");
        System.out.println("--------------------------------------------------------------");
        
        for (int n : testNumbers) {
            long iter = FactorialCalculator.factorialIterative(n);
            long recur = FactorialCalculator.factorialRecursive(n);
            long tail = FactorialCalculator.factorialTailRecursive(n);
            long memo = FactorialCalculator.factorialMemoized(n);
            
            System.out.printf("%d\t%d\t\t%d\t\t%d\t\t%d%n", n, iter, recur, tail, memo);
        }
        
        // Test with larger numbers using BigInteger
        System.out.println("\n=== Large Number Factorial (using BigInteger) ===");
        int[] largeNumbers = {25, 30, 50};
        for (int n : largeNumbers) {
            java.math.BigInteger result = FactorialCalculator.factorialBigInteger(n);
            System.out.println(n + "! = " + result.toString());
            System.out.println("   (length: " + result.toString().length() + " digits)");
        }
        
        // Verify all methods produce same result
        System.out.println("\n=== Verification ===");
        int testNum = 10;
        long result1 = FactorialCalculator.factorialIterative(testNum);
        long result2 = FactorialCalculator.factorialRecursive(testNum);
        long result3 = FactorialCalculator.factorialTailRecursive(testNum);
        long result4 = FactorialCalculator.factorialMemoized(testNum);
        
        System.out.println("All methods produce same result for n=" + testNum + ": " + 
            (result1 == result2 && result2 == result3 && result3 == result4));
        
        // Edge case
        System.out.println("\n=== Edge Cases ===");
        System.out.println("0! = " + FactorialCalculator.factorialIterative(0));
        System.out.println("1! = " + FactorialCalculator.factorialIterative(1));
    }
}


