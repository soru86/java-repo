/**
 * Thread-Safe Singleton Pattern in Java
 * 
 * This program demonstrates different ways to implement thread-safe Singleton pattern:
 * 1. Eager initialization
 * 2. Lazy initialization with synchronized
 * 3. Double-checked locking
 * 4. Bill Pugh Singleton (Inner static class)
 * 5. Enum Singleton (Recommended)
 */
public class SingletonPattern {
    
    /**
     * Approach 1: Eager Initialization
     * Thread-safe because instance is created at class loading time
     * Pros: Simple, thread-safe
     * Cons: Instance created even if not used
     */
    public static class EagerSingleton {
        private static final EagerSingleton instance = new EagerSingleton();
        
        private EagerSingleton() {
            // Private constructor to prevent instantiation
        }
        
        public static EagerSingleton getInstance() {
            return instance;
        }
        
        public void doSomething() {
            System.out.println("EagerSingleton doing something");
        }
    }
    
    /**
     * Approach 2: Lazy Initialization with Synchronized
     * Thread-safe but performance overhead due to synchronization
     * Pros: Lazy initialization
     * Cons: Performance overhead on every access
     */
    public static class LazySingletonSynchronized {
        private static LazySingletonSynchronized instance;
        
        private LazySingletonSynchronized() {
            // Private constructor
        }
        
        public static synchronized LazySingletonSynchronized getInstance() {
            if (instance == null) {
                instance = new LazySingletonSynchronized();
            }
            return instance;
        }
        
        public void doSomething() {
            System.out.println("LazySingletonSynchronized doing something");
        }
    }
    
    /**
     * Approach 3: Double-Checked Locking
     * Thread-safe with better performance
     * Pros: Lazy initialization, better performance
     * Cons: Complex, requires volatile keyword
     */
    public static class DoubleCheckedLockingSingleton {
        private static volatile DoubleCheckedLockingSingleton instance;
        
        private DoubleCheckedLockingSingleton() {
            // Private constructor
        }
        
        public static DoubleCheckedLockingSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedLockingSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedLockingSingleton();
                    }
                }
            }
            return instance;
        }
        
        public void doSomething() {
            System.out.println("DoubleCheckedLockingSingleton doing something");
        }
    }
    
    /**
     * Approach 4: Bill Pugh Singleton (Inner Static Class)
     * Recommended approach - lazy initialization with thread safety
     * Pros: Lazy initialization, thread-safe, no synchronization overhead
     * Cons: None significant
     */
    public static class BillPughSingleton {
        private BillPughSingleton() {
            // Private constructor
        }
        
        private static class SingletonHelper {
            private static final BillPughSingleton INSTANCE = new BillPughSingleton();
        }
        
        public static BillPughSingleton getInstance() {
            return SingletonHelper.INSTANCE;
        }
        
        public void doSomething() {
            System.out.println("BillPughSingleton doing something");
        }
    }
    
    /**
     * Approach 5: Enum Singleton (Best Practice)
     * Recommended by Joshua Bloch - inherently thread-safe and serialization-safe
     * Pros: Thread-safe, serialization-safe, reflection-safe, simplest
     * Cons: Not flexible (cannot extend classes)
     */
    public enum EnumSingleton {
        INSTANCE;
        
        public void doSomething() {
            System.out.println("EnumSingleton doing something");
        }
    }
    
    /**
     * Test thread safety by creating multiple threads
     */
    public static void testThreadSafety(Runnable getInstanceMethod, String methodName) throws InterruptedException {
        System.out.println("\n=== Testing Thread Safety: " + methodName + " ===");
        
        java.util.Set<Object> instances = java.util.Collections.synchronizedSet(new java.util.HashSet<>());
        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                Object instance = getInstanceMethod.run();
                instances.add(instance);
            });
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("Number of unique instances created: " + instances.size());
        if (instances.size() == 1) {
            System.out.println("✓ Thread-safe: Only one instance created");
        } else {
            System.out.println("✗ Not thread-safe: Multiple instances created");
        }
    }
}





