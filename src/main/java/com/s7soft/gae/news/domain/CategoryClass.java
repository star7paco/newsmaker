package com.s7soft.gae.news.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class CategoryClass {

	public static List<CategoryClass> getDefault(){
		List<CategoryClass> categorys = new ArrayList<CategoryClass>();

		CategoryClass category = new CategoryClass();
		category.setName("Yahoo top");
		category.setRssUrl("http://news.yahoo.co.jp/pickup/rss.xml");
		category.setUrl("http://www.yahoo.co.jp/");
		category.setStatus(1);
		category.setDate(new Date());
		categorys.add( category);

		CategoryClass category2 = new CategoryClass();
		category2.setStatus(1);
		category2.setName("AKB LOVE");
		category2.setUrl("http://akb.48lover.com/");
		category2.setRssUrl("http://akb.48lover.com/rss");
		category2.setDate(new Date());
		categorys.add( category2);

		CategoryClass category3 = new CategoryClass();
		category3.setStatus(1);
		category3.setName("일본 자동차 뉴스");
		category3.setUrl("http://carview.yahoo.co.jp/news/");
		category3.setRssUrl("http://headlines.yahoo.co.jp/rss/carv-bus.xml");
		category3.setDate(new Date());
		categorys.add( category3);

		CategoryClass category4 = new CategoryClass();
		category4.setStatus(1);
		category4.setName("일본IT뉴스");
		category4.setUrl("http://juggly.cn/");
		category4.setRssUrl("http://feeds.feedburner.com/Jugglycn");
		category4.setDate(new Date());
		categorys.add( category4);

		return categorys;
	}

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String url;
	private String rssUrl;

	private Date date;
	private int status;
	private int clickCount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public CategoryClass() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}

}
