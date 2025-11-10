package find_all_anagrams_in_a_string;

import java.util.ArrayList;
import java.util.List;

public class FindAllAnagramsInAStringSolution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (p.length() > s.length()) {
            return result;
        }
        int[] need = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < p.length(); i++) {
            need[p.charAt(i) - 'a']++;
            window[s.charAt(i) - 'a']++;
        }
        if (matches(need, window)) {
            result.add(0);
        }
        for (int i = p.length(); i < s.length(); i++) {
            window[s.charAt(i) - 'a']++;
            window[s.charAt(i - p.length()) - 'a']--;
            if (matches(need, window)) {
                result.add(i - p.length() + 1);
            }
        }
        return result;
    }

    private boolean matches(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}




