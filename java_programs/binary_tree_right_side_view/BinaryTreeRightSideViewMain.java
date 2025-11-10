package binary_tree_right_side_view;

public class BinaryTreeRightSideViewMain {
    public static void main(String[] args) {
        BinaryTreeRightSideViewSolution.TreeNode root =
            new BinaryTreeRightSideViewSolution.TreeNode(1);
        root.left = new BinaryTreeRightSideViewSolution.TreeNode(2);
        root.right = new BinaryTreeRightSideViewSolution.TreeNode(3);
        root.left.right = new BinaryTreeRightSideViewSolution.TreeNode(5);
        root.right.right = new BinaryTreeRightSideViewSolution.TreeNode(4);

        BinaryTreeRightSideViewSolution solution = new BinaryTreeRightSideViewSolution();
        System.out.println("Right side view: " + solution.rightSideView(root));
    }
}





