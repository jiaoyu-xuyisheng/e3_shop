package com.search.mytest;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolr {
	
	@Test
	public void addDocument() throws Exception{
		
		SolrServer solrServer= new HttpSolrServer("http://192.168.25.129:8080/solr");
		SolrInputDocument document =new SolrInputDocument();
		document.addField("id","texst001");
		document.addField("item_title", "测试商品");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void delDocument() throws Exception{
		
		SolrServer solrServer= new HttpSolrServer("http://192.168.25.129:8080/solr");		
		solrServer.deleteById("texst001");
		solrServer.commit();
	}
	

}
