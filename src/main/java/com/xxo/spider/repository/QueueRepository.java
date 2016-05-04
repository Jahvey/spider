package com.xxo.spider.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 普通队列（不具有优先级）
 * Created by xiaoxiaomo on 2016/5/4.
 */
public class QueueRepository implements Repository {

    Queue<String> queue = new ConcurrentLinkedDeque<String>() ;

    public String poll() {
        return queue.poll();
    }

    public void add(String url) {
        queue.add(url) ;
    }

    public void addHeigh(String url) {
        this.add(url);
    }
}
