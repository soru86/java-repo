package invert_binary_tree;

public class InvertBinaryTreeMain {
    public static void main(String[] args) {
        InvertBinaryTreeSolution.TreeNode root = new InvertBinaryTreeSolution.TreeNode(4);
        root.left = new InvertBinaryTreeSolution.TreeNode(2);
        root.right = new InvertBinaryTreeSolution.TreeNode(7);
        root.left.left = new InvertBinaryTreeSolution.TreeNode(1);
        root.left.right = new InvertBinaryTreeSolution.TreeNode(3);
        root.right.left = new InvertBinaryTreeSolution.TreeNode(6);
        root.right.right = new InvertBinaryTreeSolution.TreeNode(9);

        InvertBinaryTreeSolution solution = new InvertBinaryTreeSolution();
        solution.invertTree(root);
        System.out.println("Tree inverted successfully.");
    }
}





