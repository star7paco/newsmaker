package com.s7soft.gae.news.parser;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.rss.RssReader;

public class ParserTest2 {

	String sample[] = {
			"http://zasshi.news.yahoo.co.jp/article?a=20160325-00010000-friday-ent",
			"http://headlines.yahoo.co.jp/videonews/jnn?a=20160325-00000036-jnn-soci",
			"http://headlines.yahoo.co.jp/hl?a=20160325-00000090-jij-pol",
			"",
			};

	public void parsering() {
		CategoryClass category = new CategoryClass();
		category.setName("야후일본 톱 뉴스");
		category.setUrl("http://www.yahoo.co.jp/");
		category.setRssUrl("http://news.yahoo.co.jp/pickup/rss.xml");
//		category.setNewsLinkTag("newslink");
//		category.setTitleTag("hd");
//		category.setNewsTag("ynDetailText");
//
//		category.setTitleTagSub("yjXL");
//		category.setNewsTagSub("ymuiContainerNopad");

		parsering(category);
	}

	public void parsering(CategoryClass category ) {
		Document doc = null;

		List<TargetClass> list = RssReader.readRss(category);

		for (TargetClass target : list) {

			try {
				String url = getNewsLink(category, target);


				if(!url.contains("headlines.yahoo.co.jp") && !url.contains("zasshi.news.yahoo.co.jp")){
					continue;
				}

				System.out.println(url);
				doc = Jsoup.connect(getNewsLink(category, target)).get();
	//			System.out.println(doc);

//				Elements element1 = doc.getElementsByClass(category.getTitleTag());
//				System.out.println("MAIN TITLE1: " + element1.get(0).childNode(1).toString());
//
//				Elements element2 = doc.getElementsByClass(category.getNewsTag());
//				System.out.println("MAIN BODY: " + element2.text());
//
//				Elements element3 = doc.getElementsByClass(category.getTitleTagSub());
//				System.out.println("SUB TITLE: " + element3.text());
//
//				Elements element4 = doc.getElementsByClass(category.getNewsTagSub());
//				System.out.println("SUB BODY: " + element4.text());

			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (Exception e) {
			}
		}
	}


	public String getNewsLink(CategoryClass category , TargetClass targetClass) {

//		if( category.getNewsLinkTag() == null || category.getNewsLinkTag().isEmpty() ){
//			return category.getRssUrl();
//		}
//
//		try {
//			Document page = Jsoup.connect(targetClass.getUrl()).get();
//
//			Elements elements = page.getElementsByClass(category.getNewsLinkTag());
//			String newslink = elements.get(0).attr("href");
//			if(newslink == null || newslink.trim().length() < 1){
//				return category.getRssUrl();
//			}
//			return newslink;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return category.getRssUrl();
	}
}
