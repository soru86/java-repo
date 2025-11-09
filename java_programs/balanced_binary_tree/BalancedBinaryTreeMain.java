package balanced_binary_tree;

public class BalancedBinaryTreeMain {
    public static void main(String[] args) {
        BalancedBinaryTreeSolution.TreeNode root = new BalancedBinaryTreeSolution.TreeNode(3);
        root.left = new BalancedBinaryTreeSolution.TreeNode(9);
        root.right = new BalancedBinaryTreeSolution.TreeNode(20);
        root.right.left = new BalancedBinaryTreeSolution.TreeNode(15);
        root.right.right = new BalancedBinaryTreeSolution.TreeNode(7);

        BalancedBinaryTreeSolution solution = new BalancedBinaryTreeSolution();
        System.out.println("Is balanced: " + solution.isBalanced(root));
    }
}

