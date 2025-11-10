package validate_binary_search_tree;

public class ValidateBinarySearchTreeMain {
    public static void main(String[] args) {
        ValidateBinarySearchTreeSolution.TreeNode root = new ValidateBinarySearchTreeSolution.TreeNode(2);
        root.left = new ValidateBinarySearchTreeSolution.TreeNode(1);
        root.right = new ValidateBinarySearchTreeSolution.TreeNode(3);

        ValidateBinarySearchTreeSolution solution = new ValidateBinarySearchTreeSolution();
        System.out.println("Is valid BST: " + solution.isValidBST(root));
    }
}





