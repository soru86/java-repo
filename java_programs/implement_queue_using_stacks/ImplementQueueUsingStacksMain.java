package implement_queue_using_stacks;

public class ImplementQueueUsingStacksMain {
    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Pop: " + queue.pop());
        System.out.println("Is empty: " + queue.empty());
    }
}




