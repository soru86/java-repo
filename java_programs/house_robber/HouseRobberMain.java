package house_robber;

public class HouseRobberMain {
    public static void main(String[] args) {
        HouseRobberSolution solution = new HouseRobberSolution();
        int[] nums = {1, 2, 3, 1};
        System.out.println("Max rob amount: " + solution.rob(nums));
    }
}

