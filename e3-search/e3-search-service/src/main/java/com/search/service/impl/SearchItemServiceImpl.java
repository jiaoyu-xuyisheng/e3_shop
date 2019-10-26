package com.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.SearchItem;
import com.jiaoyushop.util.SearchResult;
import com.search.dao.SearchDao;
import com.search.mapper.ItemMapper;
import com.search.service.SearchItemService;


@Service
public class SearchItemServiceImpl implements SearchItemService{
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchDao;
	
	
	@Value("${DEFAULT_FIELD}")
	private String DEFAULT_FIELD;
	

	@Override
	public E3Result importItems() {
		try {
			List<SearchItem> itemList=itemMapper.getItemByList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document=new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				solrServer.add(document);
			}
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500,"商品导入失败");
		}		
	}

	@Override
	public SearchResult search(String keyWord, int page, int rows) throws Exception {
		SolrQuery query=new SolrQuery();
		query.setQuery(keyWord);
		query.setStart((page-1)*rows);
		query.setRows(rows);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		SearchResult result = searchDao.search(query);
		long recourdCount = result.getRecourdCount();
		int pages=(int) (recourdCount/rows);
		if(recourdCount%rows>0)pages++;
		result.setTotalPages(pages);
		return result;
	}
	
	public E3Result addDocument(long itemId) throws Exception{
		SearchItem searchItem=itemMapper.getItemById(itemId);
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		solrServer.add(document);
		solrServer.commit();	
		return E3Result.ok();
	}
	
	
	
	

}
