package com.jonledet.mailbox;

import java.util.Stack;
import java.util.concurrent.Semaphore;

public class Mailbox extends PostOffice{
    public Stack<Messages> mailbox = new Stack<>();
    public Semaphore full = new Semaphore(0, true);
    public Semaphore mutex = new Semaphore(1, true);
    public Semaphore empty;

    public Mailbox(int capacity) {
        this.empty = new Semaphore(capacity, true);
    }
}
