package com.s7soft.gae.news.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {


	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:sss");
	static{
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
	}

	public static String format(Date date) {
		return sdf.format(date);
	}
}
