package add_two_numbers;

public class AddTwoNumbersMain {
    public static void main(String[] args) {
        AddTwoNumbersSolution.ListNode l1 = list(2, 4, 3);
        AddTwoNumbersSolution.ListNode l2 = list(5, 6, 4);
        AddTwoNumbersSolution solution = new AddTwoNumbersSolution();
        AddTwoNumbersSolution.ListNode sum = solution.addTwoNumbers(l1, l2);
        print(sum);
    }

    private static AddTwoNumbersSolution.ListNode list(int... values) {
        AddTwoNumbersSolution.ListNode dummy = new AddTwoNumbersSolution.ListNode(0);
        AddTwoNumbersSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new AddTwoNumbersSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static void print(AddTwoNumbersSolution.ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println("Sum list: " + sb);
    }
}





