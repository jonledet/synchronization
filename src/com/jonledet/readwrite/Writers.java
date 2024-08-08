package com.jonledet.readwrite;

public class Writers extends ReadersWriters implements Runnable{
    public int id;

    public Writers(int id) {
        this.id = id;
    }

    @Override
    public void run() {
    }
}
