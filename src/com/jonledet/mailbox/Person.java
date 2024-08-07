package com.jonledet.mailbox;

import com.jonledet.utils.Tools;
import java.util.concurrent.Semaphore;

public class Person implements Runnable{

    public Mailbox mailbox;
    public int id;
    public static int numPeople;
    public static int capacity;
    public static int msgs;
    public static int msgsSent = 0;
    public static int msgsRead = 0;
    public static Semaphore msgMutex = new Semaphore(1, true);
    public static Semaphore readMutex = new Semaphore(1, true);
    public static String[] messages = {"hiya", "hello", "sup dude", "whats good", "hows it going"};

    public Person(Mailbox mailbox, int id) {
        this.mailbox = mailbox;
        this.id = id;
    }

    public int randIntAvoid(int origin, int bound, int avoid) {
        int rand = Tools.random.nextInt(origin, bound);
        if (rand == avoid) {
            return randIntAvoid(origin, bound, avoid);
        }
        return rand;
    }

    public void read() {
        mailbox.fullSpaces.acquireUninterruptibly();
        mailbox.mutex.acquireUninterruptibly();
        if (!mailbox.box.isEmpty()) {
            System.out.printf("Person %d reads %s from their mailbox.", id, mailbox.box.get(0));
            mailbox.box.remove(0);
        } else {
            System.out.printf("Person %d's mailbox is empty.\n", id);
        }
        mailbox.mutex.release();
        mailbox.emptySpaces.release();
    }

    public void send() {
        //compose msg
        mailbox.emptySpaces.acquireUninterruptibly();
        mailbox.mutex.acquireUninterruptibly();
        //send msg
        mailbox.mutex.release();
        mailbox.fullSpaces.release();
    }

    @Override
    public void run() {
        while(true) {
            System.out.printf("Person %d entered the post office", id);
            readMutex.acquireUninterruptibly();
            if (msgs <= msgsRead) {
                readMutex.release();
                break;
            }
            readMutex.release();
        }
    }
}
