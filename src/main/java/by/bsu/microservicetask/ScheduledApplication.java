package by.bsu.microservicetask;

import by.bsu.microservicetask.consuming.Receiver;
import by.bsu.microservicetask.publishing.JobPublisher;

/**
 * Runs publish-subscribe task using Quartz
 */
public class ScheduledApplication {

    public static void main(String[] args) {
        Receiver.doReceive();
        JobPublisher.doScheduledPublish();
    }
}
