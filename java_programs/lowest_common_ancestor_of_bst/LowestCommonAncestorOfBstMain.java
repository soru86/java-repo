package lowest_common_ancestor_of_bst;

public class LowestCommonAncestorOfBstMain {
    public static void main(String[] args) {
        LowestCommonAncestorOfBstSolution.TreeNode root =
            new LowestCommonAncestorOfBstSolution.TreeNode(6);
        root.left = new LowestCommonAncestorOfBstSolution.TreeNode(2);
        root.right = new LowestCommonAncestorOfBstSolution.TreeNode(8);
        root.left.left = new LowestCommonAncestorOfBstSolution.TreeNode(0);
        root.left.right = new LowestCommonAncestorOfBstSolution.TreeNode(4);
        root.left.right.left = new LowestCommonAncestorOfBstSolution.TreeNode(3);
        root.left.right.right = new LowestCommonAncestorOfBstSolution.TreeNode(5);

        LowestCommonAncestorOfBstSolution solution = new LowestCommonAncestorOfBstSolution();
        var ancestor = solution.lowestCommonAncestor(root, root.left, root.left.right);
        System.out.println("LCA value: " + (ancestor == null ? "none" : ancestor.val));
    }
}

