package org.example;

import com.rabbitmq.client.*;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Consumer {

    private final static String QUEUE_NAME = "aircraft_info";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        String rabbitMqHost = System.getenv("RABBITMQ_HOST");
        factory.setHost(rabbitMqHost != null ? rabbitMqHost : "localhost");
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

    public static void sendUpdateNotification() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        String rabbitMqHost = System.getenv("RABBITMQ_HOST");
        factory.setHost(rabbitMqHost != null ? rabbitMqHost : "localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            String message = "updateOccured";
            channel.queueDeclare("aircraft_update_queue", false, false, false, null);
            channel.basicPublish("", "aircraft_queue_update", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

    private static void handleUpdate() {
        try {
            File file = new File("aircraft-info-service/data/aircraftData.json");
            String absolutePath = file.getAbsolutePath();
            String content = new String(Files.readAllBytes(Paths.get(absolutePath)));
            //String filePath = "C:\\Users\\vegar\\Desktop\\Mikrotjenester\\flightsite\\aircraft-info-service\\data\\aircraftData.json";
            //String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject aircraftData = new JSONObject(content);
            System.out.println("Aircraft data updated: " + aircraftData);
            sendUpdateNotification();
        } catch (IOException e) {
            System.err.println("Error updating aircraft data: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
