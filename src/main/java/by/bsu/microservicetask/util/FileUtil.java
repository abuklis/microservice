package by.bsu.microservicetask.util;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Consists of method for writing into file
 */
class FileUtil {
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

    static void writeDataIntoFile(String fileName, String data) {
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))){
            writer.println(data);
        } catch (IOException e){
            LOGGER.error(e.getMessage());
        }
    }
}
