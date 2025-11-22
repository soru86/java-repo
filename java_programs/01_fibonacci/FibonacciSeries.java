/**
 * Fibonacci Series Program
 * 
 * Fibonacci series is a series of numbers where each number is equal to 
 * sum of previous two numbers: f(n) = f(n-1) + f(n-2)
 * 
 * Example: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34...
 */
public class FibonacciSeries {
    
    /**
     * Iterative approach to print Fibonacci series
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static void printFibonacciIterative(int n) {
        if (n <= 0) {
            System.out.println("Please enter a positive number");
            return;
        }
        
        int first = 0, second = 1;
        
        if (n >= 1) {
            System.out.print(first);
        }
        if (n >= 2) {
            System.out.print(" " + second);
        }
        
        for (int i = 3; i <= n; i++) {
            int next = first + second;
            System.out.print(" " + next);
            first = second;
            second = next;
        }
        System.out.println();
    }
    
    /**
     * Recursive approach to print Fibonacci series
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) due to recursion stack
     */
    public static int fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
    
    /**
     * Print Fibonacci series using recursive method
     */
    public static void printFibonacciRecursive(int n) {
        if (n <= 0) {
            System.out.println("Please enter a positive number");
            return;
        }
        
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacciRecursive(i));
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    
    /**
     * Optimized recursive approach using memoization
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static void printFibonacciMemoized(int n) {
        if (n <= 0) {
            System.out.println("Please enter a positive number");
            return;
        }
        
        int[] memo = new int[n + 1];
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacciMemoized(i, memo));
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    
    private static int fibonacciMemoized(int n, int[] memo) {
        if (n <= 1) {
            return n;
        }
        if (memo[n] != 0) {
            return memo[n];
        }
        memo[n] = fibonacciMemoized(n - 1, memo) + fibonacciMemoized(n - 2, memo);
        return memo[n];
    }
}








