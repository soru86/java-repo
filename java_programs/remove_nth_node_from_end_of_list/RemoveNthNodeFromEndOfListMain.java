package remove_nth_node_from_end_of_list;

public class RemoveNthNodeFromEndOfListMain {
    public static void main(String[] args) {
        RemoveNthNodeFromEndOfListSolution.ListNode head = list(1, 2, 3, 4, 5);
        RemoveNthNodeFromEndOfListSolution solution = new RemoveNthNodeFromEndOfListSolution();
        RemoveNthNodeFromEndOfListSolution.ListNode updated = solution.removeNthFromEnd(head, 2);
        print(updated);
    }

    private static RemoveNthNodeFromEndOfListSolution.ListNode list(int... values) {
        RemoveNthNodeFromEndOfListSolution.ListNode dummy = new RemoveNthNodeFromEndOfListSolution.ListNode(0);
        RemoveNthNodeFromEndOfListSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new RemoveNthNodeFromEndOfListSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void print(RemoveNthNodeFromEndOfListSolution.ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println("Updated list: " + sb);
    }
}

