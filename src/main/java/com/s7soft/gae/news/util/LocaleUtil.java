package com.s7soft.gae.news.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class LocaleUtil {

	public static String getLoction(HttpServletRequest request){

		Locale locale = request.getLocale();

		//① ブラウザの言語設定が取得できた場合
		if (locale != null) {
		    // とある処理を実行
		    // ・・・・
			if(isKorean(locale)){
				return "ko";
			}else if(isJapanese(locale)){
				return "ja";
			}else if(isEnglish(locale)){
				return "en";
			}
		}

		//② ブラウザの言語設定が取得できない（未設定）の場合
		return "ko";
	}


	public static boolean isKorean(Locale locale){
		if( Locale.KOREAN.getLanguage().equals(locale.getLanguage())){
			return true;
		}
		return false;
	}

	public static boolean isJapanese(Locale locale){
		if( Locale.JAPANESE.getLanguage().equals(locale.getLanguage())){
			return true;
		}
		return false;
	}
	public static boolean isEnglish(Locale locale){
		if( Locale.ENGLISH.getLanguage().equals(locale.getLanguage())){
			return true;
		}
		return false;
	}

}
