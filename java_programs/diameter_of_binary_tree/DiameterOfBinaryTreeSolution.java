package diameter_of_binary_tree;

public class DiameterOfBinaryTreeSolution {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        depth(root);
        return maxDiameter;
    }

    private int maxDiameter;

    private int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = depth(node.left);
        int rightDepth = depth(node.right);
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);
        return 1 + Math.max(leftDepth, rightDepth);
    }
}

