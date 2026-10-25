package org.example;

import java.util.concurrent.atomic.AtomicInteger;


public class IncrementCounter {
  private AtomicInteger count;

  public IncrementCounter(int initialCount) {
    this.count = new AtomicInteger(initialCount);
  }

  // 1 -> 1
  // 2 -> 1
  // 3 -> 1

  // read and increment -> Atomically
  public void increment() {
    System.out.println("Current value of the counter " + Thread.currentThread().getName() + " read count " + count.get());
    this.count.incrementAndGet();
    System.out.println("Current value of the counter " + Thread.currentThread().getName() + " incremented count " + count.get());
  }

  public int getCount() {
    return count.get();
  }
}
