package palindrome_partitioning_ii;

public class PalindromePartitioningIiSolution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j < 2 || isPalindrome[j + 1][i - 1])) {
                    isPalindrome[j][i] = true;
                    min = j == 0 ? 0 : Math.min(min, dp[j - 1] + 1);
                }
            }
            dp[i] = min;
        }
        return dp[n - 1];
    }
}

