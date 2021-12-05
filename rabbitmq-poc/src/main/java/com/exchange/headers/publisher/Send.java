package com.exchange.headers.publisher;

import com.exchange.constants.RabbitMQConstants;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.exchange.constants.RabbitMQConstants.HEADERS_EXCHANGE;
import static com.exchange.constants.RabbitMQConstants.HEADERS_QUEUE_NAME;

public class Send {

    public static void main(String [] args){

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(RabbitMQConstants.HEADERS_EXCHANGE, BuiltinExchangeType.HEADERS , true);
            System.out.println("Exchange declareOk:" + declareOk.toString()) ;
            channel.queueDeclare(RabbitMQConstants.HEADERS_QUEUE_NAME, true, false, false, null);


            String message = "How are you?" + Math.random();
           // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            Map<String,Object> headers = new HashMap<>();
            //headers.put("x-match","all");
            headers.put("Content-Type","application/json");
            headers.put("Contenst-Type","applicatsion/json");
            headers.put("eeContenst-Type","applicasstion/json");
            headers.put("Content-Type","application/json");

            // x-match :all or any : this is decided between exchange and receiver/consumer
            //based on x-match and headers decided upon , messages sent by publisher , that match header criteria , are received by consumer

            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().headers(headers).build();

            channel.basicPublish(RabbitMQConstants.HEADERS_EXCHANGE , "" , basicProperties , message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }
}
