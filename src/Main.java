import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Random random = new Random();
    public static Scanner input = new Scanner(System.in);
    //ASCII colors to clarify output.
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
                    case "1" -> diningPhilosophers();
                    case "2" -> postOffice();
                    case "3" -> readersWriters();
                    default -> printErr();
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

    private static int getInput(int origin, int bound){
        while (!input.hasNextInt()){
            input.next();
        }

        int userInput = input.nextInt();
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
        input.close();
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
        input.close();
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
        input.close();
        ReadersWriters.rControl.release(ReadersWriters.maxReaders);
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