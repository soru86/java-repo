package diameter_of_binary_tree;

public class DiameterOfBinaryTreeMain {
    public static void main(String[] args) {
        DiameterOfBinaryTreeSolution.TreeNode root = new DiameterOfBinaryTreeSolution.TreeNode(1);
        root.left = new DiameterOfBinaryTreeSolution.TreeNode(2);
        root.right = new DiameterOfBinaryTreeSolution.TreeNode(3);
        root.left.left = new DiameterOfBinaryTreeSolution.TreeNode(4);
        root.left.right = new DiameterOfBinaryTreeSolution.TreeNode(5);

        DiameterOfBinaryTreeSolution solution = new DiameterOfBinaryTreeSolution();
        System.out.println("Diameter: " + solution.diameterOfBinaryTree(root));
    }
}







