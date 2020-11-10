/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import DTO.CombinedQuotesDTO;
import DTO.FriendsDTO;
import DTO.TrumpDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class QuoteFetcher {
    //https://api.tronalddump.io/random/quote
    //https://friends-quotes-api.herokuapp.com/quotes/random
    
    public static String runParallel(ExecutorService executorService) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        long start = System.nanoTime(); 
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        Callable<FriendsDTO> friendsTask = new Callable<FriendsDTO>(){
            @Override
            public FriendsDTO call() throws Exception {
               String friends = HttpUtils.fetchData("https://friends-quotes-api.herokuapp.com/quotes/random");
               FriendsDTO friendsDTO = gson.fromJson(friends, FriendsDTO.class);
               return friendsDTO; 
            }         
        }; 
   
           Callable<TrumpDTO> trumpTask = new Callable<TrumpDTO>(){
            @Override
            public TrumpDTO call() throws Exception {
               String trump = HttpUtils.fetchData("https://api.tronalddump.io/random/quote");
               TrumpDTO trumpDTO = gson.fromJson(trump, TrumpDTO.class);
               return trumpDTO; 
            }         
        }; 
        
        Future<FriendsDTO> futureFriend = executorService.submit(friendsTask); 
        Future<TrumpDTO> futureTrump = executorService.submit(trumpTask); 
       
        FriendsDTO friend = futureFriend.get(2,TimeUnit.SECONDS); 
        TrumpDTO trump = futureTrump.get(2,TimeUnit.SECONDS); 
        
        
        CombinedQuotesDTO combinedQuotesDTO = new CombinedQuotesDTO(friend,trump); 
       
        String combinedJSON = gson.toJson(combinedQuotesDTO); 
        
        return combinedJSON;

    }
    
   

}


