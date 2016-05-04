package com.xxo.spider.repository;

/**
 * URL资源
 * Created by xiaoxiaomo on 2016/5/4.
 */
public interface Repository {

    String poll() ;

    void add( String url ) ;

    void addHeigh(String url) ;
}
