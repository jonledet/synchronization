package com.jonledet.mailbox;

public class Person implements Runnable{
    public int id;
    public Mailbox mailbox;
    public static int totalMessages;

    public Person(int id, Mailbox mailbox) {
        this.id = id;
        this.mailbox = mailbox;
    }

    @Override
    public void run() {
        
    }
}
