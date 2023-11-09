import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Mailbox {

    public ArrayList<String> box = new ArrayList<>();
    public Semaphore mutex = new Semaphore(1,true);
    public Semaphore fullspaces = new Semaphore(0, true);
    public Semaphore emptyspaces = new Semaphore(Person.capacity, true);
}
