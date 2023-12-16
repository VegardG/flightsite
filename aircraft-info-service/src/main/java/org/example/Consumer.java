package org.example;

import com.rabbitmq.client.*;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Consumer {

    private final static String QUEUE_NAME = "aircraft_info";
    private static JSONObject aircraftData;

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");

            if ("updateOccurred".equals(message)) {
                handleUpdate();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

    private static void handleUpdate() {
        try {
            String filePath = "aircraft-info-service/data/aircraftData.json"; // Update this path
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            aircraftData = new JSONObject(content);
            System.out.println("Aircraft data updated: " + aircraftData);
        } catch (IOException e) {
            System.err.println("Error updating aircraft data: " + e.getMessage());
        }
    }
}
