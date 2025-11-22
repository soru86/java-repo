/**
 * Main class to demonstrate Linked List Loop Detection program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Linked List Loop Detection Program ===");
        
        // Test Case 1: Linked list without loop
        System.out.println("\n1. Testing linked list WITHOUT loop:");
        LinkedList list1 = new LinkedList();
        for (int i = 1; i <= 5; i++) {
            list1.append(i);
        }
        System.out.print("   List: ");
        list1.printList();
        System.out.println("   Has Loop: " + list1.hasLoop());
        System.out.println("   Loop Start: " + (list1.findLoopStart() != null ? 
            list1.findLoopStart().data : "No loop"));
        
        // Test Case 2: Linked list with loop
        System.out.println("\n2. Testing linked list WITH loop:");
        LinkedList list2 = new LinkedList();
        for (int i = 1; i <= 5; i++) {
            list2.append(i);
        }
        // Create a loop at position 2 (0-indexed), so node 3 points back to node 2
        list2.createLoop(2);
        System.out.print("   List (with loop): ");
        list2.printList();
        System.out.println("   Has Loop: " + list2.hasLoop());
        if (list2.findLoopStart() != null) {
            System.out.println("   Loop Start Node: " + list2.findLoopStart().data);
            System.out.println("   Loop Length: " + list2.getLoopLength());
        }
        
        // Test Case 3: Circular linked list (loop at head)
        System.out.println("\n3. Testing circular linked list (loop at head):");
        LinkedList list3 = new LinkedList();
        list3.append(1);
        list3.append(2);
        list3.append(3);
        list3.createLoop(0);
        System.out.print("   List (circular): ");
        list3.printList();
        System.out.println("   Has Loop: " + list3.hasLoop());
        if (list3.findLoopStart() != null) {
            System.out.println("   Loop Start Node: " + list3.findLoopStart().data);
        }
        
        // Test Case 4: Single node (no loop)
        System.out.println("\n4. Testing single node:");
        LinkedList list4 = new LinkedList();
        list4.append(1);
        System.out.print("   List: ");
        list4.printList();
        System.out.println("   Has Loop: " + list4.hasLoop());
        
        // Test Case 5: Empty list
        System.out.println("\n5. Testing empty list:");
        LinkedList list5 = new LinkedList();
        System.out.print("   List: ");
        list5.printList();
        System.out.println("   Has Loop: " + list5.hasLoop());
    }
}








