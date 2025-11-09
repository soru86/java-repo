package count_and_say;

public class CountAndSaySolution {
    public String countAndSay(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        String current = "1";
        for (int i = 1; i < n; i++) {
            current = next(current);
        }
        return current;
    }

    private String next(String term) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= term.length(); i++) {
            if (i < term.length() && term.charAt(i) == term.charAt(i - 1)) {
                count++;
            } else {
                sb.append(count).append(term.charAt(i - 1));
                count = 1;
            }
        }
        return sb.toString();
    }
}

