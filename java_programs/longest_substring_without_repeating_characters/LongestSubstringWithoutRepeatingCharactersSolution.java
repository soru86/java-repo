package longest_substring_without_repeating_characters;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharactersSolution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);
            if (lastIndex.containsKey(current) && lastIndex.get(current) >= left) {
                left = lastIndex.get(current) + 1;
            }
            lastIndex.put(current, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}





