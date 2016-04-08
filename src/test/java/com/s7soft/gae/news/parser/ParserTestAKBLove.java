package com.s7soft.gae.news.parser;

import java.util.List;

import org.junit.Test;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.rss.RssReader;

public class ParserTestAKBLove {

	String sample[] = {
			"http://zasshi.news.yahoo.co.jp/article?a=20160325-00010000-friday-ent",
			"http://headlines.yahoo.co.jp/videonews/jnn?a=20160325-00000036-jnn-soci",
			"http://headlines.yahoo.co.jp/hl?a=20160325-00000090-jij-pol",
			"",
			};

	@Test
	public void parsering() {
		CategoryClass category = new CategoryClass();
		category.setName("AKB LOVE");
		category.setUrl("http://akb.48lover.com/");
		category.setRssUrl("http://akb.48lover.com/rss");

		parsering(category);
	}

	public void parsering(CategoryClass category ) {
		List<TargetClass> list = RssReader.readRss(category);

		for (TargetClass target : list) {
			for(ParserClass parser: ParserClass.getDefault()){
				if(!target.getUrl().contains(parser.getKey())){
					continue;
				}

				TargetClass ret = Parser.parsing(target, parser);
				System.out.println(ret.getUrl());
				System.out.println(ret.getImgurl());
				System.out.println(ret.getVideourl());
				System.out.println(ret.getTitle());
				System.out.println(ret.getStringBody());

				String imageLink = Parser.getImage(target);
				System.out.println(imageLink);

			}

		}
	}

	@Test
	public void testImg() {
		String postUrl = "http://akb.48lover.com/log/29059";

		String imageLink = Parser.getImage(postUrl);
		System.out.println(imageLink);

	}



	@Test
	public void testdata() {
		String postUrl = "http://akb.48lover.com/log/29105";
		TargetClass target = new TargetClass();
		target.setUrl(postUrl);

		for(ParserClass parser: ParserClass.getDefault()){
			if(!target.getUrl().contains(parser.getKey())){
				continue;
			}

			TargetClass ret = Parser.parsing(target, parser);
			System.out.println(ret.getUrl());
			System.out.println(ret.getImgurl());
			System.out.println(ret.getVideourl());
			System.out.println(ret.getTitle());
			System.out.println(ret.getStringBody());

			String imageLink = Parser.getImage(target);
			System.out.println(imageLink);

		}

	}
}
