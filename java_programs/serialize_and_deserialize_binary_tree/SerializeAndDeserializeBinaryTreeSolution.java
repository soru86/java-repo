package serialize_and_deserialize_binary_tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class SerializeAndDeserializeBinaryTreeSolution {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(',');
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        return sb.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] parts = data.split(",");
        Queue<TreeNode> queue = new ArrayDeque<>();
        TreeNode root = new TreeNode(Integer.parseInt(parts[0]));
        queue.offer(root);
        int index = 1;
        while (!queue.isEmpty() && index < parts.length) {
            TreeNode node = queue.poll();
            if (!parts[index].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(parts[index]));
                node.left = left;
                queue.offer(left);
            }
            index++;
            if (index < parts.length && !parts[index].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(parts[index]));
                node.right = right;
                queue.offer(right);
            }
            index++;
        }
        return root;
    }
}

