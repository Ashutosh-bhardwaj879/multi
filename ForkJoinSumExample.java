package org.example.forkjoinsum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSumExample extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 5;
    private final int[] array;
    private final int start;
    private final int end;

    public ForkJoinSumExample(int[] array,int start,int end){
        this.array = array;
        this.start = start;
        this.end   = end;
    }
    @Override
    protected Integer compute(){

        /*If threshhold  value is greater than this then directly compute*/
        if( end - start <= THRESHOLD){
            int sum = 0;
            for(int i=start;i<end;i++){
                sum += array[i];
            }
            System.out.println(Thread.currentThread().getName() + " computed sum :" + sum);
            return sum;
        }

        int mid = (start + end) /2;

        ForkJoinSumExample leftTask  = new ForkJoinSumExample(array,0,mid);
        ForkJoinSumExample rightTask = new ForkJoinSumExample(array,mid,end);

        leftTask.fork();
        rightTask.fork();

        int leftResult  = leftTask.join();
        int rightResult = rightTask.join();

        return leftResult + rightResult;
    }

    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        ForkJoinPool       pool = new ForkJoinPool();
        ForkJoinSumExample task = new ForkJoinSumExample(array,0,array.length);

        int result = pool.invoke(task);

        System.out.println("Total Sum :: " + result);
    }
}
