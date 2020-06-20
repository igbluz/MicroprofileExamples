package ch.berchtold.microprofile.sample.activemq.frontend;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient
@ApplicationScoped
public interface Service {

    @POST
    @Path("/{message}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response sendMessageToQueue(@PathParam("message") String parameter);

}
