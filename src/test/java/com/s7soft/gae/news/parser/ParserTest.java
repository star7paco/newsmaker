package com.s7soft.gae.news.parser;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.rss.RssReader;

public class ParserTest {



	@Test
	public void parsering() {

		TargetClass target = new TargetClass();
		target.setUrl("http://news.yahoo.co.jp/pickup/6195601");

		for(ParserClass parser: ParserClass.getDefault()){
			if(!target.getUrl().contains(parser.getKey())){
				continue;
			}
			TargetClass ret = Parser.parsing(target, parser);
			assertThat(ret.getUrl()).startsWith("http://h");
			System.out.println(ret.getUrl());
		}


		target.setUrl("http://headlines.yahoo.co.jp/videonews/nnn?a=20160325-00000006-nnn-soci");

		for(ParserClass parser: ParserClass.getDefault()){
			if(!target.getUrl().contains(parser.getKey())){
				continue;
			}
			TargetClass ret = Parser.parsing(target, parser);

			System.out.println(ret.getTitle());
			System.out.println(ret.getStringBody());
		}


		target.setUrl("http://headlines.yahoo.co.jp/hl?a=20160325-00000067-dal-base");

		for(ParserClass parser: ParserClass.getDefault()){
			if(!target.getUrl().contains(parser.getKey())){
				continue;
			}
			TargetClass ret = Parser.parsing(target, parser);

			System.out.println(ret.getTitle());
			System.out.println(ret.getStringBody());
		}

	}

	@Test
	public void rssAndParsing(){

		List<TargetClass> retList = new ArrayList<TargetClass>();

		CategoryClass category = new CategoryClass();
		category.setRssUrl("http://news.yahoo.co.jp/pickup/rss.xml");
		List<TargetClass> list = RssReader.readRss(category );
		for (TargetClass target : list) {
			for(ParserClass parser: ParserClass.getDefault()){
				if(!target.getUrl().contains(parser.getKey())){
					continue;
				}
				TargetClass ret = Parser.parsing(target, parser);
				System.out.println(ret.getUrl());
				retList.add(ret);
			}
		}

		System.out.println("----------------------------------------------------------------");

		for (TargetClass target : retList) {
			for(ParserClass parser: ParserClass.getDefault()){
				if(!target.getUrl().contains(parser.getKey())){
					continue;
				}
				TargetClass ret = Parser.parsing(target, parser);
				System.out.println(ret.getTitle());
				System.out.println(ret.getStringBody());
			}
		}
	}

}
