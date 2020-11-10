package utils;

import DTO.ChuckDTO;
import DTO.CombinedJokeDTO;
import DTO.DadDTO;
import DTO.FriendsDTO;
import DTO.InsultDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JokeFetcher {
    //https://api.chucknorris.io/jokes/random
    //https://icanhazdadjoke.com
    //https://evilinsult.com/generate_insult.php?lang=en&type=json



    public static String runParallel(ExecutorService executorService) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        long start = System.nanoTime(); 
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
               
        Callable<ChuckDTO> chuckTask = new Callable<ChuckDTO>(){
            @Override
            public ChuckDTO call() throws Exception {
               String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
               ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
               return chuckDTO; 
            }         
        }; 
        
        Callable<DadDTO> dadTask = new Callable<DadDTO>(){
            @Override
            public DadDTO call() throws Exception {
               String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
               DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
               return dadDTO; 
            }         
        }; 
        
        
        
        Callable<InsultDTO> insultTask = new Callable<InsultDTO>(){
            @Override
            public InsultDTO call() throws Exception {
               String insult = HttpUtils.fetchData("https://evilinsult.com/generate_insult.php?lang=en&type=json");
               InsultDTO insultDTO = gson.fromJson(insult, InsultDTO.class);
               return insultDTO; 
            }         
        }; 

        Future<ChuckDTO> futureChuck = executorService.submit(chuckTask); 
        Future<DadDTO> futureDad = executorService.submit(dadTask); 
        Future<InsultDTO> futureInsult = executorService.submit(insultTask); 
        

        ChuckDTO chuck = futureChuck.get(2,TimeUnit.SECONDS); 
        DadDTO dad = futureDad.get(2,TimeUnit.SECONDS); 
        InsultDTO insult = futureInsult.get(2,TimeUnit.SECONDS); 
        
        CombinedJokeDTO combinedDTO = new CombinedJokeDTO(chuck,dad,insult);
        String combinedJSON = gson.toJson(combinedDTO); 
        
        return combinedJSON;

    }
    
   

}
