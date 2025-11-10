package meeting_rooms_ii;

public class MeetingRoomsIIMain {
    public static void main(String[] args) {
        MeetingRoomsIISolution solution = new MeetingRoomsIISolution();
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Minimum rooms needed: " + solution.minMeetingRooms(intervals));
    }
}





