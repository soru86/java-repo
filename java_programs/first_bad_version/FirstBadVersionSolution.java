package first_bad_version;

public class FirstBadVersionSolution {
    public interface VersionControl {
        boolean isBadVersion(int version);
    }

    public int firstBadVersion(int n, VersionControl checker) {
        int left = 1;
        int right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (checker.isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}

