package com.s7soft.gae.news.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.s7soft.gae.news.util.HtmlUtil;

@Entity
@Component
public class ParserClass{

	public static List<ParserClass> getDefault(){

		List<ParserClass> parsers = new ArrayList<ParserClass>();
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

		ParserClass parser3 = new ParserClass();
		parser3.key = "akb.48lover.com";
		parser3.name = "AKB48 Love";
		parser3.startTitle = HtmlUtil.convertSanitize("<h1 class=\"title\">");
		parser3.endTitle = HtmlUtil.convertSanitize("</h1>");
		parser3.startBody = HtmlUtil.convertSanitize("<div class=\"post-body\">");
		parser3.endBody = HtmlUtil.convertSanitize("</p>");
		parser3.status = 1;
		parser3.setDate(new Date());

		ParserClass parser4 = new ParserClass();
		parser4.key = "rdsig.yahoo.co.jp";
		parser4.name = "carview";
		parser4.titleTag = "hd";
		parser4.bodyTag = "ynDetailText";
		parser4.status = 1;
		parser4.setDate(new Date());


		ParserClass parser5 = new ParserClass();
		parser5.key = "juggly.cn";
		parser5.name = "juggly.cn";
		parser5.startTitle = HtmlUtil.convertSanitize("<h2>");
		parser5.endTitle = HtmlUtil.convertSanitize("</h2>");
		parser5.startBody = HtmlUtil.convertSanitize("<p>");
		parser5.endBody = HtmlUtil.convertSanitize("</p>");
		parser5.status = 1;
		parser5.setDate(new Date());

		parsers.add(parser0);
		parsers.add(parser1);
		parsers.add(parser2);
		parsers.add(parser3);
		parsers.add(parser4);
		parsers.add(parser5);
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


	private String startTitle;
	private String endTitle;

	private String startBody;
	private String endBody;

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
	public String getStartTitle() {
		return startTitle;
	}



	public void setStartTitle(String startTitle) {
		this.startTitle =  HtmlUtil.convertSanitize(startTitle);
	}
	public String getEndTitle() {
		return endTitle;
	}
	public void setEndTitle(String endTitle) {
		this.endTitle =  HtmlUtil.convertSanitize(endTitle);
	}
	public String getStartBody() {
		return startBody;
	}
	public void setStartBody(String startBody) {
		this.startBody =  HtmlUtil.convertSanitize(startBody);
	}
	public String getEndBody() {
		return endBody;
	}
	public void setEndBody(String endBody) {
		this.endBody =  HtmlUtil.convertSanitize(endBody);
	}



}
