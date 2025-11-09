package longest_common_subsequence;

public class LongestCommonSubsequenceMain {
    public static void main(String[] args) {
        LongestCommonSubsequenceSolution solution = new LongestCommonSubsequenceSolution();
        String text1 = "abcde";
        String text2 = "ace";
        System.out.println("Length of LCS: " + solution.longestCommonSubsequence(text1, text2));
    }
}

