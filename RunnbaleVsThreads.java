package org.example;

class ThreadDemo extends Thread{
    @Override
    public void run(){
        System.out.println("Hello Extends Thread");
    }
}
class RunnableDemo implements Runnable{
    @Override
    public void run(){
        System.out.print("Hello Runnable");
    }
}
public class RunnbaleVsThreads {
    public static  void main(String[] args){
        ThreadDemo td = new ThreadDemo();
        td.start();

        RunnableDemo rd = new RunnableDemo();
        Thread t = new Thread(rd);
        t.start();
    }
}
