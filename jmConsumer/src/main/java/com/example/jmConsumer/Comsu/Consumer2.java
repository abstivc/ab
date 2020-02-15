package com.example.jmConsumer.Comsu;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Consumer2 {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "mytest.queue")
    @SendTo("out.queue")
    public String receiveQueue(TextMessage text) {
        try {
            String msg = text.getText();
            System.out.println("Consumer2收到的报文为:"+ msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return "return message"+text;
    }
}