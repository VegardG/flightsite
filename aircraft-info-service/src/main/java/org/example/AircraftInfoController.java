package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@CrossOrigin(origins = "*")
@RestController
public class AircraftInfoController {

    private static final Logger logger = LoggerFactory.getLogger(AircraftInfoController.class);
    private static JSONObject aircraftData;

    // Static method to update aircraft data
    public static void updateAircraftData(JSONObject newData) {
        aircraftData = newData;
    }

    static {
        // Initial load of aircraft data
        loadAircraftData();
        // Sets up message queue listener
        setupListener();
    }

    // Loads aircraft data from JSON file
    private static void loadAircraftData() {
        try {
            File file = new File("data/aircraftData.json");
            String absolutePath = file.getAbsolutePath();
            String content = new String(Files.readAllBytes(Paths.get(absolutePath)));
            aircraftData = new JSONObject(content);
            logger.info("Loaded aircraft data: {}", aircraftData.toString());
        } catch (IOException e) {
            logger.error("Error loading aircraft data", e);
        }
    }

    // Endpoint to fetch information about an aircraft
    @GetMapping("/aircraft/{model}")
    public ResponseEntity<?> getAircraftInfo(@PathVariable String model) {
        if (aircraftData != null && aircraftData.has(model)) {
            return ResponseEntity.ok(aircraftData.getJSONObject(model).toString());
        } else {
            logger.warn("Model {} not found in aircraft data", model);
            return ResponseEntity.notFound().build();
        }
    }

    // Message queue listener
    private static void setupListener() {
        ConnectionFactory factory = new ConnectionFactory();
        String rabbitMqHost = System.getenv("RABBITMQ_HOST");
        factory.setHost(rabbitMqHost != null ? rabbitMqHost : "localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("aircraft_update_queue", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                if ("updateOccurred".equals(message)) {
                    loadAircraftData();
                }
            };
            channel.basicConsume("aircraft_update_queue", true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            logger.error("Error setting up listener", e);
        }
    }
}