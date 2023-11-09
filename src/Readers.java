public class Readers extends ReadersWriters implements Runnable{

    public int id;
    public int read;

    public Readers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //all readers start in rcontrol semaphore queue
        rcontrol.acquireUninterruptibly();

        if (done) {
            wcontrol.release(w);
            System.exit(0);
        }
        rmutex.acquireUninterruptibly();
        readcount++;
        if (readcount == 1) {
            area.acquireUninterruptibly();
        }
        rmutex.release();
        System.out.printf("Reader %d began reading.\n", id);
        for (int i = 0; i < buffer.length; i++) {
            read = buffer[i];
            Thread.yield();
        }
        System.out.printf("-Reader %d finished reading.\n", id);
        rmutex.acquireUninterruptibly();
        readcount--;
        if (readcount == 0) {
            area.release();
            wcontrol.release();
        }
        rmutex.release();
    }
}
