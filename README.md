# Synchronization

**Create a new branch for each issue in this format:**
```bash
{issue number}-{short issue name}
```
**then after pushing to the new branch, create a pull request with the *new-feature* branch.  No spaces in the branch names.**


This Java project is a synchronization problem solver that aims to provide solutions for three classic synchronization problems. The solutions are implemented using Java and make use of semaphores for synchronization.

## Table of Contents
- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [How to Use](#how-to-use)
- [Acknowledgments](#acknowledgments)

## Project Overview

The project focuses on solving the following synchronization problems:

1. **Dining Philosophers**
   - A classic problem where philosophers sit around a dining table and must share chopsticks without causing conflicts. Each philosopher(thread) needs 2 chopsticks(semaphores) to eat a meal.

2. **Bounded-Buffer**
   - Simulates a post office with people, mailboxes, and messages. Semaphores are used to control mailbox access when sending messages. An extension of the bounded-buffer problem.

3. **Readers-Writers**
   - Addresses the Readers-Writers problem where multiple readers can access a file simultaneously, but only one writer can write at a time.

Each of these problems is solved with proper synchronization techniques, ensuring that concurrent processes behave correctly.

## Project Structure

The project is structured as follows:

- `src/` : Contains the source code for the three synchronization problems.
- `docs/` : Documentation and additional resources.

## How to Use

1. Clone or download this repository to your local machine.

2. Navigate to Main in the `src/` directory.

3. Compile and run the Java code for the chosen problem.

```bash
javac Main.java
java Main
```

- To run Dining Philosophers, use the command line argument -A 1.
- To run Post Office Simulation, use the command line argument -A 2.
- To run Readers-Writer Problem, use the command line argument -A 3.
For example, to run Dining Philosophers, use:

```
java Main -A 1
```

Follow the on-screen prompts and instructions to interact with and observe the solution.

You can run each problem separately and observe how they handle synchronization.

## Acknowledgments
This project was completed to gain a deeper understanding of synchronization problems and their solutions. The code and implementations were developed as part of a learning process. Special thanks to the educators and resources that provided guidance and insights during the project's development.

Feel free to explore the source code and use it as a reference for your own synchronization challenges or for educational purposes.
