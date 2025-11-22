package symmetric_tree;

public class SymmetricTreeMain {
    public static void main(String[] args) {
        SymmetricTreeSolution.TreeNode root = new SymmetricTreeSolution.TreeNode(1);
        root.left = new SymmetricTreeSolution.TreeNode(2);
        root.right = new SymmetricTreeSolution.TreeNode(2);
        root.left.left = new SymmetricTreeSolution.TreeNode(3);
        root.left.right = new SymmetricTreeSolution.TreeNode(4);
        root.right.left = new SymmetricTreeSolution.TreeNode(4);
        root.right.right = new SymmetricTreeSolution.TreeNode(3);

        SymmetricTreeSolution solution = new SymmetricTreeSolution();
        System.out.println("Is symmetric: " + solution.isSymmetric(root));
    }
}







