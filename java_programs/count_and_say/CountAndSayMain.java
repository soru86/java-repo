package count_and_say;

public class CountAndSayMain {
    public static void main(String[] args) {
        CountAndSaySolution solution = new CountAndSaySolution();
        int n = 5;
        System.out.println("Count and Say term " + n + ": " + solution.countAndSay(n));
    }
}

