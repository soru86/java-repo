package merge_k_sorted_lists;

public class MergeKSortedListsMain {
    public static void main(String[] args) {
        MergeKSortedListsSolution.ListNode l1 = list(1, 4, 5);
        MergeKSortedListsSolution.ListNode l2 = list(1, 3, 4);
        MergeKSortedListsSolution.ListNode l3 = list(2, 6);
        MergeKSortedListsSolution solution = new MergeKSortedListsSolution();
        MergeKSortedListsSolution.ListNode merged = solution.mergeKLists(new MergeKSortedListsSolution.ListNode[]{l1, l2, l3});
        printList(merged);
    }

    private static MergeKSortedListsSolution.ListNode list(int... values) {
        MergeKSortedListsSolution.ListNode dummy = new MergeKSortedListsSolution.ListNode(0);
        MergeKSortedListsSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new MergeKSortedListsSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void printList(MergeKSortedListsSolution.ListNode head) {
        MergeKSortedListsSolution.ListNode current = head;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        System.out.println("Merged list: " + sb);
    }
}

