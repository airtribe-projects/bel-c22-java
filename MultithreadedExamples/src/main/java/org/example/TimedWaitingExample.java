package org.example;

public class TimedWaitingExample {
  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("State of the thread1 after sleeping " + Thread.currentThread().getState()); // RUNNABLE -> RUNNING
    });

    System.out.println("State of the main thread before starting the thread1 " + Thread.currentThread().getState()); // RUNNABLE
    System.out.println("State of the thread1 " + thread1.getState()); // NEW

    thread1.start();;

//    Thread.sleep(70); // main
    System.out.println("State of the thread1 after starting " + thread1.getState()); // RUNNABLE RUNNING TIMED_WAITING
    System.out.println("State of the main thread after starting the thread1 " + Thread.currentThread().getState()); // RUNNING
  }
}
