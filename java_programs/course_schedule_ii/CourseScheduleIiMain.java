package course_schedule_ii;

import java.util.Arrays;

public class CourseScheduleIiMain {
    public static void main(String[] args) {
        CourseScheduleIiSolution solution = new CourseScheduleIiSolution();
        int numCourses = 4;
        int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        System.out.println("Course order: " + Arrays.toString(solution.findOrder(numCourses, prerequisites)));
    }
}

