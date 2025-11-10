package binary_tree_level_order_traversal;

public class BinaryTreeLevelOrderTraversalMain {
    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversalSolution.TreeNode root =
            new BinaryTreeLevelOrderTraversalSolution.TreeNode(3);
        root.left = new BinaryTreeLevelOrderTraversalSolution.TreeNode(9);
        root.right = new BinaryTreeLevelOrderTraversalSolution.TreeNode(20);
        root.right.left = new BinaryTreeLevelOrderTraversalSolution.TreeNode(15);
        root.right.right = new BinaryTreeLevelOrderTraversalSolution.TreeNode(7);

        BinaryTreeLevelOrderTraversalSolution solution = new BinaryTreeLevelOrderTraversalSolution();
        System.out.println("Level order: " + solution.levelOrder(root));
    }
}




