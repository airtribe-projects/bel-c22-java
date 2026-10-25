package org.example;

import static java.lang.Thread.*;


public class DeadlockExample {
  private static Object lock1 = new Object();
  private static Object lock2 = new Object();

  public static void main(String[] args) {
    Thread thread1  = new Thread(() -> {
      synchronized (lock1) {
        System.out.println("Thread 1: Holding lock 1...");
        try {
          sleep(100);
          synchronized (lock2) {
            System.out.println("Thread 1: Holding lock 1 & 2...");
          }
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    Thread thread2 = new Thread(() -> {
      synchronized (lock1) {
        System.out.println("Thread 2: Holding lock 1...");
        try {
          sleep(100);
          synchronized (lock2) {
            System.out.println("Thread 2: Holding lock 1 & 2...");
          }
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    thread1.start();
    thread2.start();
  }

}
