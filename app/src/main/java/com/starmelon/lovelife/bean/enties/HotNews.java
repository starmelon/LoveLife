package com.starmelon.lovelife.bean.enties;


import java.io.Serializable;

public class HotNews implements Serializable{

	private int id;
	private String title;// 资讯标题
	private int topclass;// 一级分类
	private String img;// 图片
	private String description;// 描述
	private String keywords;// 关键字
	private String message;// 资讯内容
	private int count;// 访问次数
	private int fcount;// 收藏数
	private int rcount;// 评论读数
	private String fromname;
	private String fromurl;
	private String  time;

	public HotNews() {
		super();
	}

	//region 属性访问器

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTopclass() {
		return topclass;
	}

	public void setTopclass(int topclass) {
		this.topclass = topclass;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getFcount() {
		return fcount;
	}

	public void setFcount(int fcount) {
		this.fcount = fcount;
	}

	public int getRcount() {
		return rcount;
	}

	public void setRcount(int rcount) {
		this.rcount = rcount;
	}

	public String getFromname() {
		return fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public String getFromurl() {
		return fromurl;
	}

	public void setFromurl(String fromurl) {
		this.fromurl = fromurl;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	//endregion


}
