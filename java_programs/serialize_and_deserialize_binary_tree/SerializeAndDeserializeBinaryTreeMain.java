package serialize_and_deserialize_binary_tree;

public class SerializeAndDeserializeBinaryTreeMain {
    public static void main(String[] args) {
        SerializeAndDeserializeBinaryTreeSolution.TreeNode root =
            new SerializeAndDeserializeBinaryTreeSolution.TreeNode(1);
        root.left = new SerializeAndDeserializeBinaryTreeSolution.TreeNode(2);
        root.right = new SerializeAndDeserializeBinaryTreeSolution.TreeNode(3);
        root.right.left = new SerializeAndDeserializeBinaryTreeSolution.TreeNode(4);
        root.right.right = new SerializeAndDeserializeBinaryTreeSolution.TreeNode(5);

        SerializeAndDeserializeBinaryTreeSolution solution = new SerializeAndDeserializeBinaryTreeSolution();
        String serialized = solution.serialize(root);
        System.out.println("Serialized: " + serialized);
        var deserialized = solution.deserialize(serialized);
        System.out.println("Deserialized root value: " + (deserialized == null ? "null" : deserialized.val));
    }
}







