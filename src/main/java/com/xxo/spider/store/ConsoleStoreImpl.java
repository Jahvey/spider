package com.xxo.spider.store;

import com.xxo.spider.domain.Page;

/**
 * Created by xiaoxiaomo on 2016/4/29.
 */
public class ConsoleStoreImpl implements Storeable {
    public void store(Page page) {

        System.out.println(page.getUrl() +" -- "+ page.getProp().get("price"));
    }
}
