package word_ladder;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadderSolution {
    public int ladderLength(String beginWord, String endWord, java.util.List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) {
            return 0;
        }
        Map<String, java.util.List<String>> patterns = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                String pattern = word.substring(0, i) + '*' + word.substring(i + 1);
                patterns.computeIfAbsent(pattern, k -> new java.util.ArrayList<>()).add(word);
            }
        }
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        int steps = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) {
                    return steps;
                }
                for (int j = 0; j < word.length(); j++) {
                    String pattern = word.substring(0, j) + '*' + word.substring(j + 1);
                    for (String neighbor : patterns.getOrDefault(pattern, java.util.List.of())) {
                        if (visited.add(neighbor)) {
                            queue.offer(neighbor);
                        }
                    }
                }
            }
            steps++;
        }
        return 0;
    }
}







