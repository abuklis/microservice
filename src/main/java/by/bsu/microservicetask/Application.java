package by.bsu.microservicetask;

import by.bsu.microservicetask.util.RabbitMQUtil;

/**
 * Runs publish-subscribe task
 */
public class Application {

    public static void main(String[] args) {
        RabbitMQUtil.doSubscribe();
        RabbitMQUtil.doPublish("Secret text to encrypt");
    }
}
