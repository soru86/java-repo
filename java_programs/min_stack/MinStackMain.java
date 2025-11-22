package min_stack;

public class MinStackMain {
    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        System.out.println("Min: " + stack.getMin());
        stack.pop();
        System.out.println("Top: " + stack.top());
        System.out.println("Min: " + stack.getMin());
    }
}







