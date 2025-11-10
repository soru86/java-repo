package intersection_of_two_arrays_ii;

import java.util.Arrays;

public class IntersectionOfTwoArraysIIMain {
    public static void main(String[] args) {
        IntersectionOfTwoArraysIISolution solution = new IntersectionOfTwoArraysIISolution();
        int[] nums1 = {4, 9, 5};
        int[] nums2 = {9, 4, 9, 8, 4};
        int[] intersection = solution.intersect(nums1, nums2);
        System.out.println("Intersection: " + Arrays.toString(intersection));
    }
}





