package jump_game_ii;

public class JumpGameIiMain {
    public static void main(String[] args) {
        JumpGameIiSolution solution = new JumpGameIiSolution();
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println("Minimum jumps: " + solution.jump(nums));
    }
}

