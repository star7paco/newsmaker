package com.s7soft.gae.news.util;

import javax.servlet.http.HttpServletRequest;

public class CommanUtil {


	public static boolean isMobile(HttpServletRequest request){
    	String ua = request.getHeader("user-agent");
    	if (ua.indexOf("iPhone") > 0 || (ua.indexOf("Android") > 0) && (ua.indexOf("Mobile") > 0) || ua.indexOf("Windows Phone") > 0) {
    		return true;
    	}
        return false;
	}

	public static String getLoction(HttpServletRequest request){

		//① ブラウザの言語設定が取得できた場合
		if (request.getLocales() != null) {
		    // とある処理を実行
		    // ・・・・

		//② ブラウザの言語設定が取得できない（未設定）の場合
		} else {
		    // とある処理を実行
		    // ・・・・
		}
		return "ko";
	}


}
