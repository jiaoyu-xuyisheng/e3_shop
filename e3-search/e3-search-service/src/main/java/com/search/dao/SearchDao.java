package com.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiaoyushop.util.SearchItem;
import com.jiaoyushop.util.SearchResult;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	
	public SearchResult search(SolrQuery query) throws SolrServerException {
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList resultsList = queryResponse.getResults();
		long numFound = resultsList.getNumFound();
		SearchResult result=new SearchResult();
		result.setRecourdCount(numFound);
		List<SearchItem> itemList = new ArrayList<>();
		//得到高亮后的结果
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : resultsList) {
			SearchItem searchItem=new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle="";
			if(list!=null && list.size()>0) {
				itemTitle=list.get(0);
			}else {
				 itemTitle = (String) solrDocument.get("item_title");
			}
			searchItem.setTitle(itemTitle);
			itemList.add(searchItem);		
		}
		result.setItemList(itemList);
		return result;
	}
	
}
