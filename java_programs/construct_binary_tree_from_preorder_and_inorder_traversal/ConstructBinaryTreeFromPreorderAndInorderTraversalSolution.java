package construct_binary_tree_from_preorder_and_inorder_traversal;

import java.util.HashMap;
import java.util.Map;

public class ConstructBinaryTreeFromPreorderAndInorderTraversalSolution {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, 0, inorder.length - 1, indexMap);
    }

    private TreeNode build(int[] preorder, int preLeft, int preRight,
                           int inLeft, int inRight, Map<Integer, Integer> indexMap) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        int rootVal = preorder[preLeft];
        TreeNode root = new TreeNode(rootVal);
        int inIndex = indexMap.get(rootVal);
        int leftSize = inIndex - inLeft;
        root.left = build(preorder, preLeft + 1, preLeft + leftSize, inLeft, inIndex - 1, indexMap);
        root.right = build(preorder, preLeft + leftSize + 1, preRight, inIndex + 1, inRight, indexMap);
        return root;
    }
}




