package org.example.nonblockingatomic;

import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingCounter {
    private  final AtomicInteger counter = new AtomicInteger(0);

    public void increment(){
        int current;
        int updated;
        do{
            current = counter.get();
            updated = current+1;
        }while(!counter.compareAndSet(current,updated));
    }

    public int getValue(){
        return counter.get();
    }

    public static void main(String[] args) {
        NonBlockingCounter nonBlockingCounter = new NonBlockingCounter();

        Runnable task = ()->{
            for(int i =0;i<1000;i++){
                nonBlockingCounter.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();

        try{
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final Counter Example :: " + nonBlockingCounter.getValue());
    }

}
