package maximum_depth_of_binary_tree;

public class MaximumDepthOfBinaryTreeMain {
    public static void main(String[] args) {
        MaximumDepthOfBinaryTreeSolution.TreeNode root =
            new MaximumDepthOfBinaryTreeSolution.TreeNode(3);
        root.left = new MaximumDepthOfBinaryTreeSolution.TreeNode(9);
        root.right = new MaximumDepthOfBinaryTreeSolution.TreeNode(20);
        root.right.left = new MaximumDepthOfBinaryTreeSolution.TreeNode(15);
        root.right.right = new MaximumDepthOfBinaryTreeSolution.TreeNode(7);

        MaximumDepthOfBinaryTreeSolution solution = new MaximumDepthOfBinaryTreeSolution();
        System.out.println("Maximum depth: " + solution.maxDepth(root));
    }
}

