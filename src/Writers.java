public class Writers extends ReadersWriters implements Runnable{

    public int id;

    public Writers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        wcontrol.acquireUninterruptibly();
        area.acquireUninterruptibly();
        System.out.printf("Writer %d started writing.\n", id);
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = Main.random.nextInt();
            Thread.yield();
        }
        System.out.printf("-Writer %d finished writing.\n", id);
        area.release();
        wmutex.acquireUninterruptibly();
        totalwritecount++;
        if (totalwritecount == w) {
            wmutex.release();
            done = true;
            wcontrol.release(w);
            rcontrol.release(r);
        }
        wmutex.release();
        rcontrol.release(maxreaders);
    }
}
