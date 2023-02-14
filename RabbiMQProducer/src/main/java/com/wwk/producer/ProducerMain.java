package com.wwk.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 * @author wwkbear
 * @create 2023-02-14-15:59
 */
public class ProducerMain {
    /*
    消息生产者的流程：
        1.创建连接 建立TCP连接。
        2.创建通道channel
        3.生成一个队列
        4.发出消息到队列中

     具体详细流程：
        1.创建一个连接工厂，并设置连接到RabbitMQ的账号，再创建一个连接。
        2.利用连接创建一个通道
        3.利用通道创建一个队列（正常来说要创建一个交换机先再创建一个队列。但只使用一个交换机则不需要）
            创建队列需要
                1.队列名称、
                2.队列内容是否持久化（存硬盘）、
                3.队列内容是否共享（是一个消费者使用还是谁都能用）、
                4.队列内容是否删除（最后一个消费者断开连接后会检查）
                5.其他参数
        4.利用通道发送消息publish
            发送消息需要：
                1.发送到具体交换机
                2.路由的key(队列)
                3.其他的参数信息
                4.发送消息的消息体
     */
    public static final String QUEUE_NAME = "wwk";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.16.128");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        String message = "MQ测试"; //要发送的消息
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //创建队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //发送消息
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("发送完成：" + message);

    }


}
