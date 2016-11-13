package com.starmelon.lovelife.bean;

import java.io.Serializable;

public class NewsDetail implements Serializable{
	public String title;// 资讯标题
	public int topclass;// 一级分类
	public String img;// 图片
	public String description;// 描述
	public String keywords;// 关键字
	public String message;// 资讯内容
	public int count;// 访问次数
	public int fcount;// 收藏数
	public int rcount;// 评论读数
	public String fromname; //信息来源
	public String fromurl;
	public long time;

	@Override
	public String toString() {
		return "NewsDetail [title=" + title + ", topclass=" + topclass
				+ ", img=" + img + ", description=" + description
				+ ", keywords=" + keywords + ", message=" + message
				+ ", count=" + count + ", fcount=" + fcount + ", rcount="
				+ rcount + ", fromname=" + fromname + ", fromurl=" + fromurl
				+ ", time=" + time + "]";
	}

}
