package kth_smallest_element_in_a_bst;

import java.util.ArrayDeque;
import java.util.Deque;

public class KthSmallestElementInABstSolution {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            if (--k == 0) {
                return current.val;
            }
            current = current.right;
        }
        throw new IllegalArgumentException("Tree does not contain k elements");
    }
}





