import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Random random = new Random();
    public static Scanner input = new Scanner(System.in);
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        try {
            if (args.length == 2 && args[0].equals("-A")) {
                String option = args[1];
                switch (option) {
                    case "1":
                        diningphilosophers();
                        break;
                    case "2":
                        postoffice();
                        break;
                    case "3":
                        readerswriters();
                        break;
                    default:
                        printErr();
                }
            } else {
                printErr();
            }
        } catch (Exception e) {
            printErr();
        }
    }

    private static void printErr() {
        System.err.println("-A 1 - Dining Philosophers\n-A 2 - Post Office\n-A 3 - Readers Writers\n");
    }

    private static void diningphilosophers() {
        System.out.println("Starting Dining Philosophers");
        System.out.println("How many philosophers?");
        Philosopher.philosophers = input.nextInt();
        System.out.println("How many meals?");
        Philosopher.meals = input.nextInt();
        Chopstick[] chopsticks = new Chopstick[Philosopher.philosophers];
        for (int i = 0; i < Philosopher.philosophers; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < Philosopher.philosophers; i++) {
            Thread t = new Thread(new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % Philosopher.philosophers]));
            t.start();
        }
    }

    private static void postoffice() {
        System.out.println("Starting Post Office");
        System.out.println("How many people?");
        Person.numpeople = input.nextInt();
        System.out.println("Mailbox capacity?");
        Person.capacity = input.nextInt();
        System.out.println("How many messages?");
        Person.msgs = input.nextInt();
        for (int i = 0; i < Person.numpeople; i++) {
            Thread t = new Thread(new Person(new Mailbox(), i));
            t.start();
        }
    }

    private static void readerswriters() {
        System.out.println("Starting Readers-Writers");
        System.out.println("How many readers?");
        ReadersWriters.r = input.nextInt();
        System.out.println("How many writers?");
        ReadersWriters.w = input.nextInt();
        System.out.println("How many readers at once?");
        ReadersWriters.maxreaders = input.nextInt();
        ReadersWriters.rcontrol.release(ReadersWriters.maxreaders);
        for (int i = 0; i < ReadersWriters.r; i++) {
            Thread t = new Thread(new Readers(i));
            t.start();
        }
        for (int i = 0; i < ReadersWriters.w; i++) {
            Thread t = new Thread(new Writers(i));
            t.start();
        }
    }
}