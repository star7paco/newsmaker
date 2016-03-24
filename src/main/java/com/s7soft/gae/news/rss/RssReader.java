package com.s7soft.gae.news.rss;

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

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.TargetClass;

public class RssReader {

	public static List<TargetClass> readRss(CategoryClass category) {
		return parseXML(category.getRssUrl());
	}

	private static List<TargetClass> parseXML(String path) {
		List<TargetClass> list = new ArrayList<TargetClass>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(path);
			Element root = document.getDocumentElement();

			/* Get and print Title of RSS Feed. */
//			NodeList channel = root.getElementsByTagName("channel");
			// NodeList nodeTitle =
			// ((Element)channel.item(0)).getElementsByTagName("title");
			// System.out.println("\nTitle: " +
			// nodeTitle.item(0).getFirstChild().getNodeValue() + "\n");

			/* Get Node list of RSS items */
			NodeList item_list = root.getElementsByTagName("item");
			for (int i = 0; i < item_list.getLength(); i++) {
				Element element = (Element) item_list.item(i);
				NodeList item_title = element.getElementsByTagName("title");
				NodeList item_link = element.getElementsByTagName("link");

				String title = item_title.item(0).getFirstChild()
						.getNodeValue();
				String link = item_link.item(0).getFirstChild().getNodeValue();

				TargetClass target = new TargetClass();
				target.setTitle(title);
				target.setUrl(link);
				list.add(target);

				// System.out.println(" title: " + title);
				// System.out.println(" link:  " + link + "\n");
			}
		} catch (IOException e) {
			System.out.println("IO Exception");
		} catch (ParserConfigurationException e) {
			System.out.println("Parser Configuration Exception");
		} catch (SAXException e) {
			System.out.println("SAX Exception");
		}
		return list;
	}
}
