package rest;

import DTO.ChuckDTO;
import DTO.CombinedDTO;
import DTO.DadDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import utils.HttpUtils;

/**
 * REST Web Service
 *
 * @author josef
 */
@Path("jokes")
public class JokeResource {

    @Context
    private UriInfo context;

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
        
        System.out.println("JSON fetched from chucknorris:");
        System.out.println(chuck);
        System.out.println("JSON fetched from dadjokes:");
        System.out.println(dad);
      
        ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
        System.out.println("Value: " + chuckDTO.getValue() + ", id: " + chuckDTO.getId());
        
        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class); 

        CombinedDTO combinedDTO = new CombinedDTO(chuckDTO,dadDTO);
        String combinedJSON = gson.toJson(combinedDTO); 

        return combinedJSON; 
    }

   
}
