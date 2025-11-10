package longest_repeating_character_replacement;

public class LongestRepeatingCharacterReplacementSolution {
    public int characterReplacement(String s, int k) {
        int[] counts = new int[26];
        int left = 0;
        int maxCount = 0;
        int result = 0;
        for (int right = 0; right < s.length(); right++) {
            int index = s.charAt(right) - 'A';
            counts[index]++;
            maxCount = Math.max(maxCount, counts[index]);
            while (right - left + 1 - maxCount > k) {
                counts[s.charAt(left) - 'A']--;
                left++;
            }
            result = Math.max(result, right - left + 1);
        }
        return result;
    }
}





