package word_pattern;

import java.util.HashMap;
import java.util.Map;

public class WordPatternSolution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }
        Map<Character, String> mapPS = new HashMap<>();
        Map<String, Character> mapSP = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            String word = words[i];
            if (mapPS.containsKey(ch) && !mapPS.get(ch).equals(word)) {
                return false;
            }
            if (mapSP.containsKey(word) && mapSP.get(word) != ch) {
                return false;
            }
            mapPS.put(ch, word);
            mapSP.put(word, ch);
        }
        return true;
    }
}







