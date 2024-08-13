package com.jonledet;

import com.jonledet.mailbox.Mailbox;
import com.jonledet.mailbox.Person;
import com.jonledet.mailbox.PostOffice;
import com.jonledet.philosophers.Chopstick;
import com.jonledet.philosophers.Philosopher;
import com.jonledet.readwrite.Readers;
import com.jonledet.readwrite.ReadersWriters;
import com.jonledet.readwrite.Writers;
import com.jonledet.utils.Tools;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-A")) {
            printError();
        }
        else {
            switch (args[1]) {
                case "1" -> diningPhilosophers();
                case "2" -> postOffice();
                case "3" -> readersWriters();
                default -> printError();
            }
        }
    }

    private static void printError() {
        System.err.println("-A 1 - Dining Philosophers\n-A 2 - Post Office\n-A 3 - Readers Writers\n");
    }

    private static int getInput(int origin, int bound){
        while (!Tools.input.hasNextInt()){
            Tools.input.next();
        }
        int userInput = Tools.input.nextInt();
        if (userInput > origin && userInput < bound){
            return userInput;
        }
        return getInput(origin, bound);
    }

    private static void diningPhilosophers() {
        System.out.println("Starting Dining Philosophers");
        System.out.println("How many philosophers? (Between 1 & 10000)");
        Philosopher.philosophers = getInput(1, 10000);
        System.out.println("How many meals? (Between 1 & 10000)");
        Philosopher.meals = getInput(1, 10000);
        Tools.input.close();
        Chopstick[] chopsticks = new Chopstick[Philosopher.philosophers];
        for (int i = 0; i < Philosopher.philosophers; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < Philosopher.philosophers; i++) {
            Thread t = new Thread(new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % Philosopher.philosophers]));
            t.start();
        }
    }

    private static void postOffice() {
        System.out.println("Starting Post Office");
        System.out.println("How many people? (Between 1 & 10000)");
        int personCount = getInput(1, 10000);
        PostOffice.people = new Person[personCount];
        System.out.println("Mailbox capacity? (Between 1 & 10000)");
        int capacity = getInput(1, 10000);
        System.out.println("How many messages? (Between 1 & 10000)");
        Person.totalMessages = getInput(1, 10000);
        Tools.input.close();
        Mailbox[] mailboxes = new Mailbox[personCount];
        Person[] people = new Person[personCount];
        for (int i = 0; i < personCount; i++) {
            mailboxes[i] = new Mailbox(capacity);
            people[i] = new Person(i, mailboxes[i]);
            PostOffice.people[i] = people[i];
        }
        for (int i = 0; i < personCount; i++) {
            Thread t = new Thread(people[i]);
            t.start();
        }
    }

    private static void readersWriters() {
        System.out.println("Starting Readers-Writers");
        System.out.println("How many readers? (Between 0 & 10000)");
        int readerCount = getInput(0, 10000);
        ReadersWriters.readerCount = readerCount;
        System.out.println("How many writers? (Between 0 & 10000)");
        int writerCount = getInput(0, 10000);
        ReadersWriters.writerCount = writerCount;
        System.out.println("How many readers at once? (Between 0 & 10000)");
        int maxReaders = getInput(0, 10000);
        ReadersWriters.maxReaders = maxReaders;
        ReadersWriters.readerControl = new Semaphore(maxReaders, true);
        Tools.input.close();
        for (int i = 0; i < readerCount; i++) {
            Thread t = new Thread(new Readers(i));
            t.start();
        }
        for (int i = 0; i < writerCount; i++) {
            Thread t = new Thread(new Writers(i));
            t.start();
        }
    }
}