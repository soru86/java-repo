package binary_tree_paths;

public class BinaryTreePathsMain {
    public static void main(String[] args) {
        BinaryTreePathsSolution.TreeNode root = new BinaryTreePathsSolution.TreeNode(1);
        root.left = new BinaryTreePathsSolution.TreeNode(2);
        root.right = new BinaryTreePathsSolution.TreeNode(3);
        root.left.right = new BinaryTreePathsSolution.TreeNode(5);

        BinaryTreePathsSolution solution = new BinaryTreePathsSolution();
        System.out.println("Paths: " + solution.binaryTreePaths(root));
    }
}





