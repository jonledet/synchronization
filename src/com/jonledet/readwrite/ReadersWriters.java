package com.jonledet.readwrite;

import java.util.concurrent.Semaphore;

public class ReadersWriters {
    public static int maxReaders;
    public static int readerCount;
    public static int writerCount;
    public static Semaphore area = new Semaphore(1, true);
    public static Semaphore writerControl = new Semaphore(0, true);
    public static Semaphore readerControl;
}
