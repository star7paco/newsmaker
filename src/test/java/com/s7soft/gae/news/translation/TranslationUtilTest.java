package com.s7soft.gae.news.translation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.parser.Parser;

public class TranslationUtilTest  {

	 @Test
	public void testGetChangeHtml(){
		String ko = "";
		try {
			 ko = TranslationUtil.getChangeHtml("G大量新人 寮のおきてに異変");

			 assertThat("G대량신인기숙사의 규정에 이변").isEqualTo(ko.trim());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void 長い本文を通訳(){
		String url = "http://headlines.yahoo.co.jp/hl?a=20160413-00000322-oric-ent";
		TargetClass target = new TargetClass();
		target.setUrl(url);

		for(ParserClass parser: ParserClass.getDefault()){
			if(!target.getUrl().contains(parser.getKey())){
				continue;
			}

			target = Parser.parsing(target, parser);
			try {
				TranslationUtil.trans(target);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}



	}
}
