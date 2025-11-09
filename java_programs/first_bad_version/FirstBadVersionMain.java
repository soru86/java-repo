package first_bad_version;

public class FirstBadVersionMain {
    public static void main(String[] args) {
        int n = 10;
        int firstBad = 4;
        FirstBadVersionSolution solution = new FirstBadVersionSolution();
        FirstBadVersionSolution.VersionControl checker = version -> version >= firstBad;
        System.out.println("First bad version: " + solution.firstBadVersion(n, checker));
    }
}

