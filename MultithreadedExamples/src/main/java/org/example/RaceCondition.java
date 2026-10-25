package org.example;

public class RaceCondition {
  public static void main(String[] args) {
    IncrementCounter counter = new IncrementCounter(0);
    for (int i=0;i<10000;i++) {
      Thread thread = new Thread(() -> {
        counter.increment();
        System.out.println("Count: " + counter.getCount() + " Thread: " + Thread.currentThread().getName());
      });
      thread.start();
    }
  }
}
