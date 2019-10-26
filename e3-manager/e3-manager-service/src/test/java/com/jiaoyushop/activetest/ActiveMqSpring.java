package com.jiaoyushop.activetest;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Destination;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqSpring {
	
	@Test
	public void sendMessageByMq() throws Exception{
		ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext-activemq.xml");
		JmsTemplate jmsTemplate=app.getBean(JmsTemplate.class);
		Destination destination=(Destination)app.getBean("queueDestination");
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				return session.createTextMessage("spring activemq test");
			}
		});
	}
}
