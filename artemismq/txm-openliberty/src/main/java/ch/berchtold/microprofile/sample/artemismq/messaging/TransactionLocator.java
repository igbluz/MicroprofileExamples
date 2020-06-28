package ch.berchtold.microprofile.sample.artemismq.messaging; 
 
import javax.transaction.TransactionManager;

import org.apache.activemq.artemis.service.extensions.transactions.TransactionManagerLocator;
import org.jboss.logging.Logger;

public class TransactionLocator implements TransactionManagerLocator {
    private static final Logger logger = Logger.getLogger(TransactionLocator.class);

    public TransactionManager getTransactionManager() {
        logger.debug("TransactionLocator entry");

        try {
            return (TransactionManager) com.ibm.tx.jta.TransactionManagerFactory.getTransactionManager();

        } catch (Exception e) {
            logger.error("Could not obtain transaction manager", e);
            logger.debug("TransactionLocator exception exit");
            return null;
        }
    }
}