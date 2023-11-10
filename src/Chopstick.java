import java.util.concurrent.Semaphore;

public class Chopstick {
    Semaphore chopstick;

    public Chopstick() {
        chopstick = new Semaphore(1, true);
    }

    public void grab(int id, String direction) {
        //use acquireUninterruptibly since no interrupts are called
        chopstick.acquireUninterruptibly();
        System.out.printf("--%sPhilosopher %d grabbed their %s chopstick.%s\n", Main.GREEN, id, direction, Main.RESET);
    }

    public void drop(int id, String direction) {
        chopstick.release();
        System.out.printf("----%sPhilosopher %d dropped their %s chopstick.%s\n", Main.RED, id, direction, Main.RESET);
    }
}
