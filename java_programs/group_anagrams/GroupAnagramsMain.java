package group_anagrams;

import java.util.List;

public class GroupAnagramsMain {
    public static void main(String[] args) {
        GroupAnagramsSolution solution = new GroupAnagramsSolution();
        String[] input = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> groups = solution.groupAnagrams(input);
        System.out.println("Grouped anagrams: " + groups);
    }
}







