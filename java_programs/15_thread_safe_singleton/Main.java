/**
 * Main class to demonstrate Thread-Safe Singleton Pattern
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Thread-Safe Singleton Pattern ===");
        
        // Test Eager Singleton
        System.out.println("\n1. Eager Singleton:");
        SingletonPattern.EagerSingleton eager1 = SingletonPattern.EagerSingleton.getInstance();
        SingletonPattern.EagerSingleton eager2 = SingletonPattern.EagerSingleton.getInstance();
        System.out.println("Same instance? " + (eager1 == eager2));
        eager1.doSomething();
        
        // Test Lazy Synchronized Singleton
        System.out.println("\n2. Lazy Synchronized Singleton:");
        SingletonPattern.LazySingletonSynchronized lazy1 = 
            SingletonPattern.LazySingletonSynchronized.getInstance();
        SingletonPattern.LazySingletonSynchronized lazy2 = 
            SingletonPattern.LazySingletonSynchronized.getInstance();
        System.out.println("Same instance? " + (lazy1 == lazy2));
        lazy1.doSomething();
        
        // Test Double-Checked Locking Singleton
        System.out.println("\n3. Double-Checked Locking Singleton:");
        SingletonPattern.DoubleCheckedLockingSingleton dcl1 = 
            SingletonPattern.DoubleCheckedLockingSingleton.getInstance();
        SingletonPattern.DoubleCheckedLockingSingleton dcl2 = 
            SingletonPattern.DoubleCheckedLockingSingleton.getInstance();
        System.out.println("Same instance? " + (dcl1 == dcl2));
        dcl1.doSomething();
        
        // Test Bill Pugh Singleton
        System.out.println("\n4. Bill Pugh Singleton (Recommended):");
        SingletonPattern.BillPughSingleton billPugh1 = 
            SingletonPattern.BillPughSingleton.getInstance();
        SingletonPattern.BillPughSingleton billPugh2 = 
            SingletonPattern.BillPughSingleton.getInstance();
        System.out.println("Same instance? " + (billPugh1 == billPugh2));
        billPugh1.doSomething();
        
        // Test Enum Singleton
        System.out.println("\n5. Enum Singleton (Best Practice):");
        SingletonPattern.EnumSingleton enum1 = SingletonPattern.EnumSingleton.INSTANCE;
        SingletonPattern.EnumSingleton enum2 = SingletonPattern.EnumSingleton.INSTANCE;
        System.out.println("Same instance? " + (enum1 == enum2));
        enum1.doSomething();
        
        // Test thread safety
        try {
            SingletonPattern.testThreadSafety(
                () -> SingletonPattern.EagerSingleton.getInstance(),
                "Eager Singleton"
            );
            
            SingletonPattern.testThreadSafety(
                () -> SingletonPattern.LazySingletonSynchronized.getInstance(),
                "Lazy Synchronized Singleton"
            );
            
            SingletonPattern.testThreadSafety(
                () -> SingletonPattern.DoubleCheckedLockingSingleton.getInstance(),
                "Double-Checked Locking Singleton"
            );
            
            SingletonPattern.testThreadSafety(
                () -> SingletonPattern.BillPughSingleton.getInstance(),
                "Bill Pugh Singleton"
            );
            
            SingletonPattern.testThreadSafety(
                () -> SingletonPattern.EnumSingleton.INSTANCE,
                "Enum Singleton"
            );
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}






