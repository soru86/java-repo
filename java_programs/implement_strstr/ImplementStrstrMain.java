package implement_strstr;

public class ImplementStrstrMain {
    public static void main(String[] args) {
        ImplementStrstrSolution solution = new ImplementStrstrSolution();
        String haystack = "hello";
        String needle = "ll";
        System.out.println("Index of needle: " + solution.strStr(haystack, needle));
    }
}





