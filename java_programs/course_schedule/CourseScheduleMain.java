package course_schedule;

public class CourseScheduleMain {
    public static void main(String[] args) {
        CourseScheduleSolution solution = new CourseScheduleSolution();
        int numCourses = 2;
        int[][] prerequisites = {{1, 0}};
        System.out.println("Can finish: " + solution.canFinish(numCourses, prerequisites));
    }
}





