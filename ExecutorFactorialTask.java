package org.example.factorialTask;

import java.util.concurrent.*;

public class ExecutorFactorialTask {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        int[] numbersArray = {20, 2, 3, -1, 5};

        try{
            for (int num : numbersArray) {
                Future<Long> future = executorService.submit(new FactorialTask(num));

                try{
                    Long result = future.get(3,TimeUnit.SECONDS);
                    System.err.println("Future of :: " + num + " is " + result);
                }catch(TimeoutException ie){
                    System.err.println("Task for " + num + " timed out.");
                    future.cancel(true);
                }catch(ExecutionException ee){
                    System.err.println("Error computing factorial for " + num + ": " + ee.getCause().getMessage());
                } catch (InterruptedException e) {
                    System.err.println("Task interrupted for " + num);
                }
        }
        }finally {
            executorService.shutdown();
        }
    }


}

class FactorialTask implements Callable<Long>{
    private final int number;

    public  FactorialTask(int number){
        this.number = number;
    }

    @Override
    public Long call() throws IllegalArgumentException{
        if(number < 0){
            throw new IllegalArgumentException("Number should not be less than 0");
        }
        return calculateFactorial(number);
    }

    private Long calculateFactorial(int number){
        if(number == 0|| number == 1)
            return 1L;
        return number * calculateFactorial(number-1);
    }
}
