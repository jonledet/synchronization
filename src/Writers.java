import java.util.concurrent.ThreadLocalRandom;

public class Writers extends ReadersWriters implements Runnable{
    public int id;

    public Writers(int id) {
        this.id = id;
    }

    public void write(){
        area.acquireUninterruptibly();

        simWriteLatency();
        System.out.printf("----W%d finished writing.\n", id);

        area.release();
    }

    //simulate writing latency
    public void simWriteLatency(){
        System.out.printf("---W%d started writing.\n", id);
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

        wMutex.acquireUninterruptibly();
        totalWriteCount++;
        if(totalWriteCount == writerCount){
            done = true;
            wMutex.release();
            System.exit(0);
        }

        //release maxReaders after writer is finished
        readControl.release(maxReaders);
    }
}
