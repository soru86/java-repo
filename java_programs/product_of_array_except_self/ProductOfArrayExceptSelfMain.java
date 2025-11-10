package product_of_array_except_self;

import java.util.Arrays;

public class ProductOfArrayExceptSelfMain {
    public static void main(String[] args) {
        ProductOfArrayExceptSelfSolution solution = new ProductOfArrayExceptSelfSolution();
        int[] nums = {1, 2, 3, 4};
        System.out.println("Products: " + Arrays.toString(solution.productExceptSelf(nums)));
    }
}




