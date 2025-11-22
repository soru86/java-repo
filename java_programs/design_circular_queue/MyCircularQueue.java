package design_circular_queue;

public class MyCircularQueue {
    private final int[] data;
    private int head = -1;
    private int tail = -1;

    public MyCircularQueue(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        data = new int[k];
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        if (isEmpty()) {
            head = 0;
        }
        tail = (tail + 1) % data.length;
        data[tail] = value;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        if (head == tail) {
            head = -1;
            tail = -1;
        } else {
            head = (head + 1) % data.length;
        }
        return true;
    }

    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return data[head];
    }

    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return data[tail];
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public boolean isFull() {
        return !isEmpty() && (tail + 1) % data.length == head;
    }
}







