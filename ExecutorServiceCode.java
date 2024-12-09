package org.example.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadPoolExample {
    public static void main(String[] args) {
        // Create a fixed thread pool with 5 threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            // Submit 10 different tasks to the thread pool
            for (int i = 1; i <= 10; i++) {
                int taskId = i; // Task ID for clarity
                executorService.submit(() -> performTask(taskId));
            }
        } finally {
            // Shut down the executor service gracefully
            executorService.shutdown();
        }
    }

    // Method to
    // perform the task
    private static void performTask(int taskId) {
        System.out.println("Task " + taskId + " is being executed by " +
                Thread.currentThread().getName());
        // Simulating different operations for each task
        switch (taskId) {
            case 1 -> System.out.println("Task 1: Printing 1");
            case 2 -> System.out.println("Task 2: Printing 2");
            case 3 -> System.out.println("Task 3: Printing 3");
            case 4 -> System.out.println("Task 4: Printing 4");
            case 5 -> System.out.println("Task 5: Printing 5");
            case 6 -> System.out.println("Task 6: Printing 6");
            case 7 -> System.out.println("Task 7: Printing 7");
            case 8 -> System.out.println("Task 8: Printing 8");
            case 9 -> System.out.println("Task 9: Printing 9");
            case 10 -> System.out.println("Task 10: Printing 10");
            default -> System.out.println("Unknown Task");
        }
    }
}
