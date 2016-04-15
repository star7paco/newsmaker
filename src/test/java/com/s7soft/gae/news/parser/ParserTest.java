package com.s7soft.gae.news.parser;

import java.util.List;

import org.junit.Test;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.rss.RssReader;

public class ParserTest {

	String sample[] = {
			"http://zasshi.news.yahoo.co.jp/article?a=20160325-00010000-friday-ent",
			"http://headlines.yahoo.co.jp/videonews/jnn?a=20160325-00000036-jnn-soci",
			"http://headlines.yahoo.co.jp/hl?a=20160325-00000090-jij-pol",
			"",
			};

//	@Test
	public void parsering() {

		for (CategoryClass category : CategoryClass.getDefault()) {
			try {
				parsering(category);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

	}

	public void parsering(CategoryClass category ) throws Exception {
		List<TargetClass> list = RssReader.readRss(category);

		for (TargetClass target : list) {
			for(ParserClass parser: ParserClass.getDefault()){
				if(!target.getUrl().contains(parser.getKey())){
					continue;
				}


				TargetClass ret = Parser.parsing(target, parser);
				System.out.println(ret.getUrl());
				System.out.println(ret.getImgurl());

			}

		}
	}

	@Test
	public void testImg() {
		String postUrl = "http://news.yahoo.co.jp/pickup/6196184";

		String imageLink = Parser.getImage(postUrl);
		System.out.println(imageLink);

	}
}
