package com.xxo.spider.store;

import com.alibaba.fastjson.JSONObject;
import com.xxo.spider.domain.Page;
import com.xxo.spider.utils.HbaseUtils;
import com.xxo.spider.utils.RedisUtils;

/**
 * HBase 存储
 * Created by xiaoxiaomo on 2016/6/21.
 */
public class HBaseStoreImpl implements Storeable{

    HbaseUtils hbaseUtils =  new HbaseUtils();
    RedisUtils redisUtils =  new RedisUtils();
    @Override
    public void store(Page page) {
        String url = page.getUrl();

        JSONObject prop = page.getProp();
        String title = prop.getString("title");
        String img = prop.getString("img");
        String price = prop.getString("price");
        String property = prop.getString("property");
        String goodsId = prop.getString("goodId") ;
        try{
            //hbase数据入库
            hbaseUtils.put( HbaseUtils.TABLE_NAME , goodsId , HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_TITLE, title );
            hbaseUtils.put( HbaseUtils.TABLE_NAME , goodsId , HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PRICE, price );
            hbaseUtils.put( HbaseUtils.TABLE_NAME , goodsId , HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PIC_URL, img );
            hbaseUtils.put( HbaseUtils.TABLE_NAME , goodsId , HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_DATA_URL, url );
            hbaseUtils.put( HbaseUtils.TABLE_NAME , goodsId , HbaseUtils.COLUMNFAMILY_2, HbaseUtils.COLUMNFAMILY_2_PARAM, property );

            //把数据的rowkey添加到redis的solr_index队列中
            redisUtils.add("solr_index", goodsId);
        }catch ( Exception e ){
            e.printStackTrace();
        }

    }
}
