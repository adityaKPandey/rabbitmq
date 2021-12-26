package com.chat.fanout;

import com.chat.common.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.util.Scanner;
import java.util.UUID;

import static com.exchange.constants.RabbitMQConstants.*;

public class Sender {

  public static void main(String[] argv) throws Exception {
    RabbitMQConnectionFactory connectionFactory = RabbitMQConnectionFactory.getInstance();

    Connection connection = connectionFactory.getConnection();
    Channel channel = connectionFactory.getChannel();

    Thread send = new Thread(() -> {
      try {
        channel.exchangeDeclare(CHAT_FAN_OUT_EXCHANGE, "fanout");
        while (true) {
          Scanner scanner = new Scanner(System.in);
          String message = scanner.nextLine();

          channel.basicPublish(CHAT_FAN_OUT_EXCHANGE, "", null, message.getBytes("UTF-8"));
          System.out.println(" [x] Sent '" + message + "'");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    send.start();

    Thread recv = new Thread(() -> {
      try {
        String queueName = CHAT_FAN_OUT_QUEUE + ":" + UUID.randomUUID().toString();
        channel.exchangeDeclare(CHAT_FAN_OUT_EXCHANGE, "fanout");
        channel.queueDeclare(queueName, true, false, false, null);

        channel.queueBind(queueName, CHAT_FAN_OUT_EXCHANGE, "");

        System.out.println(
            " [*] Waiting for messages in queue " + queueName + ". To exit press CTRL+C");

        while (true) {
          DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
          };

          channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
          });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    recv.start();

  }
}