package kth_smallest_element_in_a_bst;

public class KthSmallestElementInABstMain {
    public static void main(String[] args) {
        KthSmallestElementInABstSolution.TreeNode root =
            new KthSmallestElementInABstSolution.TreeNode(3);
        root.left = new KthSmallestElementInABstSolution.TreeNode(1);
        root.right = new KthSmallestElementInABstSolution.TreeNode(4);
        root.left.right = new KthSmallestElementInABstSolution.TreeNode(2);

        KthSmallestElementInABstSolution solution = new KthSmallestElementInABstSolution();
        System.out.println("Kth smallest: " + solution.kthSmallest(root, 1));
    }
}




