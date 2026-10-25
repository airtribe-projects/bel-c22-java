package org.example;

import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockExample {
  static ReentrantLock lock = new ReentrantLock();
  static int count = 0;

  // T1 -> acquire a lock  -> 1 -> Tries to acquire it again -> it wont fail
  // T2 -> blocked
  static void increment() {
    try {
      lock.lock();
      count++;
      lockAgain();
    } finally {
      lock.unlock();
    }
  }

  static void lockAgain() {
    lock.lock();
    try {
      if (count==1){
        System.out.println("Hold count inside log(): " + lock.getHoldCount());
      }
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 100_000; i++) {
        increment();
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 100_000; i++) {
        increment();
      }
    });

    thread1.start();
    thread2.start();

    System.out.println("Expected: 200000, Actual: " + count);

  }
}
