package ch.berchtold.microprofile.sample.activemq.backend.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "JmsReceiverEJB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "sample.backend.queue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class JmsReceiver implements MessageListener {
    
    @Override
    public void onMessage(Message message) {
        
        TextMessage textMessage = (TextMessage) message;
        
        try {
            String incomingText = textMessage.getText();
            System.out.println("Receiving message: "+incomingText);

        } catch (Exception ex) {

        }
    }
    
}

