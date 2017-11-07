package by.bsu.microservicetask.consuming;

import com.rabbitmq.client.*;
import org.apache.log4j.Logger;


/**
 * Receives message from Rabbit MQ,
 * runs consumer
 */

public class Receiver {
    private static final Logger LOGGER = Logger.getLogger(Receiver.class);
    private static final String EXCHANGE_NAME = "messageExchange";
    private static final String HOST = "localhost";

    public static void main(String[] argv) throws Exception {
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
    }
}
