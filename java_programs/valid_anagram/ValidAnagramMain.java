package valid_anagram;

public class ValidAnagramMain {
    public static void main(String[] args) {
        ValidAnagramSolution solution = new ValidAnagramSolution();
        String s = "anagram";
        String t = "nagaram";
        System.out.println("Is anagram: " + solution.isAnagram(s, t));
    }
}




