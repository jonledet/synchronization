public class Readers extends ReadersWriters implements Runnable{

    public int id;
    public int read;

    public Readers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //all readers start in rcontrol semaphore queue
        rControl.acquireUninterruptibly();

        if (done) {
            wControl.release(writerCount);
            System.exit(0);
        }
        rMutex.acquireUninterruptibly();
        readCount++;
        if (readCount == 1) {
            area.acquireUninterruptibly();
        }
        rMutex.release();
        System.out.printf("Reader %d began reading.\n", id);
        for (int item : buffer) {
            read = item;
            Thread.yield();
        }
        System.out.printf("-Reader %d finished reading.\n", id);
        rMutex.acquireUninterruptibly();
        readCount--;
        if (readCount == 0) {
            area.release();
            wControl.release();
        }
        rMutex.release();
    }
}
