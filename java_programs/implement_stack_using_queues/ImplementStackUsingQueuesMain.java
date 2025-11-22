package implement_stack_using_queues;

public class ImplementStackUsingQueuesMain {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        System.out.println("Top: " + stack.top());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Is empty: " + stack.empty());
    }
}







