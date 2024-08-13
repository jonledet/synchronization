package com.jonledet.mailbox;

import com.jonledet.utils.*;
import java.util.concurrent.Semaphore;

public class Person extends PostOffice implements Runnable{
    public int id;
    public Mailbox mailbox;
    public static Semaphore mutex = new Semaphore(1, true);
    public static int sentMessages = 0;
    public static int totalMessages;

    public Person(int id, Mailbox mailbox) {
        this.id = id;
        this.mailbox = mailbox;
    }

    public void consume() {
        mailbox.full.acquireUninterruptibly();
        mailbox.mutex.acquireUninterruptibly();
        Messages message = mailbox.mailbox.pop();
        System.out.printf("--%sPerson %d read: '%s' from Person %d.%s\n", Colors.GREEN, id, message.message, message.recipient, Colors.RESET);
        mailbox.mutex.release();
        mailbox.empty.release();
    }

    public void produce() {
        String randomString = RandomString.getRandomString();
        int randomRecipient = Tools.nextIntAvoid(0, PostOffice.people.length, id);
        System.out.printf("---%sPerson %d is composing a message to Person %d.%s\n", Colors.CYAN, id, randomRecipient, Colors.RESET);
        people[randomRecipient].mailbox.empty.acquireUninterruptibly();
        people[randomRecipient].mailbox.mutex.acquireUninterruptibly();
        System.out.printf("----%sPerson %d is sending: '%s' to Person %d.%s\n", Colors.PURPLE, id, randomString, randomRecipient, Colors.RESET);
        people[randomRecipient].mailbox.mailbox.push(new Messages(randomString, randomRecipient));
        people[randomRecipient].mailbox.mutex.release();
        people[randomRecipient].mailbox.full.release();
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("-%sPerson %d entered the post office.%s\n", Colors.YELLOW, id, Colors.RESET);
            if (mailbox.mailbox.empty()){
                System.out.printf("--%sNo mail in Person %d's mailbox.%s\n", Colors.RED, id, Colors.RESET);
            }
            while (!mailbox.mailbox.empty()) {
                //read messages
                consume();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //break conditions
            mutex.acquireUninterruptibly();
            if (sentMessages == totalMessages) {
                mutex.release();
                break;
            }
            sentMessages++;
            mutex.release();

            //send messages
            produce();

            System.out.printf("-----%sPerson %d is leaving the post office.%s\n", Colors.YELLOW, id, Colors.RESET);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
