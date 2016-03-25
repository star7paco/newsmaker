package com.s7soft.gae.news.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class ParserClass implements Serializable{

	public static ParserClass[] getDefault(){
		ParserClass[] parsers = new ParserClass[3];
		ParserClass parser0 = new ParserClass();
		parser0.key = "news.yahoo.co.jp/pickup";
		parser0.name = "yahoo next page";
		parser0.newsLinkTag = "newslink";
		parser0.status = 1;
		parser0.setDate(new Date());

		ParserClass parser1 = new ParserClass();
		parser1.key = "headlines.yahoo.co.jp";
		parser1.name = "yahoo top news";
		parser1.titleTag = "hd";
		parser1.bodyTag = "ynDetailText";
		parser1.status = 1;
		parser1.setDate(new Date());

		ParserClass parser2 = new ParserClass();
		parser2.key = "zasshi.news.yahoo.co.jp";
		parser2.name = "yahoo top news";
		parser2.titleTag = "hd";
		parser2.bodyTag = "ynDetailText";
		parser2.status = 1;
		parser2.setDate(new Date());

		parsers[0] = parser0;
		parsers[1] = parser1;
		parsers[2] = parser2;
		return parsers;
	}

	@Id
	@GeneratedValue
	private Long id;

	private String key;
	private String name;

	private String newsLinkTag;
	private String titleTag;
	private String bodyTag;

	// 締め（処理最後に行う作業）
	private String closing;

	private Date date;
	private int status;



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNewsLinkTag() {
		return newsLinkTag;
	}
	public void setNewsLinkTag(String newsLinkTag) {
		this.newsLinkTag = newsLinkTag;
	}
	public String getTitleTag() {
		return titleTag;
	}
	public void setTitleTag(String titleTag) {
		this.titleTag = titleTag;
	}
	public String getBodyTag() {
		return bodyTag;
	}
	public void setBodyTag(String bodyTag) {
		this.bodyTag = bodyTag;
	}
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
	public String getClosing() {
		return closing;
	}
	public void setClosing(String closing) {
		this.closing = closing;
	}



}
