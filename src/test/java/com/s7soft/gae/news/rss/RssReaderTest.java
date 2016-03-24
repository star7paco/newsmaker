package com.s7soft.gae.news.rss;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.TargetClass;

public class RssReaderTest {

	@Test
	public void test(){

		CategoryClass category = new CategoryClass();
		category.setRssUrl("http://news.yahoo.co.jp/pickup/rss.xml");
		List<TargetClass> list = RssReader.readRss(category );

		assertThat(list).hasSize(8);

		for(TargetClass link : list){
			assertThat(link.getUrl()).isNotEmpty();
		}
	}
}
