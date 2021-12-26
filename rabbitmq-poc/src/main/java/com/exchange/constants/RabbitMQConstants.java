package com.exchange.constants;

public class RabbitMQConstants{

    public final static String QUEUE_NAME = "hello2" ;
    public final static String QUEUE_NAME2 = "hello22" ;
    public final static String DIRECT_EXCHANGE = "simple_direct_exchange" ;
    public final static String ROUTING_KEY1 = "routing_key1" ;
    public final static String ROUTING_KEY2 = "routing_key2" ;
    public final static String ROUTING_KEY22 = "routing_key22" ;

    public final static String TOPIC_QUEUE_NAME = "helloTopic" ;
    public final static String TOPIC_EXCHANGE = "simple_topic_exchange" ;
    public final static String ROUTING_KEY_TOPIC_PATTERN = "topic.*" ;
    public final static String TOPIC_ROUTING_KEY = "topic.exchangeKey" ;

    public final static String HEADERS_QUEUE_NAME = "helloHeader" ;
    public final static String HEADERS_EXCHANGE = "simple_header_exchange" ;

    public final static String CHAT_FAN_OUT_EXCHANGE = "chat_fanout_exchange" ;
    public final static String CHAT_FAN_OUT_QUEUE = "chat_fanout_queue" ;

    public final static String CHAT_DIRECT_EXCHANGE = "chat_direct_exchange" ;
    public final static String CHAT_DIRECT_QUEUE = "chat_direct_queue" ;

}