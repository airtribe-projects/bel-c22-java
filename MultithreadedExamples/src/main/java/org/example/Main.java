package org.example;

public class Main {
  public static void main(String[] args) {
    // Main thread
    // child thread -> example thread
    // non deamon thread
//    ExampleThread thread = new ExampleThread();
//    thread.setDaemon(true);
//    thread.start();

    RunnableThreadExample runnableThreadExample = new RunnableThreadExample();
    Thread thread = new Thread(runnableThreadExample);
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.start();

    Thread thread2 = new Thread(() -> {
      for (int i=0;i<5000;i++) {
        System.out.println("Running something on thread 2 " + Thread.currentThread().getState());
      }
    });

    thread2.run();

    for (int i=0;i<1000;i++) {
      System.out.println("Running the loop on " + Thread.currentThread().getName());
    }
  }
}

//Non Deamon thread -> BY default every child thread is a non deamon thread
// parent will wait for the child to complete
// deamon thread -> Parent wont wait for child to complete

// Graceful executon -> Slow stop

// Depends on underlying CPU cores
// concurrency or parallely
// Tomcat server threads
// Reactor thread pool
// Application level threads