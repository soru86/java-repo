package path_sum;

public class PathSumMain {
    public static void main(String[] args) {
        PathSumSolution.TreeNode root = new PathSumSolution.TreeNode(5);
        root.left = new PathSumSolution.TreeNode(4);
        root.right = new PathSumSolution.TreeNode(8);
        root.left.left = new PathSumSolution.TreeNode(11);
        root.left.left.left = new PathSumSolution.TreeNode(7);
        root.left.left.right = new PathSumSolution.TreeNode(2);
        root.right.left = new PathSumSolution.TreeNode(13);
        root.right.right = new PathSumSolution.TreeNode(4);
        root.right.right.right = new PathSumSolution.TreeNode(1);

        PathSumSolution solution = new PathSumSolution();
        System.out.println("Has path sum 22: " + solution.hasPathSum(root, 22));
    }
}







