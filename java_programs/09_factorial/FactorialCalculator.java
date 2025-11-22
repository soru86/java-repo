/**
 * Calculate Factorial in Java
 * 
 * Factorial of n (n!) is the product of all positive integers less than or equal to n.
 * n! = n × (n-1) × (n-2) × ... × 2 × 1
 * 0! = 1
 */
public class FactorialCalculator {
    
    /**
     * Calculate factorial using iterative approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * @param n the number
     * @return the factorial of n
     */
    public static long factorialIterative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        
        if (n == 0 || n == 1) {
            return 1;
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        
        return result;
    }
    
    /**
     * Calculate factorial using recursive approach
     * Time Complexity: O(n)
     * Space Complexity: O(n) due to recursion stack
     * 
     * @param n the number
     * @return the factorial of n
     */
    public static long factorialRecursive(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        
        // Base case
        if (n == 0 || n == 1) {
            return 1;
        }
        
        // Recursive case
        return n * factorialRecursive(n - 1);
    }
    
    /**
     * Calculate factorial using tail recursion (optimized)
     * 
     * @param n the number
     * @return the factorial of n
     */
    public static long factorialTailRecursive(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        
        return factorialTailRecursiveHelper(n, 1);
    }
    
    private static long factorialTailRecursiveHelper(int n, long acc) {
        if (n == 0 || n == 1) {
            return acc;
        }
        
        return factorialTailRecursiveHelper(n - 1, n * acc);
    }
    
    /**
     * Calculate factorial using BigInteger for very large numbers
     * 
     * @param n the number
     * @return the factorial of n as BigInteger
     */
    public static java.math.BigInteger factorialBigInteger(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        
        if (n == 0 || n == 1) {
            return java.math.BigInteger.ONE;
        }
        
        java.math.BigInteger result = java.math.BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(java.math.BigInteger.valueOf(i));
        }
        
        return result;
    }
    
    /**
     * Calculate factorial using memoization (cache results)
     */
    private static final int MAX_CACHE = 20;
    private static final long[] cache = new long[MAX_CACHE + 1];
    private static boolean cacheInitialized = false;
    
    static {
        cache[0] = 1;
        cache[1] = 1;
        for (int i = 2; i <= MAX_CACHE; i++) {
            cache[i] = cache[i - 1] * i;
        }
        cacheInitialized = true;
    }
    
    public static long factorialMemoized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        
        if (n <= MAX_CACHE && cacheInitialized) {
            return cache[n];
        }
        
        // Fallback to iterative for large numbers
        return factorialIterative(n);
    }
}








