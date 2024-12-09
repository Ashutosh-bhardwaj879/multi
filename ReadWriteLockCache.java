package org.example.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockCache<K,V> {
    private final Map<K,V> cache = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public V read(K key){
        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " reading " + key);
            return cache.get(key);
        }finally {
            lock.readLock().unlock();
        }
    }

    public void write(K key,V value){
        lock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " is writing " + key + " -> " + value);
            cache.put(key,value);
        }finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockCache<String,String> cache = new ReadWriteLockCache<>();
        Thread writer = new Thread(()->{
            for(int i=1;i<=5;i++){
                cache.write(" key "+i," value "+i);
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread reader1 = new Thread(()->{
            for(int i=1;i<=5;i++){
                cache.read(" key " + i);
                try{
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread reader2 = new Thread(()->{
            for(int i=1;i<=5;i++){
                cache.read(" key " + i);
                try{
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        writer.start();
        reader1.start();
        reader2.start();
    }

}
