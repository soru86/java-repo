/**
 * Ensure Thread Sequence T1, T2, T3 in Java
 * 
 * This program demonstrates different ways to ensure threads execute
 * in a specific sequence (T1 -> T2 -> T3).
 */
public class ThreadSequencer {
    
    /**
     * Approach 1: Using join() method
     */
    public static void sequenceUsingJoin() throws InterruptedException {
        System.out.println("\n=== Thread Sequencing using join() ===");
        
        Thread t1 = new Thread(() -> {
            System.out.println("Thread T1 executing");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T1");
        
        Thread t2 = new Thread(() -> {
            System.out.println("Thread T2 executing");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T2");
        
        Thread t3 = new Thread(() -> {
            System.out.println("Thread T3 executing");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T3");
        
        t1.start();
        t1.join(); // Wait for T1 to complete
        
        t2.start();
        t2.join(); // Wait for T2 to complete
        
        t3.start();
        t3.join(); // Wait for T3 to complete
    }
    
    /**
     * Approach 2: Using wait() and notify()
     */
    private static final Object lock = new Object();
    private static int currentThread = 1;
    
    public static void sequenceUsingWaitNotify() throws InterruptedException {
        System.out.println("\n=== Thread Sequencing using wait() and notify() ===");
        currentThread = 1;
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (currentThread != 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread T1 executing");
                currentThread = 2;
                lock.notifyAll();
            }
        }, "T1");
        
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                while (currentThread != 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread T2 executing");
                currentThread = 3;
                lock.notifyAll();
            }
        }, "T2");
        
        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                while (currentThread != 3) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread T3 executing");
            }
        }, "T3");
        
        t3.start();
        t2.start();
        t1.start(); // Start in any order, but execution will be T1 -> T2 -> T3
        
        t1.join();
        t2.join();
        t3.join();
    }
    
    /**
     * Approach 3: Using CountDownLatch
     */
    public static void sequenceUsingCountDownLatch() throws InterruptedException {
        System.out.println("\n=== Thread Sequencing using CountDownLatch ===");
        
        java.util.concurrent.CountDownLatch latch1 = new java.util.concurrent.CountDownLatch(1);
        java.util.concurrent.CountDownLatch latch2 = new java.util.concurrent.CountDownLatch(1);
        
        Thread t1 = new Thread(() -> {
            System.out.println("Thread T1 executing");
            latch1.countDown(); // Signal T2 to start
        }, "T1");
        
        Thread t2 = new Thread(() -> {
            try {
                latch1.await(); // Wait for T1 to complete
                System.out.println("Thread T2 executing");
                latch2.countDown(); // Signal T3 to start
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T2");
        
        Thread t3 = new Thread(() -> {
            try {
                latch2.await(); // Wait for T2 to complete
                System.out.println("Thread T3 executing");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T3");
        
        t3.start();
        t2.start();
        t1.start();
        
        t1.join();
        t2.join();
        t3.join();
    }
    
    /**
     * Approach 4: Using Semaphore
     */
    public static void sequenceUsingSemaphore() throws InterruptedException {
        System.out.println("\n=== Thread Sequencing using Semaphore ===");
        
        java.util.concurrent.Semaphore sem1 = new java.util.concurrent.Semaphore(1);
        java.util.concurrent.Semaphore sem2 = new java.util.concurrent.Semaphore(0);
        java.util.concurrent.Semaphore sem3 = new java.util.concurrent.Semaphore(0);
        
        Thread t1 = new Thread(() -> {
            try {
                sem1.acquire();
                System.out.println("Thread T1 executing");
                sem2.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T1");
        
        Thread t2 = new Thread(() -> {
            try {
                sem2.acquire();
                System.out.println("Thread T2 executing");
                sem3.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T2");
        
        Thread t3 = new Thread(() -> {
            try {
                sem3.acquire();
                System.out.println("Thread T3 executing");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "T3");
        
        t3.start();
        t2.start();
        t1.start();
        
        t1.join();
        t2.join();
        t3.join();
    }
}






