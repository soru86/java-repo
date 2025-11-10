/**
 * Main class to demonstrate Deadlock Prevention
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Avoid Deadlock in Java ===");
        
        try {
            // Test deadlock prevention by ordering
            DeadlockPrevention.testDeadlockPreventionByOrdering();
            
            // Add delay between tests
            Thread.sleep(500);
            
            // Test deadlock prevention by timeout
            DeadlockPrevention.testDeadlockPreventionByTimeout();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}





