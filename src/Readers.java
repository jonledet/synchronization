public class Readers extends ReadersWriters implements Runnable{
    public int id;
    //variable to read into while simulating read latency
    public int read;

    public Readers(int id) {
        this.id = id;
    }

    public void read(){
        rMutex.acquireUninterruptibly();
        if(readCount == 0){
            //claim area semaphore if first reader
            area.acquireUninterruptibly();
        }
        readCount++;
        rMutex.release();

        //simulating reading latency
        simReadLatency();

        rMutex.acquireUninterruptibly();
        if(readCount == 1){
            //release area semaphore and 1 writer if last reader
            area.release();
            writeControl.release();
        }
        readCount--;
        rMutex.release();
    }

    public void simReadLatency(){
        for(int elmt : buffer){
            read = elmt;
        }
    }

    @Override
    public void run() {
        //pattern is maxReaders read, 1 writer writes, maxReaders read,...
        //readers get stuck in empty semaphore queue and maxReaders are released at a time to maintain pattern
        readControl.acquireUninterruptibly();

        //reader algorithm allowing maxReaders to read at once
        read();

    }
}
