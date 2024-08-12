package com.jonledet.mailbox;

public class Person implements Runnable{
    public Mailbox mailbox;
    public static int totalMessages;

    public Person(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    @Override
    public void run() {
        
    }
}
