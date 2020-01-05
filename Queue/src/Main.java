import datastructer.CircularQueue;

import java.util.Random;

public class Main {

    private Main() {
        CircularQueue circularQueue = new CircularQueue(4);
        System.out.println(circularQueue.dequeue());
        Random random = new Random();
        for (int i = 0; i < 4; i++)
            circularQueue.enqueue(random.nextInt());
        circularQueue.enqueue(random.nextInt());
    }

    public static void main(String[] args) {
        new Main();
    }

}
