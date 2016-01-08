package com.s7soft.newsmaker.rss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.s7soft.newsmaker.bean.Rss;

public class RssManeger {

	private static final String RSS_URL = "http://news.yahoo.co.jp/pickup/rss.xml";

	public static List<Rss> getUrlList() {
		List<Rss> list = new ArrayList<Rss>();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(RSS_URL);

			// ドキュメントのルートを取得
			Element root = document.getDocumentElement();

			// ルート直下の"channel"に含まれるノードリストを取得
			NodeList channel = root.getElementsByTagName("channel");

			// "channel"直下の"title"に含まれるノードリストを取得
			NodeList title = ((Element) channel.item(0)).getElementsByTagName("title");

			// とりあえず出力する
			System.out.println("タイトル：" + title.item(0).getFirstChild().getNodeValue());

			// 各"item"とその中の"title"と<description>を取得する。
			NodeList item_list = root.getElementsByTagName("item");

			// item分ループする
			for (int i = 0; i < item_list.getLength(); i++) {

				Element element = (Element) item_list.item(i);

				// title を取得する
				NodeList item_title = element.getElementsByTagName("title");
				// link
				NodeList item_link = element.getElementsByTagName("link");

				// 出力する
				System.out.println(item_title.item(0).getFirstChild().getNodeValue());
				System.out.println(item_link.item(0).getFirstChild().getNodeValue());
				list.add(new Rss());
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static void main(String[] args) {
		getUrlList();
	}

}
