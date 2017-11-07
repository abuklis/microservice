package by.bsu.microservicetask.publishing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Publishes message to Rabbit MQ
 * to encode data using Cesar's encrypting
 */
public class CustomJob implements Job {
    private static final String EXCHANGE_NAME = "messageExchange";
    private static final String CHARSET = "UTF-8";
    private static final String HOST = "localhost";
    private static final Logger LOGGER = Logger.getLogger(CustomJob.class);

    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            JsonObject message = Json.createObjectBuilder()
                    .add("command", "Do Cesar's encryption")
                    .add("content", "Secret text to encrypt")
                    .build();

            channel.basicPublish(EXCHANGE_NAME, "", null,
                    message.toString().getBytes(CHARSET));
            LOGGER.info("Sent :" + message);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception was thrown." + e.getMessage());
        } finally {
            closeResources(connection, channel);
        }
    }

    private void closeResources(Connection connection, Channel channel){
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception was thrown." + e.getMessage());
        }
    }
}
