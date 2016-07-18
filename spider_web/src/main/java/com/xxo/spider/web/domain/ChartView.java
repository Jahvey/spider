package com.xxo.spider.web.domain;

public abstract class ChartView {
	public String text;//主标题
	
	/**
	 * 
	 * @param text 主标题
	 */
	public void setTitle(String text){
		this.text = text;
	}
	public String getText() {
		return text;
	}
}
