package word_pattern;

public class WordPatternMain {
    public static void main(String[] args) {
        WordPatternSolution solution = new WordPatternSolution();
        String pattern = "abba";
        String s = "dog cat cat dog";
        System.out.println("Matches pattern: " + solution.wordPattern(pattern, s));
    }
}

