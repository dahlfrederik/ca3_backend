package rest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utils.JokeFetcher;

/**
 * REST Web Service
 */
@Path("jokes")
public class JokeResource {
    private ExecutorService es = Executors.newCachedThreadPool();

    @Context
    private UriInfo context;
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return JokeFetcher.runParallel(es); 
    }

   
}
