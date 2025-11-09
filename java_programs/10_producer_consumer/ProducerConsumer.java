import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Producer Consumer Problem Implementation
 * 
 * This program demonstrates the classic Producer-Consumer problem
 * using multiple approaches:
 * 1. wait() and notify() methods
 * 2. BlockingQueue
 * 3. Semaphore
 */
public class ProducerConsumer {
    
    // Shared buffer for wait/notify approach
    private static final int BUFFER_SIZE = 5;
    private static int[] buffer = new int[BUFFER_SIZE];
    private static int count = 0;
    private static int in = 0;
    private static int out = 0;
    private static final Object lock = new Object();
    
    // Using BlockingQueue
    private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BUFFER_SIZE);
    
    /**
     * Producer class using wait() and notify()
     */
    static class ProducerWaitNotify extends Thread {
        private int id;
        private int itemsToProduce;
        
        public ProducerWaitNotify(int id, int itemsToProduce) {
            this.id = id;
            this.itemsToProduce = itemsToProduce;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < itemsToProduce; i++) {
                try {
                    synchronized (lock) {
                        // Wait if buffer is full
                        while (count == BUFFER_SIZE) {
                            System.out.println("Producer " + id + " waiting - buffer full");
                            lock.wait();
                        }
                        
                        // Produce item
                        buffer[in] = i;
                        in = (in + 1) % BUFFER_SIZE;
                        count++;
                        System.out.println("Producer " + id + " produced: " + i);
                        
                        // Notify waiting consumers
                        lock.notifyAll();
                    }
                    Thread.sleep(100); // Simulate production time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    /**
     * Consumer class using wait() and notify()
     */
    static class ConsumerWaitNotify extends Thread {
        private int id;
        private int itemsToConsume;
        
        public ConsumerWaitNotify(int id, int itemsToConsume) {
            this.id = id;
            this.itemsToConsume = itemsToConsume;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < itemsToConsume; i++) {
                try {
                    synchronized (lock) {
                        // Wait if buffer is empty
                        while (count == 0) {
                            System.out.println("Consumer " + id + " waiting - buffer empty");
                            lock.wait();
                        }
                        
                        // Consume item
                        int item = buffer[out];
                        out = (out + 1) % BUFFER_SIZE;
                        count--;
                        System.out.println("Consumer " + id + " consumed: " + item);
                        
                        // Notify waiting producers
                        lock.notifyAll();
                    }
                    Thread.sleep(150); // Simulate consumption time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    /**
     * Producer class using BlockingQueue
     */
    static class ProducerBlockingQueue extends Thread {
        private int id;
        private int itemsToProduce;
        
        public ProducerBlockingQueue(int id, int itemsToProduce) {
            this.id = id;
            this.itemsToProduce = itemsToProduce;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < itemsToProduce; i++) {
                try {
                    queue.put(i);
                    System.out.println("Producer " + id + " produced: " + i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    /**
     * Consumer class using BlockingQueue
     */
    static class ConsumerBlockingQueue extends Thread {
        private int id;
        private int itemsToConsume;
        
        public ConsumerBlockingQueue(int id, int itemsToConsume) {
            this.id = id;
            this.itemsToConsume = itemsToConsume;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < itemsToConsume; i++) {
                try {
                    int item = queue.take();
                    System.out.println("Consumer " + id + " consumed: " + item);
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    /**
     * Test Producer-Consumer using wait() and notify()
     */
    public static void testWaitNotify() throws InterruptedException {
        System.out.println("\n=== Producer-Consumer using wait() and notify() ===");
        
        ProducerWaitNotify producer1 = new ProducerWaitNotify(1, 10);
        ProducerWaitNotify producer2 = new ProducerWaitNotify(2, 10);
        ConsumerWaitNotify consumer1 = new ConsumerWaitNotify(1, 10);
        ConsumerWaitNotify consumer2 = new ConsumerWaitNotify(2, 10);
        
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        
        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();
    }
    
    /**
     * Test Producer-Consumer using BlockingQueue
     */
    public static void testBlockingQueue() throws InterruptedException {
        System.out.println("\n=== Producer-Consumer using BlockingQueue ===");
        
        ProducerBlockingQueue producer1 = new ProducerBlockingQueue(1, 10);
        ProducerBlockingQueue producer2 = new ProducerBlockingQueue(2, 10);
        ConsumerBlockingQueue consumer1 = new ConsumerBlockingQueue(1, 10);
        ConsumerBlockingQueue consumer2 = new ConsumerBlockingQueue(2, 10);
        
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        
        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();
    }
}


