package org.example.dayThree;

class Resources{
    private String name;

    Resources(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
public class DeadLockSolvingWay1 {
    public static void main(String[] args) {
        Resources r1 = new Resources("Resource 1");
        Resources r2 = new Resources("Resource 2");

        Thread t1 = new Thread(()->{
            synchronized (r1){
                System.out.println("Thread 1 locked :: " + r1.getName());
                synchronized (r2){
                    System.out.println("Thread 1 locked :: " + r2.getName());
                }
            }
        });

        Thread t2 = new Thread(()->{
            synchronized (r1){
                System.out.println("Thread 2 locked :: " + r1.getName());
                synchronized (r2){
                    System.out.println("Thread 2 locked :: " + r2.getName());
                }
            }
        });

        t1.start();
        t2.start();
    }
}
