package binary_search_tree_iterator;

public class BinarySearchTreeIteratorMain {
    public static void main(String[] args) {
        BinarySearchTreeIterator.TreeNode root = new BinarySearchTreeIterator.TreeNode(7);
        root.left = new BinarySearchTreeIterator.TreeNode(3);
        root.right = new BinarySearchTreeIterator.TreeNode(15);
        root.right.left = new BinarySearchTreeIterator.TreeNode(9);
        root.right.right = new BinarySearchTreeIterator.TreeNode(20);

        BinarySearchTreeIterator iterator = new BinarySearchTreeIterator(root);
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
}




