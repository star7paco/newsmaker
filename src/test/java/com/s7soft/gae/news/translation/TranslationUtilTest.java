package com.s7soft.gae.news.translation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

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
}
