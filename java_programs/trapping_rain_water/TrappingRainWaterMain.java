package trapping_rain_water;

public class TrappingRainWaterMain {
    public static void main(String[] args) {
        TrappingRainWaterSolution solution = new TrappingRainWaterSolution();
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Trapped water: " + solution.trap(height));
    }
}







