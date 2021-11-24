package com.exchange.headers.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.exchange.constants.RabbitMQConstants.*;

public class Recv {

   public static void main(String [] args) throws Exception{

       ConnectionFactory connectionFactory = new ConnectionFactory();
       connectionFactory.setHost("localhost");
       Connection connection = connectionFactory.newConnection();

       Map<String,Object> headers = new HashMap<>();
       headers.put("eeContenst-Type","applicasstion/json");
       headers.put("Content-Type","application/json");
       headers.put("x-match","all");


       // x-match :all or any : this is decided between exchange and receiver/consumer
       //based on x-match and headers decided upon , messages sent by publisher , that match header criteria , are received by consumer

       Channel channel = connection.createChannel() ;
       channel.queueDeclare(HEADERS_QUEUE_NAME , true , false , false , null) ;

       channel.queueBind(HEADERS_QUEUE_NAME,HEADERS_EXCHANGE , "" , headers ) ;

       System.out.println(" [*] Waiting for messages. To exit press CTRL+C");



       DeliverCallback deliverCallback = (consumerTag, message) -> {

           String messageStr = new String(message.getBody()  , StandardCharsets.UTF_8) ;

           Object header = message.getProperties().getHeaders().get("Content-Type") ;

           System.out.println(" [x] Received '" + messageStr + "'" + " from exchange:"+  message.getEnvelope().getExchange()
           + " with routing key:"+message.getEnvelope().getRoutingKey() + " ,header:" + header.toString());

       } ;

       channel.basicConsume(HEADERS_QUEUE_NAME , true ,   deliverCallback , consumerTag -> { } ) ;

   }

}
