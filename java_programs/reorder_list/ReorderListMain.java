package reorder_list;

public class ReorderListMain {
    public static void main(String[] args) {
        ReorderListSolution.ListNode head = list(1, 2, 3, 4);
        ReorderListSolution solution = new ReorderListSolution();
        solution.reorderList(head);
        print(head);
    }

    private static ReorderListSolution.ListNode list(int... values) {
        ReorderListSolution.ListNode dummy = new ReorderListSolution.ListNode(0);
        ReorderListSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new ReorderListSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void print(ReorderListSolution.ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println("Reordered list: " + sb);
    }
}

