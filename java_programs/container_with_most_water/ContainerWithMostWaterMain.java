package container_with_most_water;

public class ContainerWithMostWaterMain {
    public static void main(String[] args) {
        ContainerWithMostWaterSolution solution = new ContainerWithMostWaterSolution();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Max area: " + solution.maxArea(height));
    }
}







