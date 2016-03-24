<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.TimeZone"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<br>
<%
// タイムゾーンの設定
TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo");

// フォーマッターのインスタンス化
DateFormat formatter = new SimpleDateFormat("yyyy");

// タイムゾーンをフォーマッターに適用する
formatter.setTimeZone(timezone);

// フォーマットする
formatter.format(new Date());
%>

Copyright 2001-<%=formatter.format(new Date())%> s7soft.com All Right Reserved.<br>
admin@s7soft.com
