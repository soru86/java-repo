/**
 * Linked List with Loop Detection
 * 
 * This program implements a linked list and provides methods to:
 * 1. Check if the linked list contains a loop
 * 2. Find the starting point of the loop (if exists)
 */
public class LinkedList {
    
    /**
     * Node class for the linked list
     */
    static class Node {
        int data;
        Node next;
        
        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;
    
    /**
     * Add a node to the end of the linked list
     */
    public void append(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    
    /**
     * Create a loop in the linked list by connecting the last node
     * to the node at the specified position
     * 
     * @param position the position where the loop should start (0-indexed)
     */
    public void createLoop(int position) {
        if (head == null) {
            return;
        }
        
        Node current = head;
        Node loopNode = null;
        int count = 0;
        
        // Find the last node and the node at the specified position
        while (current.next != null) {
            if (count == position) {
                loopNode = current;
            }
            current = current.next;
            count++;
        }
        
        if (loopNode != null) {
            current.next = loopNode;
        }
    }
    
    /**
     * Check if the linked list contains a loop using Floyd's Cycle Detection Algorithm
     * (Also known as Tortoise and Hare algorithm)
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * @return true if loop exists, false otherwise
     */
    public boolean hasLoop() {
        if (head == null || head.next == null) {
            return false;
        }
        
        Node slow = head;  // Tortoise
        Node fast = head;  // Hare
        
        // Move slow one step and fast two steps
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            // If they meet, there's a loop
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Find the starting point of the loop in the linked list
     * 
     * @return the node where the loop starts, or null if no loop exists
     */
    public Node findLoopStart() {
        if (head == null || head.next == null) {
            return null;
        }
        
        Node slow = head;
        Node fast = head;
        boolean hasLoop = false;
        
        // First, detect if there's a loop
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasLoop = true;
                break;
            }
        }
        
        if (!hasLoop) {
            return null;
        }
        
        // Move slow to head and keep fast at meeting point
        // Move both one step at a time - they will meet at the loop start
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
    
    /**
     * Get the length of the loop
     * 
     * @return the length of the loop, or 0 if no loop exists
     */
    public int getLoopLength() {
        if (head == null || head.next == null) {
            return 0;
        }
        
        Node slow = head;
        Node fast = head;
        boolean hasLoop = false;
        
        // Detect loop
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasLoop = true;
                break;
            }
        }
        
        if (!hasLoop) {
            return 0;
        }
        
        // Count the length of the loop
        int length = 1;
        Node temp = slow.next;
        while (temp != slow) {
            length++;
            temp = temp.next;
        }
        
        return length;
    }
    
    /**
     * Print the linked list (works only for lists without loops to avoid infinite loop)
     */
    public void printList() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        Node current = head;
        int count = 0;
        int maxNodes = 20; // Limit to prevent infinite loop
        
        while (current != null && count < maxNodes) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
            count++;
        }
        
        if (count >= maxNodes) {
            System.out.print("... (loop detected)");
        }
        System.out.println();
    }
}





