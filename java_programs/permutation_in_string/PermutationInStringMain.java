package permutation_in_string;

public class PermutationInStringMain {
    public static void main(String[] args) {
        PermutationInStringSolution solution = new PermutationInStringSolution();
        String s1 = "ab";
        String s2 = "eidbaooo";
        System.out.println("Contains permutation: " + solution.checkInclusion(s1, s2));
    }
}

