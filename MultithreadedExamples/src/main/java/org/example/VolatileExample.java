package org.example;

public class VolatileExample {
  static boolean running = true;

  public static void main(String[] args) throws InterruptedException {
    Thread worker = new Thread(() -> {
      while(running) {

      }
      System.out.println("Worker: saw running = false, stopping");
    });
    worker.start();

    Thread.sleep(1000);
    System.out.println("Main: setting running = false");
    running = false;

    System.out.println("Main: waiting for worker to finish");
  }
}
