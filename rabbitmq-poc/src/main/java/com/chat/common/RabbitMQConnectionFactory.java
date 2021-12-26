package com.chat.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitMQConnectionFactory {

    private  ConnectionFactory connectionFactory ;
    private  Channel channel ;
    private  Connection connection ;
    private  final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConnectionFactory.class) ;

    private RabbitMQConnectionFactory(){

        try {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        }catch (Exception e){
            LOGGER.debug("Error in initializing RabbitMQ connection factory" ,e);
        }
    }

    private static  class RabbitMQConnectionFactoryBuilder {
        private static final RabbitMQConnectionFactory RABBIT_MQ_CONNECTION_FACTORY = new RabbitMQConnectionFactory() ;
    }

    public  Connection getConnection() {
        return connection;
    }

    public  Channel getChannel() {
       return channel ;
    }

    public static RabbitMQConnectionFactory getInstance() {
        return RabbitMQConnectionFactoryBuilder.RABBIT_MQ_CONNECTION_FACTORY ;
    }

}
