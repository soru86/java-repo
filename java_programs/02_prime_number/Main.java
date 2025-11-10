/**
 * Main class to demonstrate Prime Number Checker program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Prime Number Checker Program ===");
        
        int[] testNumbers = {2, 3, 4, 5, 17, 20, 29, 97, 100};
        
        System.out.println("\nChecking prime numbers using optimized approach:");
        for (int num : testNumbers) {
            boolean isPrime = PrimeNumberChecker.isPrimeOptimized(num);
            System.out.println(num + " is " + (isPrime ? "PRIME" : "NOT PRIME"));
        }
        
        System.out.println("\n=== All prime numbers up to 50 ===");
        PrimeNumberChecker.printAllPrimes(50);
        
        // Test with edge cases
        System.out.println("\n=== Testing edge cases ===");
        System.out.println("1 is " + (PrimeNumberChecker.isPrimeOptimized(1) ? "PRIME" : "NOT PRIME"));
        System.out.println("0 is " + (PrimeNumberChecker.isPrimeOptimized(0) ? "PRIME" : "NOT PRIME"));
        System.out.println("-5 is " + (PrimeNumberChecker.isPrimeOptimized(-5) ? "PRIME" : "NOT PRIME"));
    }
}





