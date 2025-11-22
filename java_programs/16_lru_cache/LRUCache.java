import java.util.HashMap;
import java.util.Map;

/**
 * LRU Cache Implementation using Generics
 * 
 * LRU (Least Recently Used) Cache implementation using:
 * - HashMap for O(1) access
 * - Doubly Linked List for O(1) insertion/deletion
 * 
 * Time Complexity: O(1) for both get and put operations
 * Space Complexity: O(capacity)
 * 
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class LRUCache<K, V> {
    
    /**
     * Node class for doubly linked list
     */
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<K, Node> cache;
    private Node head; // Dummy head (most recently used)
    private Node tail; // Dummy tail (least recently used)
    
    /**
     * Constructor
     * 
     * @param capacity the maximum number of items the cache can hold
     */
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy head and tail
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Get value by key
     * 
     * @param key the key
     * @return the value associated with the key, or null if not found
     */
    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        
        // Move to head (mark as most recently used)
        moveToHead(node);
        return node.value;
    }
    
    /**
     * Put key-value pair into cache
     * 
     * @param key the key
     * @param value the value
     */
    public void put(K key, V value) {
        Node node = cache.get(key);
        
        if (node != null) {
            // Update existing node
            node.value = value;
            moveToHead(node);
        } else {
            // Add new node
            if (cache.size() >= capacity) {
                // Remove least recently used (tail)
                Node lru = removeTail();
                cache.remove(lru.key);
            }
            
            Node newNode = new Node(key, value);
            addToHead(newNode);
            cache.put(key, newNode);
        }
    }
    
    /**
     * Remove a key from cache
     * 
     * @param key the key to remove
     * @return the value that was removed, or null if not found
     */
    public V remove(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        
        removeNode(node);
        cache.remove(key);
        return node.value;
    }
    
    /**
     * Check if cache contains a key
     * 
     * @param key the key to check
     * @return true if cache contains the key, false otherwise
     */
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
    
    /**
     * Get the current size of the cache
     * 
     * @return the number of items in the cache
     */
    public int size() {
        return cache.size();
    }
    
    /**
     * Check if cache is empty
     * 
     * @return true if cache is empty, false otherwise
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    /**
     * Clear all entries from cache
     */
    public void clear() {
        cache.clear();
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Add node to head (most recently used)
     */
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Remove node from doubly linked list
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * Move node to head (mark as most recently used)
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    
    /**
     * Remove tail node (least recently used)
     */
    private Node removeTail() {
        Node lru = tail.prev;
        removeNode(lru);
        return lru;
    }
    
    /**
     * Get all keys in order (most recently used first)
     * 
     * @return list of keys in order
     */
    public java.util.List<K> getKeysInOrder() {
        java.util.List<K> keys = new java.util.ArrayList<>();
        Node current = head.next;
        while (current != tail) {
            keys.add(current.key);
            current = current.next;
        }
        return keys;
    }
    
    @Override
    public String toString() {
        return "LRUCache{" +
                "size=" + size() +
                ", capacity=" + capacity +
                ", keys=" + getKeysInOrder() +
                '}';
    }
}








