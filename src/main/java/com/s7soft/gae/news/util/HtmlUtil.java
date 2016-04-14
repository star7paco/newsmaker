package com.s7soft.gae.news.util;

public class HtmlUtil {

	 /**
     * タグを無害化します。
     * @param str
     * @return
     */
	public static String convertSanitize( String str ) {
        if(str==null) {
            return str;
        }
        str = str.replaceAll("&" , "&amp;" );
        str = str.replaceAll("<" , "&lt;"  );
        str = str.replaceAll(">" , "&gt;"  );
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("'" , "&#39;" );
        return str;
     }

    /**
     * 無害化されたタグを元に戻します
     * @param str
     * @return
     */
	public static String convertUnsanitize( String str ) {
        if(str==null) {
            return str;
        }
        str = str.replaceAll("&#39;" , "'" );
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&gt;"  , ">" );
        str = str.replaceAll("&lt;"  , "<" );
        str = str.replaceAll("&amp;" , "&" );
        return str;
     }

	/**
     * タグを削除します
     * @param str
     * @return
     */
	public static String delTag( String str ) {
        if(str==null) {
            return str;
        }
        return str.replaceAll("<.+?>", "").replaceAll("\n", "");
     }

}
