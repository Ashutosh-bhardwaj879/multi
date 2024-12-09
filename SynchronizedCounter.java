package org.example.dayTwo;

class Counter{
    int counter = 0;

    public synchronized void incrementCounter(){
        counter++;
    }
    public synchronized void decrementCounter(){
        counter--;
    }
    public synchronized int getCounter(){
        return counter;
    }

    public void setCounter(int i){
        counter = i;
    }

}
public class SynchronizedCounter {
    public static void main(String[] args) {
        Counter c = new Counter();


        Thread incrementThread =  new Thread(()->{
            for(int i=0;i<1000;i++){
                c.incrementCounter();
            }
        });

        Thread decrementThread = new Thread(()->{
            for(int i=0;i<1000;i++){
                c.decrementCounter();
            }
        });

        incrementThread.start();
        decrementThread.start();

        try{
            incrementThread.join();
            decrementThread.join();
        }catch (InterruptedException e){
            e.getLocalizedMessage();
        }

        System.out.println(c.getCounter());
    }
}
