package palindrome_linked_list;

public class PalindromeLinkedListMain {
    public static void main(String[] args) {
        PalindromeLinkedListSolution.ListNode head = list(1, 2, 2, 1);
        PalindromeLinkedListSolution solution = new PalindromeLinkedListSolution();
        System.out.println("Is palindrome: " + solution.isPalindrome(head));
    }

    private static PalindromeLinkedListSolution.ListNode list(int... values) {
        PalindromeLinkedListSolution.ListNode dummy = new PalindromeLinkedListSolution.ListNode(0);
        PalindromeLinkedListSolution.ListNode current = dummy;
        for (int value : values) {
            current.next = new PalindromeLinkedListSolution.ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }
}







