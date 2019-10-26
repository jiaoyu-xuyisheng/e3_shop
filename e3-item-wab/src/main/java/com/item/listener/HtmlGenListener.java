package com.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.item.pojo.Item;
import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.pojo.TbItemDesc;
import com.jiaoyushop.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HtmlGenListener implements MessageListener {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${HTML_GEN}")
	private String HTML_GEN;

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
			TbItem tbItem = itemService.getItemById(itemId);
			Item item=new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			Map<String,Object> data=new HashMap<String,Object> ();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			Configuration configuration=freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Writer writer=new FileWriter(new File(HTML_GEN+itemId+".html"));
			template.process(data, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
