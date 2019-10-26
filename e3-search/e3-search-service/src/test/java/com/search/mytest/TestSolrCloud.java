package com.search.mytest;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


public class TestSolrCloud {
	
	@Test
	public void testAddDocument() throws Exception{
		CloudSolrServer solrServer=new CloudSolrServer("192.168.25.129:2181,192.168.25.129:2182,192.168.25.129:2183");
		solrServer.setDefaultCollection("collection2");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("item_title", "测试商品");
		document.addField("item_price", "100");
		document.addField("id", "test01");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void testQueryDocument() throws Exception{
		CloudSolrServer solrServer=new CloudSolrServer("192.168.25.129:2181,192.168.25.129:2182,192.168.25.129:2183");
		solrServer.setDefaultCollection("collection2");
		SolrQuery query=new SolrQuery();
		query.set("q", "*:*");
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList results = queryResponse.getResults();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
		System.out.println("总记录数："+results.getNumFound());	
		solrServer.commit();
	}
	
	
}
