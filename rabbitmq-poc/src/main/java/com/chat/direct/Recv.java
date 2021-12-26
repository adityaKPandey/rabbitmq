package com.chat.direct;

import com.chat.common.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.exchange.constants.RabbitMQConstants.*;

public class Recv {

   public static void main(String [] args) throws Exception{

       RabbitMQConnectionFactory connectionFactory = RabbitMQConnectionFactory.getInstance();
       Connection connection = connectionFactory.getConnection();
       Channel channel = connectionFactory.getChannel() ;

       channel.queueDeclare(CHAT_DIRECT_QUEUE , true , false , false , null) ;
       channel.queueBind(CHAT_DIRECT_QUEUE,CHAT_DIRECT_EXCHANGE , ROUTING_KEY22  ) ;

       System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

       DeliverCallback deliverCallback = (consumerTag, message) -> {

           String messageStr = new String(message.getBody()  , StandardCharsets.UTF_8) ;

           System.out.println(" [x] Received '" + messageStr + "'" + " from exchange:"+  message.getEnvelope().getExchange()
           + " with routing key:"+message.getEnvelope().getRoutingKey());

       } ;

       channel.basicConsume(CHAT_DIRECT_QUEUE , true ,   deliverCallback , consumerTag -> { } ) ;

   }

}
