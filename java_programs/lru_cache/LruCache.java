package lru_cache;

import java.util.HashMap;
import java.util.Map;

public class LruCache {
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0);
    private final Node tail = new Node(0, 0);

    public LruCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToFront(node);
            return;
        }
        if (map.size() == capacity) {
            Node removed = removeTail();
            if (removed != null) {
                map.remove(removed.key);
            }
        }
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        addToFront(newNode);
    }

    private void moveToFront(Node node) {
        remove(node);
        addToFront(node);
    }

    private void addToFront(Node node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node removeTail() {
        if (tail.prev == head) {
            return null;
        }
        Node node = tail.prev;
        remove(node);
        return node;
    }
}

