package find_all_anagrams_in_a_string;

public class FindAllAnagramsInAStringMain {
    public static void main(String[] args) {
        FindAllAnagramsInAStringSolution solution = new FindAllAnagramsInAStringSolution();
        String s = "cbaebabacd";
        String p = "abc";
        System.out.println("Anagram indices: " + solution.findAnagrams(s, p));
    }
}




