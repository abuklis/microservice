package by.bsu.microservicetask.consuming.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Creates HTTP Client for sending
 * http-requests to Azure function
 */
public class AzureUtil {
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class);
    private static final String FUNCTION_URL =
            "https://lambdataskbuklis.azurewebsites.net/api/HttpTriggerJS1?code=SZDJVQIg/cgOGIROJ8g18sDZE0gSqZBamxSuK21VCqmAfrrYVps2mg==";

    public static String sendRequestToAzure(String data){
        String responseBody = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(FUNCTION_URL);
            httpPost.setHeader("User-Agent", "Mozilla/5.0");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            String json = "{\"data\": \"" + data + "\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = client.execute(httpPost);
            responseBody = new BasicResponseHandler().handleResponse(response);
            responseBody = responseBody.replaceAll("\"", "");
            client.close();
        } catch (IOException e) {
            LOGGER.error("Exception during sending request.. " + e.getMessage());
        }
        return responseBody;
    }

}
