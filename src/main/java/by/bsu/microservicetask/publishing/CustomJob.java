package by.bsu.microservicetask.publishing;

import by.bsu.microservicetask.util.RabbitMQUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Publishes message to Rabbit MQ
 * to encode data using Cesar's encrypting
 */
public class CustomJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        RabbitMQUtil.doPublish("Secret text to encrypt");
    }
}
