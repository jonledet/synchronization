import java.util.concurrent.Semaphore;

public class Person implements Runnable{

    public Mailbox mailbox;
    public int id;
    public static int numpeople;
    public static int capacity;
    public static int msgs;
    public static int msgssent = 0;
    public static int msgsread = 0;
    public static Semaphore msgmutex = new Semaphore(1, true);
    public static Semaphore readmutex = new Semaphore(1, true);
    public static String[] messages = {"hiya", "hello", "sup dude", "whats good", "hows it going"};

    public Person(Mailbox mailbox, int id) {
        this.mailbox = mailbox;
        this.id = id;
    }

    public int randintavoid(int origin, int bound, int avoid) {
        int rand = Main.random.nextInt(origin, bound);
        if (rand == avoid) {
            return randintavoid(origin, bound, avoid);
        }
        return rand;
    }

    public void read() {
        mailbox.fullspaces.acquireUninterruptibly();
        mailbox.mutex.acquireUninterruptibly();
        if (!mailbox.box.isEmpty()) {
            System.out.printf("Person %d reads %s from their mailbox.", id, mailbox.box.get(0));
            mailbox.box.remove(0);
        } else {
            System.out.printf("Person %d's mailbox is empty.\n", id);
        }
        mailbox.mutex.release();
        mailbox.emptyspaces.release();
    }

    public void send() {
        //compose msg
        mailbox.emptyspaces.acquireUninterruptibly();
        mailbox.mutex.acquireUninterruptibly();
        //send msg
        mailbox.mutex.release();
        mailbox.fullspaces.release();
    }

    @Override
    public void run() {
        while(true) {
            System.out.printf("Person %d entered the post office", id);
            readmutex.acquireUninterruptibly();
            if (msgs <= msgsread) {
                readmutex.release();
                break;
            }
            readmutex.release();
        }
    }
}
