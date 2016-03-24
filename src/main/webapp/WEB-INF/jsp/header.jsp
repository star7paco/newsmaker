<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.s7soft.gae.news.admin.AdminUtil"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%
UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	String msg;
	String admin = "";
	if (user != null) {
		msg = "ようこそ! あなたは <b>" + user.getNickname()
				+ "</b> という名前でログインしています。" + " <a href='"
				+ userService.createLogoutURL("/") + "'>サインアウト</a><br>";

		if( AdminUtil.isAdminUser() ){
			admin = "<a href='/admin'>admin</a>";
		}

	} else {
		msg = "こんにちは! こちらから " + "<a href='"
				+ userService.createLoginURL("/")
				+ "'>サインイン</a> してください!";
	}
%>
<%=msg + admin%><br>
