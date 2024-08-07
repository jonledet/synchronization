package com.jonledet.philosophers;

import com.jonledet.utils.Colors;
import java.util.concurrent.Semaphore;

public class Chopstick {
    Semaphore chopstick = new Semaphore(1, true);

    public void grab(int id, String direction) {
        //use acquireUninterruptibly since no interrupts are called
        chopstick.acquireUninterruptibly();
        System.out.printf("--%sPhilosopher %d grabbed their %s chopstick.%s\n", Colors.GREEN, id, direction, Colors.RESET);
    }

    public void drop(int id, String direction) {
        chopstick.release();
        System.out.printf("----%sPhilosopher %d dropped their %s chopstick.%s\n", Colors.RED, id, direction, Colors.RESET);
    }
}
