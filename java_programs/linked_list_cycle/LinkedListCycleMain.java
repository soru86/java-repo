package linked_list_cycle;

public class LinkedListCycleMain {
    public static void main(String[] args) {
        LinkedListCycleSolution.ListNode head = new LinkedListCycleSolution.ListNode(3);
        head.next = new LinkedListCycleSolution.ListNode(2);
        head.next.next = new LinkedListCycleSolution.ListNode(0);
        head.next.next.next = new LinkedListCycleSolution.ListNode(-4);
        head.next.next.next.next = head.next;

        LinkedListCycleSolution solution = new LinkedListCycleSolution();
        System.out.println("Has cycle: " + solution.hasCycle(head));
    }
}







