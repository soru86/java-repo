/**
 * Prime Number Checker Program
 * 
 * A prime number is a natural number greater than 1 that has no positive 
 * divisors other than 1 and itself.
 */
public class PrimeNumberChecker {
    
    /**
     * Check if a number is prime using naive approach
     * Time Complexity: O(n)
     * 
     * @param n the number to check
     * @return true if the number is prime, false otherwise
     */
    public static boolean isPrimeNaive(int n) {
        if (n <= 1) {
            return false;
        }
        
        // Check from 2 to n-1
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if a number is prime using optimized approach
     * Only check up to square root of n
     * Time Complexity: O(√n)
     * 
     * @param n the number to check
     * @return true if the number is prime, false otherwise
     */
    public static boolean isPrimeOptimized(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        
        // Check only odd divisors up to square root
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Print all prime numbers up to n using Sieve of Eratosthenes
     * Time Complexity: O(n log log n)
     * 
     * @param n the upper limit
     */
    public static void printAllPrimes(int n) {
        if (n < 2) {
            System.out.println("No prime numbers less than 2");
            return;
        }
        
        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }
        
        // Sieve of Eratosthenes
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        System.out.print("Prime numbers up to " + n + ": ");
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}


