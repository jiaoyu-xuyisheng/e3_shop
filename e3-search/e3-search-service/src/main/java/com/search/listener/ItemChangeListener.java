package com.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiaoyushop.util.E3Result;
import com.search.service.impl.SearchItemServiceImpl;

public class ItemChangeListener implements MessageListener  {
	
	@Autowired
	private SearchItemServiceImpl searchItemServiceImpl;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textmessage=null;
			Long itemId=null;
			if(message instanceof TextMessage) {
				textmessage=(TextMessage) message;
				itemId=Long.parseLong(textmessage.getText());
				Thread.sleep(1000);
			}
			searchItemServiceImpl.addDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



}
