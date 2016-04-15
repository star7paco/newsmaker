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

}
