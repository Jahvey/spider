package com.xxo.spider.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxo.spider.domain.Page;
import com.xxo.spider.utils.HtmlUtils;
import com.xxo.spider.utils.PageUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoxiaomo on 2016/5/3.
 */
public class ProcessJDMobileImpl implements Processable {
    public Page process( Page page ) {

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode rootNode = cleaner.clean(page.getContent());
        page.setProp( "title", HtmlUtils.getText(rootNode, "//*[@id=\"name\"]/h1"));
        page.setProp( "img", HtmlUtils.getAttributeByName(rootNode, "//*[@id=\"spec-n1\"]/img", "src"));
        page.setProp( "price" , getJDPrice(page) );

        try {
            JSONArray jsonArray = new JSONArray() ;
            JSONObject jsonObject = null ;
            Object[] objects = rootNode.evaluateXPath("//*[@id=\"product-detail-2\"]/table/tbody/tr");
            for (Object object : objects) {
                TagNode trNode = (TagNode) object;
                if( trNode != null && !"".equals(trNode.getText().toString().trim()) ){
                    jsonObject = new JSONObject() ;
                    //判断th出现的情况
                    Object[] thObj = trNode.evaluateXPath("/th");
                    if( thObj!=null && thObj.length>0 ){
                        //th
                        TagNode thNode = (TagNode) thObj[0];
                        jsonObject.put( "name", thNode.getText().toString()) ;
                        jsonObject.put( "value" , "") ;
                    }

                    //判断td出现的情况
                    Object[] tdObj = trNode.evaluateXPath("/td");
                    if( tdObj!=null && tdObj.length>0 ) {

                        TagNode tdNode0 = (TagNode) tdObj[0];
                        TagNode tdNode1 = (TagNode) tdObj[1];
                        jsonObject.put( "name" ,  tdNode0.getText().toString()) ;
                        jsonObject.put( "value" , tdNode1.getText().toString()) ;
                    }
                    jsonArray.add(jsonObject) ;
                }
            }

            page.setProp("property", jsonArray.toString());
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return page;
    }


    private String getJDPrice( Page page ) {
        String price = null ;
        String url = page.getUrl();
        Pattern compile = Pattern.compile("http://item.jd.com/([0-9]+).html");
        Matcher matcher = compile.matcher(url);
        String goodId = null;
        if( matcher.find() ){
            goodId = matcher.group(1) ;
            page.setProp("goodId" , "JD_" + goodId );
        }
        String content = PageUtils.getContentByURL("http://p.3.cn/prices/get?skuid=J_" + goodId);
        if( content != null && content.length() > 0 ){
            JSONArray jsonArray = JSON.parseArray(content);
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                price = jsonObject.getString("p");
            }
        }
        return price;
    }
}
