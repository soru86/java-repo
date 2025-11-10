package binary_tree_inorder_traversal;

public class BinaryTreeInorderTraversalMain {
    public static void main(String[] args) {
        BinaryTreeInorderTraversalSolution.TreeNode root = new BinaryTreeInorderTraversalSolution.TreeNode(1);
        root.right = new BinaryTreeInorderTraversalSolution.TreeNode(2);
        root.right.left = new BinaryTreeInorderTraversalSolution.TreeNode(3);

        BinaryTreeInorderTraversalSolution solution = new BinaryTreeInorderTraversalSolution();
        System.out.println("Inorder traversal: " + solution.inorderTraversal(root));
    }
}





