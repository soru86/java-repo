package meeting_rooms;

public class MeetingRoomsMain {
    public static void main(String[] args) {
        MeetingRoomsSolution solution = new MeetingRoomsSolution();
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Can attend all meetings: " + solution.canAttendMeetings(intervals));
    }
}





