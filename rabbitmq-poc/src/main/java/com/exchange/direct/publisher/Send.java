package com.exchange.direct.publisher;

import com.exchange.constants.RabbitMQConstants;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String [] args){

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(RabbitMQConstants.DIRECT_EXCHANGE , BuiltinExchangeType.DIRECT , true);
            System.out.println("Exchange declareOk:" + declareOk.toString()) ;
            channel.queueDeclare(RabbitMQConstants.QUEUE_NAME2, true, false, false, null);
            String message = "How are you?";
           // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE , RabbitMQConstants.ROUTING_KEY22 , null , message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }
}
