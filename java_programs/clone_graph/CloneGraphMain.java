package clone_graph;

public class CloneGraphMain {
    public static void main(String[] args) {
        CloneGraphSolution.Node node1 = new CloneGraphSolution.Node(1);
        CloneGraphSolution.Node node2 = new CloneGraphSolution.Node(2);
        CloneGraphSolution.Node node3 = new CloneGraphSolution.Node(3);
        CloneGraphSolution.Node node4 = new CloneGraphSolution.Node(4);
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);

        CloneGraphSolution solution = new CloneGraphSolution();
        CloneGraphSolution.Node cloned = solution.cloneGraph(node1);
        System.out.println("Cloned node value: " + (cloned == null ? "null" : cloned.val));
    }
}





