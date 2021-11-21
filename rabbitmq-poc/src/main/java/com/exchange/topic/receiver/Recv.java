package com.exchange.topic.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.exchange.constants.RabbitMQConstants.*;

public class Recv {

   public static void main(String [] args) throws Exception{

       ConnectionFactory connectionFactory = new ConnectionFactory();
       connectionFactory.setHost("localhost");
       Connection connection = connectionFactory.newConnection();

       Channel channel = connection.createChannel() ;
       channel.queueDeclare(TOPIC_QUEUE_NAME , true , false , false , null) ;
       channel.queueBind(TOPIC_QUEUE_NAME,TOPIC_EXCHANGE , ROUTING_KEY_TOPIC_PATTERN  ) ;

       System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

       DeliverCallback deliverCallback = (consumerTag, message) -> {

           String messageStr = new String(message.getBody()  , StandardCharsets.UTF_8) ;

           System.out.println(" [x] Received '" + messageStr + "'" + " from exchange:"+  message.getEnvelope().getExchange()
           + " with routing key:"+message.getEnvelope().getRoutingKey());

       } ;

       channel.basicConsume(TOPIC_QUEUE_NAME , true ,   deliverCallback , consumerTag -> { } ) ;

   }

}
