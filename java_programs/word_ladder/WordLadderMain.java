package word_ladder;

import java.util.Arrays;

public class WordLadderMain {
    public static void main(String[] args) {
        WordLadderSolution solution = new WordLadderSolution();
        String begin = "hit";
        String end = "cog";
        var words = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        System.out.println("Ladder length: " + solution.ladderLength(begin, end, words));
    }
}





