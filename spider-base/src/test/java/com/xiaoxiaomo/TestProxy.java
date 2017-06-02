package com.xiaoxiaomo;

import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xiaoxiaomo on 2015/5/5.
 */
public class TestProxy {

    @Test
    public void testProxy() throws IOException {
        HttpClientBuilder builder = HttpClients.custom();

        //浏览器标识
        builder.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36") ;


        CloseableHttpClient client = builder.build();

        HttpGet httpGet = new HttpGet("http://dangdang.com");
        CloseableHttpResponse response = client.execute(httpGet);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
