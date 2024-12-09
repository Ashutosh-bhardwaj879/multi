package org.example.dayThree;


import java.util.concurrent.locks.ReentrantLock;

class Resourcee{
    private final String name;
    private final ReentrantLock lock;

    Resourcee(String name){
        this.name = name;
        this.lock = new ReentrantLock();
    }

    public String getName(){
        return name;
    }
    public ReentrantLock getLock(){
        return lock;
    }

}
public class DeadLockPreventLocking {
    public static void main(String[] args) {
        Resourcee r1 = new Resourcee("Resource 1");
        Resourcee r2 = new Resourcee("Resource 2");

        Thread t1 = new Thread(()->accessResource(r1,r2,"Thread 1"));
        Thread t2 = new Thread(()->accessResource(r2,r1,"Thread 2"));

        t1.start();
        t2.start();
    }

    private static void accessResource(Resourcee r1,Resourcee r2,String threadName){
        try{
            if(r1.getLock().tryLock()){
                System.out.println(threadName + " locked " + r1.getName());
                Thread.sleep(500);

                if(r2.getLock().tryLock()){
                    try {
                        System.out.println(threadName + " locked " + r2.getName());
                        System.out.println(threadName + " is working with " +  r1.getName() + " " + r2.getName());
                    }finally {
                        r2.getLock().unlock();
                        System.out.println(threadName + " released " + r2.getName());
                    }
                }else{
                    System.out.println(threadName + " could not lock " + r2.getName());
                }

            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            if(r1.getLock().isHeldByCurrentThread() ){
                r1.getLock().unlock();
                System.out.println(threadName + " released " + r1 .getName());
            }
        }

    }
}
