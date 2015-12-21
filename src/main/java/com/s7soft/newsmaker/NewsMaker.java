package com.s7soft.newsmaker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NewsMaker {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		try {
			Document doc = Jsoup.connect("http://en.wikipedia.org/").get(); //웹에서 내용을 가져온다.
			Elements contents = doc.select("#mp-itn b a");
			String text = contents.text(); //String 형태로 바꾸어 준다.
			System.out.println(text);
		} catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
			e.printStackTrace();
		}

		try {
			Document document = Jsoup.connect("http://www.google.co.jp").get();
			System.out.println(document.html());
		} catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
			e.printStackTrace();
		}

	}

}
