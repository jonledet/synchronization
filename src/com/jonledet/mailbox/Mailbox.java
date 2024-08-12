package com.jonledet.mailbox;

import java.util.Stack;
import java.util.concurrent.Semaphore;

public class Mailbox {
    public Stack<String> stack = new Stack<>();
    public Semaphore full = new Semaphore(0, true);
    public Semaphore empty;

    public Mailbox(int capacity) {
        this.empty = new Semaphore(capacity, true);
    }
}
