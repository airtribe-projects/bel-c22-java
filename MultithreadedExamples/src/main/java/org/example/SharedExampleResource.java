package org.example;

public class SharedExampleResource {

  private Object lock = new Object();

  // serializing ->? one after the other
  public void useResource() {
    System.out.println("Using shared resource by thread: " + Thread.currentThread().getName());

    // writing something to the file here
    // corruption =

    // data write / update

    synchronized (lock) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

class BlockedStateExample {
  public static void main(String[] args) throws InterruptedException {
    SharedExampleResource sharedExampleResource = new SharedExampleResource();

    Thread thread1 = new Thread(() -> {
      sharedExampleResource.useResource();
    });

    Thread thread2 = new Thread(() -> {
      sharedExampleResource.useResource();
    });

    System.out.println("State of thread1 before starting: " + thread1.getState()); // NEW
    System.out.println("State of thread2 before starting: " + thread2.getState()); //NEW

    thread1.start();
    thread2.start();

    Thread.sleep(50);
    System.out.println("State of thread1 after starting: " + thread1.getState()); // RUNNABLE RUNNING TIMED_WAITING
    System.out.println("State of thread2 after starting: " + thread2.getState()); // RUNNABLE RUNNING TIMED_WAITING
    // Threads share memory
  }
}
