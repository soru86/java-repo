/**
 * Avoid Deadlock in Java
 * 
 * This program demonstrates:
 * 1. How deadlock occurs
 * 2. How to prevent deadlock by ordering locks
 * 3. How to use timeout to avoid deadlock
 */
public class DeadlockPrevention {
    
    // Two resources
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();
    
    /**
     * Example of code that CAN cause deadlock
     */
    static class DeadlockExample {
        public static void method1() {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " acquired resource1");
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource2");
                }
            }
        }
        
        public static void method2() {
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " acquired resource2");
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (resource1) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource1");
                }
            }
        }
    }
    
    /**
     * Solution 1: Always acquire locks in the same order
     */
    static class DeadlockPreventionByOrdering {
        public static void method1() {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " acquired resource1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource2");
                }
            }
        }
        
        public static void method2() {
            // Always acquire resource1 first, then resource2
            // This ensures consistent lock ordering
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " acquired resource1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource2");
                }
            }
        }
    }
    
    /**
     * Solution 2: Use ReentrantLock with timeout
     */
    static class DeadlockPreventionByTimeout {
        private static final java.util.concurrent.locks.ReentrantLock lock1 = 
            new java.util.concurrent.locks.ReentrantLock();
        private static final java.util.concurrent.locks.ReentrantLock lock2 = 
            new java.util.concurrent.locks.ReentrantLock();
        
        public static void method1() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;
            
            try {
                lock1Acquired = lock1.tryLock(5, java.util.concurrent.TimeUnit.SECONDS);
                if (lock1Acquired) {
                    System.out.println(Thread.currentThread().getName() + " acquired lock1");
                    Thread.sleep(100);
                    
                    lock2Acquired = lock2.tryLock(5, java.util.concurrent.TimeUnit.SECONDS);
                    if (lock2Acquired) {
                        System.out.println(Thread.currentThread().getName() + " acquired lock2");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " failed to acquire lock2");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed to acquire lock1");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (lock2Acquired) {
                    lock2.unlock();
                }
                if (lock1Acquired) {
                    lock1.unlock();
                }
            }
        }
        
        public static void method2() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;
            
            try {
                lock1Acquired = lock1.tryLock(5, java.util.concurrent.TimeUnit.SECONDS);
                if (lock1Acquired) {
                    System.out.println(Thread.currentThread().getName() + " acquired lock1");
                    Thread.sleep(100);
                    
                    lock2Acquired = lock2.tryLock(5, java.util.concurrent.TimeUnit.SECONDS);
                    if (lock2Acquired) {
                        System.out.println(Thread.currentThread().getName() + " acquired lock2");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " failed to acquire lock2");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed to acquire lock1");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (lock2Acquired) {
                    lock2.unlock();
                }
                if (lock1Acquired) {
                    lock1.unlock();
                }
            }
        }
    }
    
    /**
     * Test deadlock prevention by ordering
     */
    public static void testDeadlockPreventionByOrdering() throws InterruptedException {
        System.out.println("\n=== Testing Deadlock Prevention by Lock Ordering ===");
        
        Thread t1 = new Thread(() -> DeadlockPreventionByOrdering.method1(), "Thread-1");
        Thread t2 = new Thread(() -> DeadlockPreventionByOrdering.method2(), "Thread-2");
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println("Both threads completed successfully - no deadlock!");
    }
    
    /**
     * Test deadlock prevention by timeout
     */
    public static void testDeadlockPreventionByTimeout() throws InterruptedException {
        System.out.println("\n=== Testing Deadlock Prevention by Timeout ===");
        
        Thread t1 = new Thread(() -> DeadlockPreventionByTimeout.method1(), "Thread-1");
        Thread t2 = new Thread(() -> DeadlockPreventionByTimeout.method2(), "Thread-2");
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println("Both threads completed - timeout prevented deadlock!");
    }
}








