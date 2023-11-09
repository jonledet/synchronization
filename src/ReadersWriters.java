import java.util.concurrent.Semaphore;

public class ReadersWriters {
    //shared class for static variables
    public static int maxreaders;
    public static int r;
    public static int w;
    public static int readcount = 0;
    public static int totalwritecount = 0;
    public static boolean done = false;
    public static Semaphore rmutex = new Semaphore(1, true);
    public static Semaphore wmutex = new Semaphore(1, true);
    public static Semaphore area = new Semaphore(1, true);
    public static Semaphore rcontrol = new Semaphore(0, true);
    public static Semaphore wcontrol = new Semaphore(0, true);
    public static int[] buffer = new int[5];

}
