package com.xxo.spider.web.index;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxo.spider.web.domain.Goods;
import com.xxo.spider.web.utils.HbaseUtils;
import com.xxo.spider.web.utils.SleepUtils;
import com.xxo.spider.web.utils.SolrUtil;
import com.xxo.spider.web.utils.RedisUtils;

/**
 * 注意：
 * 这个功能需要单独部署，它会在后台一直运行，发现新增数据就立刻建立索引
 * 
 * 
 * 建立商品索引，
 * 从redis中查询需要建索引的商品ID
 * 然后查询hbase，建立索引
 * 如果建立索引的时候出现异常，则把当前商品id重新放入索引表中
 *
 */
public class SolrIndex{
	static final Logger logger = LoggerFactory.getLogger(SolrIndex.class);
	private static final String SOLR_INDEX = "solr_index";
	static RedisUtils redis = new RedisUtils();
	/**
	 * 建立索引
	 */
	public static void doIndex(){
		String goodsId = "";
		try {
			HbaseUtils hbaseUtils = new HbaseUtils();
			//从redis队列中获取待索引的商品id
			goodsId = redis.poll(SOLR_INDEX);
			while (!Thread.currentThread().isInterrupted()) {
				if(StringUtils.isNotBlank(goodsId)){
					//根据商品id到hbase中取数据的详细信息
					Goods goods = hbaseUtils.get(HbaseUtils.TABLE_NAME, goodsId);
					if(goods==null){
						logger.error("id为{}的商品索引建立失败!原因：没有在hbase数据库中查询到数据", goodsId);
					}else{
						if(goodsId.endsWith("_jd")){
							goods.setFrom("京东");
						}else if(goodsId.endsWith("_yx")){
							goods.setFrom("易迅");
						}else{
							goods.setFrom("未知");
						}
						//使用solr建立索引
						SolrUtil.addIndex(goods);
					}
					goodsId = redis.poll(SOLR_INDEX);
				}else{
					System.out.println("暂时没有需要索引的数据,休息一会");
					SleepUtils.sleep(5000);
				}
			}
		} catch (Exception e) {
			logger.error("id为{}的商品索引建立失败!{}", goodsId, e);
			redis.add(SOLR_INDEX, goodsId);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		doIndex();
	}
}
