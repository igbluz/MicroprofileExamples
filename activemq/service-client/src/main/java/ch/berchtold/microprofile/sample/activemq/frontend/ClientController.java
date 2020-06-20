package ch.berchtold.microprofile.sample.activemq.frontend;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/client")
@ApplicationScoped
public class ClientController {

    @Inject
    @RestClient
    private Service service;

    @Inject
    ClientControllerResponse clientControllerResponse;

    @POST
    @Path("/test/{message}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response onClientSide(@PathParam("message") String parameter) {

        Response response =  service.sendMessageToQueue(parameter);
        if (response.getStatus() == 201) {
            clientControllerResponse.setResponse("Message successful delivered in Queueing System");
            return Response.ok(clientControllerResponse).build();
        }
        return Response.serverError().build();

    }
}
