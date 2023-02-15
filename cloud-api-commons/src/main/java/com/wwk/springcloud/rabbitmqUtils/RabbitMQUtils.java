package com.wwk.springcloud.rabbitmqUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ连接工具
 * @author wwkbear
 * @create 2023-02-14-17:48
 */
public class RabbitMQUtils {
    /**
     * 获取一个channel通道
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.16.128");
        factory.setUsername("admin");
        factory.setPassword("123");
        return factory.newConnection().createChannel();
    }
}
