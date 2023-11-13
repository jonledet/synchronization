import java.util.Stack;
import java.util.concurrent.Semaphore;

public class Mailbox {
    public Stack<String> box = new Stack<>();
    public Semaphore mutex = new Semaphore(1,true);
    public Semaphore fullSpaces = new Semaphore(0, true);
    public Semaphore emptySpaces = new Semaphore(Person.capacity, true);
}
