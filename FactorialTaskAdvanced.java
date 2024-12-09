package org.example.factorialTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FactorialTaskAdvanced {
    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        2,
                        5,
                        60,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(10),
                        Executors.defaultThreadFactory(),
                        new CustomRejectionHandler()
                );

        List<Callable<Long>> taskList = new ArrayList<>();

        int[] numberArray = {1,2,3,13,-1,-2,11};

        for(int num : numberArray) {
            taskList.add(new FactorialTasks(num));
        }

        try {
            List<Future<Long>> futures = threadPoolExecutor.invokeAll(taskList);

            for(int i=0;i<numberArray.length;i++){
                Future<Long> future = futures.get(i);

                try {
                    Long result = future.get(3, TimeUnit.SECONDS);
                    System.out.println(numberArray[i] + " " + result);
                } catch (TimeoutException te) {
                    future.cancel(true);
                    throw new RuntimeException(te);
                } catch (ExecutionException ee) {
                    System.err.println(ee.getLocalizedMessage());
                }catch (InterruptedException ie){
                    System.err.println(ie.getLocalizedMessage() + " " + ie.getMessage());
                }

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            threadPoolExecutor.shutdown();
        }

        monitorTasks(threadPoolExecutor);
    }

    private static void monitorTasks(ThreadPoolExecutor threadPoolExecutor){
        System.out.println("Active Threads   " + threadPoolExecutor.getActiveCount());
        System.out.println("Completed Tasks  " + threadPoolExecutor.getCompletedTaskCount());
        System.out.println("Total Tasks      " + threadPoolExecutor.getTaskCount());
    }
}

class CustomRejectionHandler implements RejectedExecutionHandler{
    @Override
    public void rejectedExecution(Runnable r,ThreadPoolExecutor executor){
        System.out.println(
        "::\n Task Rejected  :: "
        + "\n Active Thread  :: " + executor.getActiveCount()
        + "\n Queue Size     :: " + executor.getQueue().size()
        );
    }
}
class FactorialTasks implements  Callable<Long>{
    private final int number;

    FactorialTasks(int number){
        this.number = number;
    }
    @Override
    public Long call() throws IllegalArgumentException{
        if(number < 0){
            throw new IllegalArgumentException("Number should not be less than 0");
        }
        return factorialCalculator(number);
    }

    private Long factorialCalculator(int number){
        if(number ==0 || number==1){
            return 1L;
        }
        return number*factorialCalculator(number-1);
    }
}