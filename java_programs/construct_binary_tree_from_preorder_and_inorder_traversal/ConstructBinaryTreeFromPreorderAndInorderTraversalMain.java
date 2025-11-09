package construct_binary_tree_from_preorder_and_inorder_traversal;

public class ConstructBinaryTreeFromPreorderAndInorderTraversalMain {
    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        ConstructBinaryTreeFromPreorderAndInorderTraversalSolution solution =
            new ConstructBinaryTreeFromPreorderAndInorderTraversalSolution();
        ConstructBinaryTreeFromPreorderAndInorderTraversalSolution.TreeNode root =
            solution.buildTree(preorder, inorder);
        System.out.println("Constructed root value: " + (root == null ? "null" : root.val));
    }
}

