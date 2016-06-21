package com.xxo.spider.store;

import com.xxo.spider.domain.Page;
import org.slf4j.LoggerFactory;

/**
 * 控制台
 * Created by xiaoxiaomo on 2016/4/29.
 */
public class ConsoleStoreImpl implements Storeable {


    private org.slf4j.Logger logger = LoggerFactory.getLogger(ConsoleStoreImpl.class);
    public void store(Page page) {

        logger.info(" 存储数据 {} -- {}", page.getUrl(), page.getProp().get("price"));
    }
}
