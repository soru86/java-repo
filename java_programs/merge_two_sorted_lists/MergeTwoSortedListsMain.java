package merge_two_sorted_lists;

public class MergeTwoSortedListsMain {
    public static void main(String[] args) {
        MergeTwoSortedListsSolution solution = new MergeTwoSortedListsSolution();

        MergeTwoSortedListsSolution.ListNode first = buildList(new int[]{1, 2, 4});
        MergeTwoSortedListsSolution.ListNode second = buildList(new int[]{1, 3, 4});

        MergeTwoSortedListsSolution.ListNode merged = solution.mergeTwoLists(first, second);
        System.out.println("Merged list:");
        printList(merged);
    }

    private static MergeTwoSortedListsSolution.ListNode buildList(int[] values) {
        MergeTwoSortedListsSolution.ListNode dummy = new MergeTwoSortedListsSolution.ListNode(0);
        MergeTwoSortedListsSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new MergeTwoSortedListsSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void printList(MergeTwoSortedListsSolution.ListNode head) {
        MergeTwoSortedListsSolution.ListNode current = head;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        System.out.println(sb.toString());
    }
}

