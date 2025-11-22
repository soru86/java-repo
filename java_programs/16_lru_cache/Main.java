/**
 * Main class to demonstrate LRU Cache implementation
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== LRU Cache Implementation using Generics ===");
        
        // Test with Integer keys and String values
        System.out.println("\n1. Testing LRU Cache with Integer keys and String values:");
        LRUCache<Integer, String> cache1 = new LRUCache<>(3);
        
        cache1.put(1, "One");
        cache1.put(2, "Two");
        cache1.put(3, "Three");
        System.out.println("After adding 1, 2, 3: " + cache1);
        System.out.println("Keys in order: " + cache1.getKeysInOrder());
        
        cache1.get(1); // Access 1, should move it to front
        System.out.println("After accessing 1: " + cache1);
        System.out.println("Keys in order: " + cache1.getKeysInOrder());
        
        cache1.put(4, "Four"); // Should evict 2 (least recently used)
        System.out.println("After adding 4 (should evict 2): " + cache1);
        System.out.println("Keys in order: " + cache1.getKeysInOrder());
        System.out.println("Contains key 2? " + cache1.containsKey(2));
        System.out.println("Contains key 3? " + cache1.containsKey(3));
        
        // Test with String keys and Integer values
        System.out.println("\n2. Testing LRU Cache with String keys and Integer values:");
        LRUCache<String, Integer> cache2 = new LRUCache<>(4);
        
        cache2.put("apple", 5);
        cache2.put("banana", 10);
        cache2.put("cherry", 15);
        cache2.put("date", 20);
        System.out.println("After adding 4 items: " + cache2);
        
        cache2.get("banana"); // Access banana
        System.out.println("After accessing 'banana': " + cache2);
        System.out.println("Keys in order: " + cache2.getKeysInOrder());
        
        cache2.put("elderberry", 25); // Should evict apple
        System.out.println("After adding 'elderberry' (should evict 'apple'): " + cache2);
        System.out.println("Value of 'banana': " + cache2.get("banana"));
        
        // Test operations
        System.out.println("\n3. Testing cache operations:");
        System.out.println("Cache size: " + cache2.size());
        System.out.println("Is empty? " + cache2.isEmpty());
        System.out.println("Value removed: " + cache2.remove("cherry"));
        System.out.println("After removing 'cherry': " + cache2);
        System.out.println("Cache size: " + cache2.size());
        
        cache2.clear();
        System.out.println("After clearing: " + cache2);
        System.out.println("Is empty? " + cache2.isEmpty());
        
        // Test edge cases
        System.out.println("\n4. Testing edge cases:");
        LRUCache<Integer, String> cache3 = new LRUCache<>(1);
        cache3.put(1, "First");
        System.out.println("Cache with capacity 1: " + cache3);
        cache3.put(2, "Second"); // Should evict 1
        System.out.println("After adding second item: " + cache3);
        System.out.println("Contains 1? " + cache3.containsKey(1));
        System.out.println("Contains 2? " + cache3.containsKey(2));
        
        // Test get with non-existent key
        System.out.println("\n5. Testing non-existent key:");
        System.out.println("Get non-existent key 99: " + cache3.get(99));
    }
}








