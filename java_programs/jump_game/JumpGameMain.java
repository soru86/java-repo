package jump_game;

public class JumpGameMain {
    public static void main(String[] args) {
        JumpGameSolution solution = new JumpGameSolution();
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println("Can reach end: " + solution.canJump(nums));
    }
}




