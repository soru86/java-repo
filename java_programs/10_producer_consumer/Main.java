/**
 * Main class to demonstrate Producer-Consumer problem
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Producer-Consumer Problem ===");
        
        try {
            // Test wait/notify approach
            ProducerConsumer.testWaitNotify();
            
            // Add a small delay between tests
            Thread.sleep(1000);
            
            // Test BlockingQueue approach
            ProducerConsumer.testBlockingQueue();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}


