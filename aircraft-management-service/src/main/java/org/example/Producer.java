package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


public class Producer {
    private final static String QUEUE_NAME = "aircraft_info";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        String rabbitMqHost = System.getenv("RABBITMQ_HOST");
        factory.setHost(rabbitMqHost != null ? rabbitMqHost : "localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            sendUpdateNotification(channel);
        }
    }


    public static void sendUpdateNotification(Channel channel) throws Exception {
        String message = "updateOccurred";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent update notification: '" +  message + "'");
    }
}

