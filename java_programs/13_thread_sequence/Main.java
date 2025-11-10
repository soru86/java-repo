/**
 * Main class to demonstrate Thread Sequencing
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Ensure Thread Sequence T1, T2, T3 ===");
        
        try {
            // Method 1: Using join()
            ThreadSequencer.sequenceUsingJoin();
            
            Thread.sleep(500);
            
            // Method 2: Using wait() and notify()
            ThreadSequencer.sequenceUsingWaitNotify();
            
            Thread.sleep(500);
            
            // Method 3: Using CountDownLatch
            ThreadSequencer.sequenceUsingCountDownLatch();
            
            Thread.sleep(500);
            
            // Method 4: Using Semaphore
            ThreadSequencer.sequenceUsingSemaphore();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}





