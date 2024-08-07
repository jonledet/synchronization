package com.jonledet.philosophers;

import com.jonledet.utils.*;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    public int id;
    public Chopstick left;
    public Chopstick right;

    //dining philosopher variables
    public static int philosophers;
    public static int meals;
    public static int mealsEaten = 0;
    public static Semaphore count = new Semaphore(1, true);
    public static Semaphore mutex = new Semaphore(1, true);
    public static Semaphore start = new Semaphore(0, true);
    public static Semaphore end = new Semaphore(0, true);
    public static int startCount = 0;
    public static int endCount = 0;

    public Philosopher(int id, Chopstick left, Chopstick right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    //all philosophers get stuck in semaphore queue until the last one arrives
    public void startBarrier() {
        count.acquireUninterruptibly();
        startCount++;
        System.out.printf("%sPhilosopher %d is here.%s\n", Colors.CYAN, id, Colors.RESET);
        if (startCount == philosophers){
            start.release(philosophers);
            System.out.printf("-%sAll philosophers are here.%s\n", Colors.CYAN, Colors.RESET);
        }
        count.release();
        start.acquireUninterruptibly();
    }

    //all philosophers get stuck in semaphore queue until the last one arrives
    public void endBarrier() {
        count.acquireUninterruptibly();
        endCount++;

        if (endCount == philosophers){
            end.release();
            System.out.printf("------%sAll philosophers are ready to leave.%s\n", Colors.PURPLE, Colors.RESET);
        }
        count.release();
        end.acquireUninterruptibly();
        System.out.printf("-------%sPhilosopher %d left.%s\n", Colors.PURPLE, id, Colors.RESET);
        end.release();
    }

    //eating and thinking, threads yield for 3-6 cycles
    public void eat() {
        System.out.printf("---%sPhilosopher %d is eating.%s\n", Colors.BLUE, id, Colors.RESET);
        for (int i = 0; i < Tools.random.nextInt(3,7); i++) {
            Thread.yield();
        }
    }

    public void think() {
        System.out.printf("-----%sPhilosopher %d is thinking.%s\n", Colors.BLUE, id, Colors.RESET);
        for (int i = 0; i < Tools.random.nextInt(3,7); i++) {
            Thread.yield();
        }
    }

    @Override
    public void run() {
        startBarrier();
        while(true) {
            //break conditions
            mutex.acquireUninterruptibly();
            if(meals <= mealsEaten) {
                mutex.release();
                break;
            }
            mealsEaten++;
            mutex.release();
            //deadlock avoidance
            //last philosopher grabs right first
            if ((id + 1) == philosophers){
                right.grab(id, "right");
                left.grab(id, "left");
            }
            else {
                left.grab(id, "left");
                right.grab(id, "right");
            }
            eat();
            left.drop(id, "left");
            right.drop(id, "right");
            think();
        }
        endBarrier();
    }
}
