package com.jonledet.readwrite;

import com.jonledet.utils.Colors;

public class Writers extends ReadersWriters implements Runnable{
    public int id;
    public static int totalWriters = 0;

    public Writers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        writerControl.acquireUninterruptibly();

        totalWriters++;

        area.acquireUninterruptibly();

        System.out.printf("---%sW%d started writing.%s\n", Colors.GREEN, id, Colors.RESET);

        //simulate write latency
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("----%sW%d finished writing.%s\n", Colors.PURPLE, id, Colors.RESET);

        area.release();

        if (totalWriters == writerCount) {
            readerControl.release(readerCount);
        }

        readerControl.release(maxReaders);
    }
}
