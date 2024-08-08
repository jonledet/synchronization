package com.jonledet;

import com.jonledet.mailbox.Mailbox;
import com.jonledet.mailbox.Person;
import com.jonledet.philosophers.Chopstick;
import com.jonledet.philosophers.Philosopher;
import com.jonledet.readwrite.Readers;
import com.jonledet.readwrite.ReadersWriters;
import com.jonledet.readwrite.Writers;
import com.jonledet.utils.Tools;

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
        Person.numPeople = getInput(1, 10000);
        System.out.println("Mailbox capacity? (Between 1 & 10000)");
        Person.capacity = getInput(1, 10000);
        System.out.println("How many messages? (Between 1 & 10000)");
        Person.msgs = getInput(1, 10000);
        Tools.input.close();
        for (int i = 0; i < Person.numPeople; i++) {
            Thread t = new Thread(new Person(new Mailbox(), i));
            t.start();
        }
    }

    private static void readersWriters() {
        System.out.println("Starting Readers-Writers");
        System.out.println("How many readers? (Between 0 & 10000)");
        ReadersWriters.readerCount = getInput(0, 10000);
        System.out.println("How many writers? (Between 0 & 10000)");
        ReadersWriters.writerCount = getInput(0, 10000);
        System.out.println("How many readers at once? (Between 0 & 10000)");
        ReadersWriters.maxReaders = getInput(0, 10000);
        Tools.input.close();
        ReadersWriters.readControl.release(ReadersWriters.maxReaders);
        for (int i = 0; i < ReadersWriters.readerCount; i++) {
            Thread t = new Thread(new Readers(i));
            t.start();
        }
        for (int i = 0; i < ReadersWriters.writerCount; i++) {
            Thread t = new Thread(new Writers(i));
            t.start();
        }
    }
}