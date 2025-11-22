package min_stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {
    private final Deque<Integer> values = new ArrayDeque<>();
    private final Deque<Integer> mins = new ArrayDeque<>();

    public void push(int val) {
        values.push(val);
        if (mins.isEmpty() || val <= mins.peek()) {
            mins.push(val);
        }
    }

    public void pop() {
        int removed = values.pop();
        if (removed == mins.peek()) {
            mins.pop();
        }
    }

    public int top() {
        return values.peek();
    }

    public int getMin() {
        return mins.peek();
    }
}







