/**
 * Main class to demonstrate Fibonacci Series program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Fibonacci Series Program ===");
        
        int n = 10;
        System.out.println("\nFibonacci series of first " + n + " numbers:");
        
        System.out.println("\n1. Using Iterative Approach:");
        FibonacciSeries.printFibonacciIterative(n);
        
        System.out.println("\n2. Using Recursive Approach:");
        FibonacciSeries.printFibonacciRecursive(n);
        
        System.out.println("\n3. Using Memoized Recursive Approach:");
        FibonacciSeries.printFibonacciMemoized(n);
        
        // Test with different values
        System.out.println("\n=== Testing with different values ===");
        System.out.println("\nFibonacci series of first 15 numbers (iterative):");
        FibonacciSeries.printFibonacciIterative(15);
    }
}








