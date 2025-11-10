/**
 * Main class to demonstrate Middle Element Finder program
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Find Middle Element of Linked List in One Pass ===");
        
        // Test Case 1: Odd number of nodes
        System.out.println("\n1. Linked List with ODD number of nodes:");
        MiddleElementFinder list1 = new MiddleElementFinder();
        for (int i = 1; i <= 5; i++) {
            list1.append(i);
        }
        System.out.print("   List: ");
        list1.printList();
        System.out.println("   Length: " + list1.getLength());
        MiddleElementFinder.Node middle1 = list1.findMiddle();
        if (middle1 != null) {
            System.out.println("   Middle Element: " + middle1.data);
        }
        
        // Test Case 2: Even number of nodes
        System.out.println("\n2. Linked List with EVEN number of nodes:");
        MiddleElementFinder list2 = new MiddleElementFinder();
        for (int i = 1; i <= 6; i++) {
            list2.append(i);
        }
        System.out.print("   List: ");
        list2.printList();
        System.out.println("   Length: " + list2.getLength());
        MiddleElementFinder.Node middle2 = list2.findMiddle();
        if (middle2 != null) {
            System.out.println("   Middle Element (second): " + middle2.data);
        }
        MiddleElementFinder.Node middle2First = list2.findMiddleFirst();
        if (middle2First != null) {
            System.out.println("   Middle Element (first): " + middle2First.data);
        }
        
        // Test Case 3: Single node
        System.out.println("\n3. Linked List with SINGLE node:");
        MiddleElementFinder list3 = new MiddleElementFinder();
        list3.append(42);
        System.out.print("   List: ");
        list3.printList();
        MiddleElementFinder.Node middle3 = list3.findMiddle();
        if (middle3 != null) {
            System.out.println("   Middle Element: " + middle3.data);
        }
        
        // Test Case 4: Two nodes
        System.out.println("\n4. Linked List with TWO nodes:");
        MiddleElementFinder list4 = new MiddleElementFinder();
        list4.append(10);
        list4.append(20);
        System.out.print("   List: ");
        list4.printList();
        MiddleElementFinder.Node middle4 = list4.findMiddle();
        if (middle4 != null) {
            System.out.println("   Middle Element: " + middle4.data);
        }
        
        // Test Case 5: Larger list
        System.out.println("\n5. Linked List with 10 nodes:");
        MiddleElementFinder list5 = new MiddleElementFinder();
        for (int i = 1; i <= 10; i++) {
            list5.append(i * 10);
        }
        System.out.print("   List: ");
        list5.printList();
        System.out.println("   Length: " + list5.getLength());
        MiddleElementFinder.Node middle5 = list5.findMiddle();
        if (middle5 != null) {
            System.out.println("   Middle Element: " + middle5.data);
        }
    }
}






