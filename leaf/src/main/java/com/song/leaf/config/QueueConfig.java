package com.song.leaf.config;

import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class QueueConfig {
	@Value("${queue}")
	private String queue;

	@Bean
	public Queue logQueue() {
		return new ActiveMQQueue(queue);
	}

	@Bean
	ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://192.168.120.128:61616");
		return activeMQConnectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory, Queue queue) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setDeliveryMode(2);// 进行持久化配置 1表示非持久化，2表示持久化</span>
		jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
		jmsTemplate.setDefaultDestination(queue); // 此处可不设置默认，在发送消息时也可设置队列
		jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);// 客户端签收模式</span>
		return jmsTemplate;
	}

	// 定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
	@Bean(name = "jmsQueueListener")
	public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(
			ActiveMQConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(activeMQConnectionFactory);
		// 设置连接数
		factory.setConcurrency("1-10");
		// 重连间隔时间
		factory.setRecoveryInterval(1000L);
		factory.setSessionAcknowledgeMode(4);
		return factory;
	}

}
