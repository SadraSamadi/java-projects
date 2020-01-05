package datastructer;

public class CircularQueue {

    public static final int DEFAULT_CAPACITY = 1000;

    private Object[] array;

    private int capacity;

    private int rear;

    private int front;

    private int size;

    public CircularQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    public void enqueue(Object data) {
        if (size == capacity)
            throw new RuntimeException("Queue is full !");
        array[rear] = data;
        rear = (rear + 1) % capacity;
        size++;
    }

    public Object dequeue() {
        if (size == 0)
            throw new RuntimeException("Queue is empty !");
        Object temp = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size--;
        return temp;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object frontElement() {
        if (size == 0)
            throw new RuntimeException("Queue is empty !");
        return array[front];
    }

}