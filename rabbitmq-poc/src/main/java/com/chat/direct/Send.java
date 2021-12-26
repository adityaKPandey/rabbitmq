package com.chat.direct;

import com.chat.common.RabbitMQConnectionFactory;
import com.exchange.constants.RabbitMQConstants;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.exchange.constants.RabbitMQConstants.*;

public class Send {

  public static void main(String[] args) {

    try {

      RabbitMQConnectionFactory connectionFactory = RabbitMQConnectionFactory.getInstance();
      Connection connection = connectionFactory.getConnection();
      Channel channel = connectionFactory.getChannel();

      AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(
          RabbitMQConstants.CHAT_DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true);
      System.out.println("Exchange declareOk:" + declareOk.toString());
      channel.queueDeclare(RabbitMQConstants.CHAT_DIRECT_QUEUE, true, false, false, null);

      Thread send = new Thread(() -> {
        while (true) {
          Scanner scanner = new Scanner(System.in);
          String message = scanner.nextLine();
          // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
          try {
            channel.basicPublish(RabbitMQConstants.CHAT_DIRECT_EXCHANGE,
                RabbitMQConstants.ROUTING_KEY22, null, message.getBytes());
          } catch (IOException e) {
            e.printStackTrace();
          }

          //System.out.println(" [x] Sent '" + message + "'");
        }
      });

      send.start();

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Thread recv = new Thread(() -> {

        //recv
        try {

          channel.queueBind(CHAT_DIRECT_QUEUE, CHAT_DIRECT_EXCHANGE, ROUTING_KEY22);

          while (true) {
            //System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, message1) -> {

              String messageStr = new String(message1.getBody(), StandardCharsets.UTF_8);

              System.out.println(
                  " [x] Received '" + messageStr + "'" + " from exchange:" + message1.getEnvelope()
                      .getExchange()
                      + " with routing key:" + message1.getEnvelope().getRoutingKey());

            };

            channel.basicConsume(CHAT_DIRECT_QUEUE, true, deliverCallback, consumerTag -> {
            });
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      );

      recv.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
