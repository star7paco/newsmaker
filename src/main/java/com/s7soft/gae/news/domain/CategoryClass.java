package com.s7soft.gae.news.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class CategoryClass {

	public static CategoryClass getDefault(){
		CategoryClass category = new CategoryClass();
		category.setName("Yahoo top");
		category.setRssUrl("http://news.yahoo.co.jp/pickup/rss.xml");
		category.setUrl("http://www.yahoo.co.jp/");
		category.setStatus(1);
		category.setDate(new Date());
		return category;
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
