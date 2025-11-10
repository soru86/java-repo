package longest_repeating_character_replacement;

public class LongestRepeatingCharacterReplacementMain {
    public static void main(String[] args) {
        LongestRepeatingCharacterReplacementSolution solution =
            new LongestRepeatingCharacterReplacementSolution();
        String s = "AABABBA";
        int k = 1;
        System.out.println("Longest length: " + solution.characterReplacement(s, k));
    }
}





