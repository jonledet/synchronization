public class Writers extends ReadersWriters implements Runnable{

    public int id;

    public Writers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        wControl.acquireUninterruptibly();
        area.acquireUninterruptibly();
        System.out.printf("Writer %d started writing.\n", id);
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = Main.random.nextInt();
            Thread.yield();
        }
        System.out.printf("-Writer %d finished writing.\n", id);
        area.release();
        wMutex.acquireUninterruptibly();
        totalWriteCount++;
        if (totalWriteCount == writerCount) {
            wMutex.release();
            done = true;
            wControl.release(writerCount);
            rControl.release(readerCount);
        }
        wMutex.release();
        rControl.release(maxReaders);
    }
}
