import java.util.concurrent.ThreadLocalRandom;

public class Writers extends ReadersWriters implements Runnable{
    public int id;

    public Writers(int id) {
        this.id = id;
    }

    public void write(){
        area.acquireUninterruptibly();

        //simulate writing latency
        simWriteLatency();

        area.release();
    }

    public void simWriteLatency(){
        for(int i = 0; i < buffer.length; i++){
            buffer[i] = ThreadLocalRandom.current().nextInt();
        }
    }

    @Override
    public void run() {
        //pattern is maxReaders read, 1 writer writes, maxReaders read,...
        //writers get stuck in empty semaphore queue and are released one at a time by the last reader.
        writeControl.acquireUninterruptibly();

        //basic writer algorithm
        write();

        //release maxReaders after writer is finished
        readControl.release(maxReaders);
    }
}
