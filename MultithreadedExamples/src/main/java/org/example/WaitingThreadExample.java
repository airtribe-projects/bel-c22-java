package org.example;

public class WaitingThreadExample {
  public static void main(String[] args) throws InterruptedException {
    Thread longerRunningThread = new Thread(() -> {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Thread shorterRunningThread = new Thread(() -> {
      try {
        Thread.sleep(5000);
        longerRunningThread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Thread monitorThread = new Thread(() -> {
      while(shorterRunningThread.isAlive()) {
        System.out.println("State of the shorter running thread before starting " + shorterRunningThread.getState()); // TIMED_WAITING WAITING
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    System.out.println("State of the longer running thread before starting " + longerRunningThread.getState()); // NEW
    System.out.println("State of the shorter running thread before starting " + shorterRunningThread.getState()); // NEW
    System.out.println("State of the main thread " + Thread.currentThread().getName()); // RUNNING

    monitorThread.start();
    longerRunningThread.start();
    shorterRunningThread.start();


    System.out.println("State of the longer running thread after starting " + longerRunningThread.getState()); //  RUNNABLE RUNNING TIMED_WAITING
    System.out.println("State of the shorter running thread after starting " + shorterRunningThread.getState()); //  RUNNABLE RUNNING TIMED_WAITING WAITING
  }
}

// Blocked
// Race conditions
// Solve for race conditions
    // Synchronization
   // Locks -> Object or ReentrantLock
   // Atomic variables
   // Concurrent Data structure
   // Semaphores and mutexes
   // Volatile
   // ThreadLocal
   // Deadlocks
// ThreadPool
// CompletableFuture
// JVM Heap structure
// JVisual VM -> Profiliong, Sampling, Heap Configurations