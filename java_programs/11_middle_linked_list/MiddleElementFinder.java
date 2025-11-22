/**
 * Find Middle Element of Linked List in One Pass
 * 
 * This program demonstrates how to find the middle element of a linked list
 * using the two-pointer technique (tortoise and hare algorithm).
 */
public class MiddleElementFinder {
    
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
     * Find middle element using two-pointer technique (tortoise and hare)
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * @return the middle node, or null if list is empty
     */
    public Node findMiddle() {
        if (head == null) {
            return null;
        }
        
        Node slow = head;  // Tortoise - moves one step at a time
        Node fast = head;  // Hare - moves two steps at a time
        
        // Move fast pointer twice as fast as slow pointer
        // When fast reaches the end, slow will be at the middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Find middle element when list has even number of nodes
     * Returns the second middle element (for even length)
     * 
     * @return the middle node
     */
    public Node findMiddleSecond() {
        if (head == null) {
            return null;
        }
        
        Node slow = head;
        Node fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Find middle element - returns first middle for even length
     * 
     * @return the middle node
     */
    public Node findMiddleFirst() {
        if (head == null) {
            return null;
        }
        
        Node slow = head;
        Node fast = head.next;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Get the length of the linked list
     * 
     * @return the length of the list
     */
    public int getLength() {
        int length = 0;
        Node current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
    
    /**
     * Print the linked list
     */
    public void printList() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        Node current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
}








