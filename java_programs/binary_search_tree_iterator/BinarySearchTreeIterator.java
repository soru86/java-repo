package binary_search_tree_iterator;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinarySearchTreeIterator {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private final Deque<TreeNode> stack = new ArrayDeque<>();

    public BinarySearchTreeIterator(TreeNode root) {
        pushLeft(root);
    }

    public int next() {
        TreeNode node = stack.pop();
        if (node.right != null) {
            pushLeft(node.right);
        }
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}





