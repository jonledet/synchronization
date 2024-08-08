package com.jonledet.readwrite;

public class Readers extends ReadersWriters implements Runnable{
    public int id;
    public int read;

    public Readers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        
    }
}
