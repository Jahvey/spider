package com.xxo.es.web.utils;

import com.xxo.es.web.domain.Article;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Esutil {
	// 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
		static Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch")
				.put("client.transport.sniff", true).build();
		// 创建私有对象
		private static TransportClient client;
		static {
			try {
				/*Class<?> clazz = Class.forName(TransportClient.class.getName());
				Constructor<?> constructor = clazz
						.getDeclaredConstructor(Settings.class);
				//启用或禁用安全检查，值为true则表示反射的对象在使用时应该取消 Java语言访问检查
				constructor.setAccessible(true);
				client = (TransportClient) constructor.newInstance(settings);*/
				client = new TransportClient(settings);
				client.addTransportAddress(new InetSocketTransportAddress(
						"192.168.1.171", 9300));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 获取客户端
		 * @return
		 */
		public static synchronized TransportClient getTransportClient() {
			return client;
		}
	
	
	
	/**
	 * 建立索引
	 * @param index
	 * @param type
	 * @param article
	 * @return
	 */
	public static String addIndex(String index,String type,Article article){
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("title", article.getTitle());
		hashMap.put("author", article.getAuthor());
		hashMap.put("describe", article.getDescribe());
		hashMap.put("content", article.getContent());
		IndexResponse indexResponse = getTransportClient().prepareIndex(index, type, article.getId()+"").setSource(hashMap).get();
		return indexResponse.getId();
	}
	
	
	/**
	 * 查询
	 * @param key
	 * @param index
	 * @param type
	 * @param start
	 * @param row
	 * @return
	 */
	public static Map<String, Object> search(String key,String index,String type,int start,int row){
		Map<String, Object> hashMap = new HashMap<String,Object>();
		if(StringUtils.isBlank(key)){
			key="hbase";
		}
		SearchResponse searchResponse = getTransportClient().prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//默认不用特殊设置，
				//设置查询信息
				.setQuery(QueryBuilders.multiMatchQuery(key, "title","describe","content"))
				//设置分页
				.setFrom(start)
				.setSize(row)
				//高亮设置
				.addHighlightedField("title")
				.addHighlightedField("describe")
				.setHighlighterPreTags("<font color='red'>")
				.setHighlighterPostTags("</font>")
				//根据相关度排序
				.setExplain(true)
				.get();
		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		hashMap.put("count", totalHits);
		SearchHit[] hits2 = hits.getHits();
		
		ArrayList<Article> arrayList = new ArrayList<Article>();
		for (SearchHit searchHit : hits2) {
			Map<String, Object> source = searchHit.getSource();
			String id = searchHit.getId();
			String title = source.get("title").toString();
			String describe = source.get("describe").toString();
			String author = source.get("author").toString();
			Article article = new Article();
			article.setId(Integer.parseInt(id));
			article.setAuthor(author);
			article.setTitle(title);
			article.setDescribe(describe);
			
			//取出高亮内容，把原始内容替换
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			HighlightField highlightField = highlightFields.get("title");
			if(highlightField!=null){
				Text[] fragments = highlightField.getFragments();
				title = "";
				for (Text text : fragments) {
					title+=text;
				}
				if(StringUtils.isNotBlank(title)){
					article.setTitle(title);
				}
			}
			
			
			HighlightField highlightField2 = highlightFields.get("describe");
			if(highlightField2!=null){
				Text[] fragments2 = highlightField2.getFragments();
				describe  = "";
				for (Text text : fragments2) {
					describe+=text;
				}
				if(StringUtils.isNotBlank(describe)){
					article.setDescribe(describe);
				}
				arrayList.add(article);
			}
		}
		
		hashMap.put("dataList", arrayList);
		return hashMap;
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> search = search("hbase", "xxo", "article", 0, 10);
		System.out.println(search.size());
	}
	
}
