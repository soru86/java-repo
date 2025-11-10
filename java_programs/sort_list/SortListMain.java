package sort_list;

public class SortListMain {
    public static void main(String[] args) {
        SortListSolution.ListNode head = list(4, 2, 1, 3);
        SortListSolution solution = new SortListSolution();
        SortListSolution.ListNode sorted = solution.sortList(head);
        print(sorted);
    }

    private static SortListSolution.ListNode list(int... values) {
        SortListSolution.ListNode dummy = new SortListSolution.ListNode(0);
        SortListSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new SortListSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void print(SortListSolution.ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println("Sorted list: " + sb);
    }
}





