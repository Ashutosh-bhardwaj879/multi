package org.example.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) {
        int userId = 101;

        CompletableFuture<String> userDetailsFuture = getUserDetails(userId);
        CompletableFuture<String> userOrdersFuture  = getUserOrders(userId);

        CompletableFuture<String> combinedFuture = userDetailsFuture.thenCombine(
                userOrdersFuture,
                (userDetails,userOrders) -> "Combining both data here :: \n" + userDetails + "\n" + userOrders
        );

        try{
            String combinedResult = combinedFuture.get();
            System.out.println(combinedResult);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    private static CompletableFuture<String> getUserDetails(int userId){
        return CompletableFuture.supplyAsync(()->{
           try{
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           return "User Details completed with :: " + userId;
        });
    }

    private static CompletableFuture<String> getUserOrders(int userId){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "User Orders completed with :: " + userId;
        });
    }
}
