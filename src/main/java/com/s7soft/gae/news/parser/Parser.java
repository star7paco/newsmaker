package com.s7soft.gae.news.parser;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.appengine.api.datastore.Text;
import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.TargetClass;

public class Parser {
	public static final Object MemcacheServiceKey = "Parser_MemcacheServiceKey";
	public static final Object MemcacheServiceTimeKey = "Parser_MemcacheServiceTime_Key";


	public static List<TargetClass> parsing(List<TargetClass> targetList, List<ParserClass> parserList) {
		return targetList;
	}

	public static TargetClass parsing(TargetClass target, ParserClass parser) {
		TargetClass newTarget = new TargetClass();
		if (parser.getNewsLinkTag() != null
				&& !parser.getNewsLinkTag().isEmpty()) {
			newTarget.setId(target.getId());
			newTarget.setCategoryId(target.getCategoryId());
			newTarget.setTitle(target.getTitle());
			newTarget.setUrl(newsLinkChange(target, parser.getNewsLinkTag()));
			newTarget.setStatus(1);
			newTarget.setDate(new Date());
			return newTarget;
		}
		return getNews(target, parser);

	}

	public static String newsLinkChange(TargetClass target, String key) {

		try {
			Document doc = Jsoup.connect(target.getUrl()).get();

			Elements elements = doc.getElementsByClass(key);

			return elements.get(0).attr("href");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static TargetClass getNews(TargetClass target, ParserClass parser) {
		TargetClass newTarget = new TargetClass();
		try {
			Document doc = Jsoup.connect(target.getUrl()).get();

			String title = doc.getElementsByClass(parser.getTitleTag()).text();
			String body =  doc.getElementsByClass(parser.getBodyTag()).text();

			// TODO 特別対応を無くしたい
			if( target.getUrl().contains("headlines.yahoo.co.jp/video") ){
				title =  doc.getElementsByClass("yjXL").text();
				body =  doc.getElementsByClass("ymuiContainerNopad").text();

			}else {
				try {
					title =  doc.getElementsByClass(parser.getTitleTag()).get(0).childNode(1).toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}


			newTarget.setId(target.getId());
			newTarget.setCategoryId(target.getCategoryId());
			newTarget.setUrl(target.getUrl());
			newTarget.setTitle(title);
			newTarget.setBody(new Text(body));
			newTarget.setStatus(2);
			newTarget.setDate(new Date());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return newTarget;
	}
}
