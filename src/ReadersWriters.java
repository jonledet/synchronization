import java.util.concurrent.Semaphore;

public class ReadersWriters {
    //shared class for static variables
    public static int maxReaders;
    public static int readerCount;
    public static int writerCount;
    public static int readCount = 0;
    public static int totalWriteCount = 0;
    public static boolean done = false;
    public static Semaphore rMutex = new Semaphore(1, true);
    public static Semaphore wMutex = new Semaphore(1, true);
    public static Semaphore area = new Semaphore(1, true);
    public static Semaphore rControl = new Semaphore(0, true);
    public static Semaphore wControl = new Semaphore(0, true);
    public static int[] buffer = new int[5];

}
