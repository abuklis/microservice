package by.bsu.microservicetask.consuming;

import by.bsu.microservicetask.util.RabbitMQUtil;


/**
 * Receives message from Rabbit MQ,
 * runs consumer
 */

public class Receiver {

    public static void doReceive() {
        RabbitMQUtil.doSubscribe();
    }
}
