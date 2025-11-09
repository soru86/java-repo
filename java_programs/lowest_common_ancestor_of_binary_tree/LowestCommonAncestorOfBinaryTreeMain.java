package lowest_common_ancestor_of_binary_tree;

public class LowestCommonAncestorOfBinaryTreeMain {
    public static void main(String[] args) {
        LowestCommonAncestorOfBinaryTreeSolution.TreeNode root =
            new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(3);
        root.left = new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(5);
        root.right = new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(1);
        root.left.left = new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(6);
        root.left.right = new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(2);
        root.left.right.left = new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(7);
        root.left.right.right = new LowestCommonAncestorOfBinaryTreeSolution.TreeNode(4);

        LowestCommonAncestorOfBinaryTreeSolution solution = new LowestCommonAncestorOfBinaryTreeSolution();
        var ancestor = solution.lowestCommonAncestor(root, root.left, root.left.right.right);
        System.out.println("LCA value: " + (ancestor == null ? "none" : ancestor.val));
    }
}

