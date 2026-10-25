package org.example;

import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReadWriteLockExample {
  static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  static int value = 0;


  static void read() {
    lock.readLock().lock();
    try {
      System.out.println(Thread.currentThread().getName() + " reading value " + value);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.readLock().unlock();
    }

  }

  static void write(int newValue) {
    lock.writeLock().lock();
    try {
      value = newValue;
      System.out.println(Thread.currentThread().getName() + " writing " + newValue);
    } finally {
      lock.writeLock().unlock();
    }

  }

  public static void main(String[] args) throws InterruptedException {
    for (int i = 1;i<=3;i++ ){
      new Thread(() -> {
        read();
      }, "Reader-" + i).start();
    }

    Thread.sleep(1000);
    Thread writer = new Thread(() -> write(42), "Writer");
    writer.start();
  }
}
