package com.s7soft.gae.newsmaker.bean;

public class RssSeed {
	int id;
	String url;
	int rssId;
	String memo;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getRssId() {
		return rssId;
	}
	public void setRssId(int rssId) {
		this.rssId = rssId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
