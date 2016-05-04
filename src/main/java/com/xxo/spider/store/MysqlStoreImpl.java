package com.xxo.spider.store;

import com.alibaba.fastjson.JSONObject;
import com.xxo.spider.domain.Page;
import com.xxo.spider.utils.MyDateUtils;
import com.xxo.spider.utils.MyDbUtils;

import java.util.Date;

/**
 *
 * Created by xiaoxiaomo on 2016/4/29.
 */
public class MysqlStoreImpl implements Storeable {
    public void store(Page page) {
        JSONObject prop = page.getProp();

        String title = prop.getString("title");
        String img = prop.getString("img");
        String price = prop.getString("price");
        String property = prop.getString("property");
        Date date = new Date() ;
        String date2 = MyDateUtils.formatDate2(date);

        MyDbUtils.update(MyDbUtils.INSERT_LOG ,
                prop.getString("goodId"),page.getUrl(), img , title ,price ,property ,date2);

    }
}
