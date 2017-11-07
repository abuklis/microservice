package by.bsu.microservicetask.util;

import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consists of methods for publishing messages and receiving them
 */
public class RabbitMQUtil {
    private static final Logger LOGGER = Logger.getLogger(RabbitMQUtil.class);
    private static final String EXCHANGE_NAME = "messageExchange";
    private static final String CHARSET = "UTF-8";
    private static final String HOST = "localhost";

    public static void doPublish(String data){
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
                    .add("content", data)
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

    public static void doSubscribe() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            LOGGER.info("Consumer subscribed. To exit press CTRL+C");
            Consumer consumer = new CustomConsumer(channel);
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException | TimeoutException e){
            LOGGER.warn("Exception was thrown during subscribing.." + e.getMessage());
        }
    }

    private static void closeResources(Connection connection, Channel channel){
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception was thrown." + e.getMessage());
        }
    }
}
