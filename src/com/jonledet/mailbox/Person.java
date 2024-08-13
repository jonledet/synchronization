package com.jonledet.mailbox;

import com.jonledet.utils.*;

public class Person extends PostOffice implements Runnable{
    public int id;
    public Mailbox mailbox;
    public static int totalMessages;

    public Person(int id, Mailbox mailbox) {
        this.id = id;
        this.mailbox = mailbox;
    }

    public void read() {
        mailbox.full.acquireUninterruptibly();
        mailbox.mutex.acquireUninterruptibly();
        System.out.printf("Person %d read: %s from Person %d.\n",id, mailbox.mailbox.pop(), mailbox.recipient.pop());
        mailbox.mutex.release();
        mailbox.empty.release();
    }

    @Override
    public void run() {
        while (true) {
            //break conditions
            System.out.printf("Person %d entered the post office.\n", id);
            if (mailbox.mailbox.empty()){
                System.out.printf("No mail in Person %d's mailbox.\n", id);
            } else {
                while (!mailbox.mailbox.empty()) {
                    read();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }
}
