package com.s7soft.gae.news.parser;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;

import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.util.HtmlUtil;

public class Parser {
	public static final Object MemcacheServiceKey = "Parser_MemcacheServiceKey";
	public static final Object MemcacheServiceTimeKey = "Parser_MemcacheServiceTime_Key";


	public static List<TargetClass> parsing(List<TargetClass> targetList, List<ParserClass> parserList) {
		return targetList;
	}

	public static TargetClass parsing(TargetClass input, ParserClass parser) throws Exception {
		TargetClass target = new TargetClass();
		BeanUtils.copyProperties(input, target);

		if (parser.getNewsLinkTag() != null
				&& !parser.getNewsLinkTag().isEmpty()) {
			String imgurl = getImage(target);

			target.setUrl(newsLinkChange(target, parser.getNewsLinkTag()));
			target.setStatus(1);

			target.addImgurl(imgurl);
			return target;
		}
		return getNews(target, parser);

	}


	public static TargetClass newsLinkChange(TargetClass input, ParserClass parser) {
		TargetClass target = new TargetClass();
		target.setBody(input.getBody());
		target.setCategoryId(input.getCategoryId());
		target.setDate(input.getDate());
		target.setImgurl(input.getImgurl());
		target.setStatus(input.getStatus());
		target.setTitle(input.getTitle());
		target.setUrl(input.getUrl());

		String imgurl = getImage(target);

		target.setUrl(newsLinkChange(target, parser.getNewsLinkTag()));
		target.setStatus(1);

		target.addImgurl(imgurl);

		return target;
	}

	private static TargetClass getNews(TargetClass target, ParserClass parser) throws Exception {

		if(parser == null){
			return target;
		}

		if( parser.getStartTitle() != null && !parser.getStartTitle().isEmpty() ){
			return getNewsByToEnd(target, parser);
		}else{
			return getNewsByClassName(target, parser);
		}

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

	private static TargetClass getNewsByToEnd(TargetClass target, ParserClass parser) throws Exception {
		try {
			Document doc = Jsoup.connect(target.getUrl()).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();

			try {
				String keywords = doc.select("meta[name=keywords]").first().attr("content");
				target.setKeywords(keywords);
			} catch (Exception e) {
			}

			String html = doc.getAllElements().html();

			//<h1 class="title">[動画] HKT48 なこみく＆めるみお「アインシュタインよりディアナ・アグロン」MV（Short ver.）公開！</h1>

			String title = getText(html, parser.getStartTitle(), parser.getEndTitle());
			String body = getText(html, parser.getStartBody(), parser.getEndBody());
			getMedia(target, getHtml(html, parser.getStartBody(), parser.getEndBody()));


			target.setTitle(title);
			target.addBody(body);

			if( title == null || title.length() == 0 || body == null || body.length() == 0){
				target.setStatus(4);
			}else{
				target.setStatus(2);
			}

		} catch (IOException e) {
			System.out.println(target.getUrl());
			e.printStackTrace();
			throw e;
		}
		return target;
	}

	private static TargetClass getNewsByClassName(TargetClass target, ParserClass parser) throws Exception {
		try {
			Document doc = Jsoup.connect(target.getUrl()).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();

			String keywords = doc.select("meta[name=keywords]").first().attr("content");
			target.setKeywords(keywords);

			String title = target.getTitle();
			String body = "";
			try {
				title = doc.getElementsByClass(parser.getTitleTag()).text();
			} catch (Exception e) {
			}
			try {
				Elements elements = doc.getElementsByClass(parser.getBodyTag());
				body =  elements.text();
				getMedia(target, elements.html());
			} catch (Exception e) {
			}

			// TODO 特別対応を無くしたい
			if( target.getUrl().contains("headlines.yahoo.co.jp/video") ){
				title =  doc.getElementsByClass("yjXL").text();
				Elements elements = doc.getElementsByClass("ymuiContainerNopad");
				body =  elements.text();
				getMedia(target, elements.html());

			}else {
				try {
					title =  doc.getElementsByClass(parser.getTitleTag()).get(0).childNode(1).toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			title = title.replaceAll("<.+?>", "");
			body = body.replaceAll("<.+?>", "");

			target.setTitle(title);
			target.addBody(body);

			if( title == null || title.length() == 0 || body == null || body.length() == 0){
				target.setStatus(4);
			}else{
				target.setStatus(2);
			}


		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return target;
	}
	public static String getImage(TargetClass target) {
		return getImage(target.getUrl(), "image");
	}

	public static String getImage(TargetClass target, String key) {
		return getImage(target.getUrl(), key);
	}

	public static String getImage(String url) {
		return getImage(url, "image");
	}

	public static String getImage(String url , String key) {

		try {
			Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();

			Elements elements = doc.getElementsByClass(key);

			Node node = elements.get(0).childNode(0);

			return node.attr("data-src");

		} catch (Exception e) {
		}

		return "";
	}


	public static String getText(String txt ,String startKey, String endKey) {

		if(txt == null || txt.isEmpty() ){
			return "";
		}

		startKey = HtmlUtil.convertUnsanitize(startKey);
		endKey = HtmlUtil.convertUnsanitize(endKey);

		int start = txt.indexOf(startKey) + startKey.length();
		int end = start + txt.substring(start).indexOf(endKey);

		return txt.substring(start, end).replaceAll("<.+?>", "");
	}

	public static String getHtml(String txt ,String startKey, String endKey) {

		if(txt == null || txt.isEmpty() ){
			return "";
		}

		startKey = HtmlUtil.convertUnsanitize(startKey);
		endKey = HtmlUtil.convertUnsanitize(endKey);

		int start = txt.indexOf(startKey) + startKey.length();
		int end = start + txt.substring(start).indexOf(endKey);

		return txt.substring(start, end);
	}

	public static void getMedia(TargetClass target ,String html){
		// youtubeのlinkを取得
		Pattern p = Pattern.compile("<\\s*iframe.*src\\s*=\\s*([\\\"'])?([^ \\\"']*)[^>]*>");
		Matcher m = p.matcher(html);
		if (m.find()) {
			String src = m.group(2);//ここにURLが入る
			if( src.contains("//www.youtube") ){
				System.out.println(src);
				target.addVideourl(src);
			}
		}


		// 画像のlinkを取得 + 動画の場合は動画を追加する。
		Pattern imgP = Pattern.compile("<\\s*img.*src\\s*=\\s*([\\\"'])?([^ \\\"']*)[^>]*>");
		Matcher imgM = imgP.matcher(html);
		if (imgM.find()) {
			String src = imgM.group(2);//ここにURLが入る
			if( src.contains("//www.youtube") ){
				System.out.println(src);
				target.addVideourl(src);
			}else{
				target.addImgurl(src);
			}
		}
	}
}
