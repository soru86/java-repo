package edit_distance;

public class EditDistanceMain {
    public static void main(String[] args) {
        EditDistanceSolution solution = new EditDistanceSolution();
        String word1 = "horse";
        String word2 = "ros";
        System.out.println("Edit distance: " + solution.minDistance(word1, word2));
    }
}




