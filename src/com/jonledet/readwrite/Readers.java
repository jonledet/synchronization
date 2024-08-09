package com.jonledet.readwrite;

import com.jonledet.utils.Colors;
import java.util.concurrent.Semaphore;

public class Readers extends ReadersWriters implements Runnable{
    public int id;
    public static int maxReaders;
    public static int readCount = 0;
    public static int totalReaders = 0;
    public static Semaphore mutex = new Semaphore(1, true);

    public Readers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        readerControl.acquireUninterruptibly();

        mutex.acquireUninterruptibly();
        readCount++;
        totalReaders++;
        if (readCount == 1) {
            area.acquireUninterruptibly();
        }
        mutex.release();

        System.out.printf("-%sR%d started reading.%s\n", Colors.BLUE, id, Colors.RESET);

        //simulate read latency
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("--%sR%d finished reading.%s\n", Colors.RED, id, Colors.RESET);

        mutex.acquireUninterruptibly();
        readCount--;
        if (readCount == 0) {
            area.release();
            writerControl.release();
        }
        if (totalReaders == readerCount) {
            writerControl.release(writerCount);
        }
        mutex.release();
    }
}
