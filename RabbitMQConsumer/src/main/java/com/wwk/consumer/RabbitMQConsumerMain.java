package com.wwk.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 * @author wwkbear
 * @create 2023-02-14-16:48
 */
public class RabbitMQConsumerMain {
    /*
    消费者从MQ中接收数据流程：
        1.同样先创建连接
        2.创建通道
        3.接收信息

     详细步骤：
        0.创建一个常量用于定义队列名
        1.创建一个连接工厂，并设置好RabbitMQ账号和地址，再创建连接
        2.利用连接创建通道
            在获取信息前，接收消息方法需要定义两个函数。
                DeliverCallback
                CancelCallback
        3.利用通道获取信息
            basicConsume需要的参数：
                1.队列名（需要消费的队列）
                2.消费成功后是否自动应答（true自动，false手动）
                3.消费者成功接收消息后的回调（函数）
                4.消费者取消后的回调（函数）
     */
    private static final String QUEUE_NAME = "wwk";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.16.128");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //声明函数
            //消费者成功接收消息后的回调
            DeliverCallback deliverCallback = (consumerTag,message) -> {
                System.out.println( new String(message.getBody()) );
            };
            //消费者取消消息的回调
            CancelCallback cancelCallback = consumerTag ->{
                System.out.println("消息 被消费者取消（中断）");
            };
            //接收消息
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
