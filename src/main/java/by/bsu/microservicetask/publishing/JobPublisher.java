package by.bsu.microservicetask.publishing;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Runs CustomJob by schedule
 */
public class JobPublisher {
    private static final Logger LOGGER = Logger.getLogger(JobPublisher.class);
    private static final String GROUP_NAME = "rabbitmqGroup";
    private static final String JOB_NAME = "rabbitmq";
    private static final String TRIGGER_NAME = "trigger";

    public static void doScheduledPublish() {
        try{
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail jobDetail = newJob(CustomJob.class)
                    .withIdentity(JOB_NAME, GROUP_NAME).build();

            Trigger trigger = newTrigger()
                    .withIdentity(TRIGGER_NAME, GROUP_NAME).startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(5)
                            .repeatForever()).build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e){
            LOGGER.error("Exception was thrown." + e.getMessage());
        }
    }
}
