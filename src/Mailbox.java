import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Mailbox {

    public ArrayList<String> box = new ArrayList<String>();
    public Semaphore mutex = new Semaphore(1,true);
    public Semaphore fullSpaces = new Semaphore(0, true);
    public Semaphore emptySpaces = new Semaphore(Person.capacity, true);
}
