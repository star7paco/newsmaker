package com.s7soft.newsmaker;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class NewsMakerTest {


	 @Test
	public void AssertJの基本的な使い方() {

		String actual = "愛媛 蜜柑";
		assertThat(actual, startsWith("愛媛"));

	}

}
