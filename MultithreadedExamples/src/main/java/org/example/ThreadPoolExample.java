package org.example;

import java.util.concurrent.ExecutorService;


public class ThreadPoolExample {
  public static void main(String[] args) {
    // fixed size thread pool -> 20
    // cached thread pool -> Flexible thread pool -> 10L 100L 60
    // single threaded thread pool  -> Cron jobs / background operations -> DB Cleanup
    // scheduled exector thread pool -> Runs on a schedule
    ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(10);
    for (int i=0;i<10000;i++) {
      int task = i;
      executorService.submit(() -> {
        System.out.println("Task " + task + " is being executed by " + Thread.currentThread().getName());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      });
    }
  }
}
