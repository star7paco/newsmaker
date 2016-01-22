package com.s7soft.newsmaker;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.s7soft.gae.newsmaker.bean.RssMaster;
import com.s7soft.gae.newsmaker.rss.RssManeger;
import com.s7soft.gae.newsmaker.translation.TranslationUtil;

public class NewsMakerTest {


	 @Test
	public void AssertJの基本的な使い方() {

		String actual = "test123";
		assertThat(actual, startsWith("te"));

	}

	 @Test
	 public void 通訳(){
		 try {
			String keyword = TranslationUtil.getChangeHtml("お疲れ様です。");

			assertThat(keyword, equalTo("수고하십니다. \n"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	 }

	 @Test
	 public void データ取得(){

		 try {
			 List<RssMaster> rssList = RssManeger.getUrlList("http://news.yahoo.co.jp/pickup/rss.xml");
			assertThat(rssList, notNullValue());
			assertThat(rssList.size(), is(8));

		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}
