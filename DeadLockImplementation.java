package org.example.dayThree;

class Resource{
    private String name;

    Resource(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
public class DeadLockImplementation {
    public static void main(String[] args) {
        Resource resource1 = new Resource("resource 1");
        Resource resource2 = new Resource("resource 2");

        Thread thread1 = new Thread(()->{
            synchronized (resource1){
                System.out.println("Thread locked is :: " + resource1.getName());
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (resource2){
                    System.out.println("Thread trying to being locked is :: " + resource2.getName());
                }
            }
        });


        Thread thread2 = new Thread(()->{

            synchronized (resource2){
                System.out.println("Thread locked is :: " + resource2.getName());

                try{
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (resource1){
                    System.out.println("Thread being trying to be locked :: " + resource1.getName());
                }
            }

        });

        thread1.start();
        thread2.start();
    }
}
