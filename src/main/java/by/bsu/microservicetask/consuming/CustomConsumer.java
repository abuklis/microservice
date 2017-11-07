package by.bsu.microservicetask.consuming;

import by.bsu.microservicetask.consuming.util.AzureUtil;
import by.bsu.microservicetask.consuming.util.FileUtil;
import by.bsu.microservicetask.consuming.util.JsonUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.log4j.Logger;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.IOException;

/**
 * extracts data from message,
 * sends it to Azure Lambda,
 * saves result into file
 */
public class CustomConsumer extends DefaultConsumer {
    private static final Logger LOGGER = Logger.getLogger(CustomConsumer.class);
    private static final String CHARSET = "UTF-8";
    private static final String CONTENT_PARAM = "content";
    private static final String OUTPUT_FILE = "output.txt";

    CustomConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, CHARSET);
        JsonObject receivedJson = JsonUtil.convertStringToJson(message);
        LOGGER.info("Received '" + message + "'");

        JsonValue content = receivedJson.get(CONTENT_PARAM);
        String contentValue = content.toString().replaceAll("\"", "");
        String response = AzureUtil.sendRequestToAzure(contentValue);
        if (response != null){
            FileUtil.writeDataIntoFile(OUTPUT_FILE, response);
        }
    }
}
