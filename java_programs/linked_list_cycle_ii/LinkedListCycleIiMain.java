package linked_list_cycle_ii;

public class LinkedListCycleIiMain {
    public static void main(String[] args) {
        LinkedListCycleIiSolution.ListNode head = new LinkedListCycleIiSolution.ListNode(3);
        head.next = new LinkedListCycleIiSolution.ListNode(2);
        head.next.next = new LinkedListCycleIiSolution.ListNode(0);
        head.next.next.next = new LinkedListCycleIiSolution.ListNode(-4);
        head.next.next.next.next = head.next;

        LinkedListCycleIiSolution solution = new LinkedListCycleIiSolution();
        LinkedListCycleIiSolution.ListNode entry = solution.detectCycle(head);
        System.out.println("Cycle starts at: " + (entry == null ? "null" : entry.val));
    }
}




