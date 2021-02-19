package de.deloitte;

import java.time.Duration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;

@Path("/hello")
public class GreetingResource {

    @GET
    @Blocking
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String t_name = Thread.currentThread().getName();
        return "Thread name is "+ t_name;
    }

    
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_PLAIN)
    @Path("/{count}")
    public Multi<String> hello2(int count) {
        String t_name = Thread.currentThread().getName();
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
            .onItem()
            .transform(a->String.format("Theard is %s", t_name))
            .transform()
            .byTakingFirstItems(count);
    }


}

