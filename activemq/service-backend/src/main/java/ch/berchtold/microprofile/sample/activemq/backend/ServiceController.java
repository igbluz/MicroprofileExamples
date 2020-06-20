package ch.berchtold.microprofile.sample.activemq.backend;

import javax.annotation.Resource;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/backend/service")
public class ServiceController {

    @Resource(lookup = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/sample.backend.queue")
    private Queue queue;

    @POST
    @Path("/{message}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response sendMessageToQueue(@PathParam("message") String message) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            TextMessage textMessage = session.createTextMessage(message);
            session.createProducer(queue).send(textMessage);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
}
