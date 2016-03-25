package com.s7soft.gae.news.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ParserTest {



	@Test
	public void parsering() {
		Document doc = null;
		try {
			doc = Jsoup.connect(getNewsLink("http://news.yahoo.co.jp/pickup/6195601")).get();
//			System.out.println(doc);

			Elements element1 = doc.getElementsByClass("article");
			System.out.println(element1.text());

			Element element2 = doc.getElementById("ynDetailText");
			System.out.println(element2.text());

		} catch (IOException e) {
		} catch (Exception e) {
			Elements element1 = doc.getElementsByClass("yjXL");
			System.out.println(element1.text());

			Elements element2 = doc.getElementsByClass("ymuiContainerNopad");
			System.out.println(element2.text());
		}
	}


	public String getNewsLink(String rssUrl) {
		try {
			Document page = Jsoup.connect(rssUrl).get();

			Elements elements = page.getElementsByClass("newslink");
			String newslink = elements.get(0).attr("href");
			if(newslink == null || newslink.trim().length() < 1){
				return rssUrl;
			}
			return newslink;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rssUrl;
	}
}
