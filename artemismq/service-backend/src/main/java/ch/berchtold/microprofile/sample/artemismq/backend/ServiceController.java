package ch.berchtold.microprofile.sample.artemismq.backend;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.jms.JMSContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/backend/service")
public class ServiceController {

    @Inject
    @JMSConnectionFactory("jms/myCF")
    private JMSContext jmsContext;

    @POST
    @Path("/{message}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response sendMessageToQueue(@PathParam("message") String message) {
        try {
            Queue queue = jmsContext.createQueue("sampleQueue");
            jmsContext.createProducer().send(queue, message );
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
}
