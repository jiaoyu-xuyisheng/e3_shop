package com.contant.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMqTest {

	@Test
	public void sendMsgByMQ() throws JMSException {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.166:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Queue query = session.createQueue("text-queue");
		MessageProducer producer = session.createProducer(query);		
		TextMessage textMessage = session.createTextMessage("hello activemq");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();	
	}
	
	@Test
	public void testQueryConsumer() throws Exception {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.166:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Queue query = session.createQueue("spring-queue");
		MessageConsumer messageConsumer = session.createConsumer(query);
		messageConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMassage=(TextMessage) message;
				String text;
				try {
					text=textMassage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		});
		System.in.read();	
	}
	
	@Test
	public void sendMsgByBigMQ() throws JMSException {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.166:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		 Topic topic= session.createTopic("topic-text");
		MessageProducer producer = session.createProducer(topic);		
		TextMessage textMessage = session.createTextMessage("topic message activemq");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();	
	}
	
	@Test
	public void testTopicQueryConsumer() throws Exception {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.166:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic-text");
		MessageConsumer messageConsumer = session.createConsumer(topic);
		messageConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMassage=(TextMessage) message;
				String text;
				try {
					text=textMassage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		});
		System.in.read();	
	}
}
