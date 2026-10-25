package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ConcurrentMapExample {

  static void populateValue(Map<Integer, Integer> map, int start) {
    for (int i = start; i < start + 10_000; i++) {
      map.put(i, i);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Map<Integer, Integer> map = new ConcurrentHashMap<>();
    Thread thread1 = new Thread(() -> populateValue(map, 0));
    Thread thread2 = new Thread(() -> populateValue(map, 10_000));
    thread1.start();
    thread2.start();

    Thread.sleep(10000);
    System.out.println("Expected size: 20000");
    System.out.println("HashMap:           " + map.size());
  }
}
// 100000 tasks to be done
// ThreadPool -> ExecutorService
// Orchestrator -> Assignment of the tasks to the threads automatically