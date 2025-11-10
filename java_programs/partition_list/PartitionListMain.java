package partition_list;

public class PartitionListMain {
    public static void main(String[] args) {
        PartitionListSolution.ListNode head = list(1, 4, 3, 2, 5, 2);
        PartitionListSolution solution = new PartitionListSolution();
        PartitionListSolution.ListNode partitioned = solution.partition(head, 3);
        print(partitioned);
    }

    private static PartitionListSolution.ListNode list(int... values) {
        PartitionListSolution.ListNode dummy = new PartitionListSolution.ListNode(0);
        PartitionListSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new PartitionListSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void print(PartitionListSolution.ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println("Partitioned list: " + sb);
    }
}




