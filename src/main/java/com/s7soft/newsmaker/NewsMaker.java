package com.s7soft.newsmaker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.s7soft.newsmaker.translation.TranslationUtil;

public class NewsMaker {



	public static void main(String[] args) {



//		try {
//			Document doc = Jsoup.connect("http://en.wikipedia.org/").get(); //웹에서 내용을 가져온다.
//			Elements contents = doc.select("#mp-itn b a");
//			String text = contents.text(); //String 형태로 바꾸어 준다.
//			System.out.println(text);
//		} catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
//			e.printStackTrace();
//		}

		try {
			Document document = Jsoup.connect("http://news.yahoo.co.jp/pickup/6186856").get();
			System.out.println(document.body().getElementsByClass("newsTitle").text());

			Elements elements = document.body().getElementsByClass("newsTitle");
			for (Node node : elements.get(0).childNodes()) {
				System.out.println(node.outerHtml());
//				if(!node.outerHtml().trim().toLowerCase().startsWith("<")){
//					System.out.println(node.outerHtml());
//				}

				String link = node.absUrl("href");
				System.out.println(TranslationUtil.getChangeHtml(getNewsBody(link)));



				for (Node childNode : node.childNodes()) {
					if(childNode.outerHtml().indexOf("<a") > 0){
						childNode.attributes();
					}
				}

			}

		} catch (Exception e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
			e.printStackTrace();
		}

	}



	public static String getNewsBody(String newsUrl){

		try {
			Document document = Jsoup.connect(newsUrl).get();

			String body = document.body().getElementsByClass("ynDetailText").text();

			return body;

		} catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
			e.printStackTrace();
		}
		return "";

	}


}
