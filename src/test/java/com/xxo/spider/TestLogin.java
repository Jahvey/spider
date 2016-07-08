package com.xxo.spider;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxiaomo on 2015/5/5.
 */
public class TestLogin {

    @Test
    public void loginSVN() throws IOException, URISyntaxException {

        //1.
        HttpClientBuilder build = HttpClients.custom();

        CloseableHttpClient client = build.build();


        HttpPost post = new HttpPost("http://svn.jundie.net/user/login") ;
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>() ;

        parameters.add( new BasicNameValuePair("uid" ,"xiaoxiaomo")) ;
        parameters.add( new BasicNameValuePair("pwd", "www.xiaoxiaomo.cn")) ;

        HttpEntity entity = new UrlEncodedFormEntity(parameters) ;
        post.setEntity(entity); //设置参数


        CloseableHttpResponse response = client.execute(post);

        //获取状态码，如果是302就跳转页面
        int code = response.getStatusLine().getStatusCode();
        if( code == 302 ){
            //获取headers里面的字段location（里面带有URI）
            Header[] locals = response.getHeaders("location");

            //从新设置URI
            post.setURI(new URI("http://svn.jundie.net/"+locals[0].getValue().toString()));

            //执行
            response = client.execute(post);
        }

        System.out.println(EntityUtils.toString(response.getEntity()));

    }
}
