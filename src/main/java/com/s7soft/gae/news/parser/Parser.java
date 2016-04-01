package com.s7soft.gae.news.parser;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
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

	public static TargetClass parsing(TargetClass input, ParserClass parser) {
		TargetClass target = new TargetClass();
		target.setId(input.getId());
		target.setBody(input.getBody());
		target.setCategoryId(input.getCategoryId());
		target.setDate(input.getDate());
		target.setImgurl(input.getImgurl());
		target.setStatus(input.getStatus());
		target.setTitle(input.getTitle());
		target.setUrl(input.getUrl());

		if (parser.getNewsLinkTag() != null
				&& !parser.getNewsLinkTag().isEmpty()) {
			String imgurl = getImage(target);

			target.setUrl(newsLinkChange(target, parser.getNewsLinkTag()));
			target.setStatus(1);

			target.setImgurl(imgurl);
			return target;
		}
		return getNews(target, parser);

	}

	public static String newsLinkChange(TargetClass target, String key) {

		try {
			Document doc = Jsoup.connect(target.getUrl()).get();

			Elements elements = doc.getElementsByClass(key);

			return elements.get(0).attr("href");
		} catch (Exception e) {
		}
		return "";
	}

	public static TargetClass getNews(TargetClass target, ParserClass parser) {
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


			target.setTitle(title);
			target.setBody(new Text(body));

			if( title == null || title.length() == 0 || body == null || body.length() == 0){
				target.setStatus(4);
			}else{
				target.setStatus(2);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
	}
	public static String getImage(TargetClass target) {
		return getImage(target.getUrl());
	}

	public static String getImage(String url) {

		try {
			Document doc = Jsoup.connect(url).get();

			Elements elements = doc.getElementsByClass("image");

			Node node = elements.get(0).childNode(0);

			return node.attr("data-src");

		} catch (Exception e) {
		}

		return "";
	}

}
