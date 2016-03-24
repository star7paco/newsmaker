<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.s7soft.gae.news.admin.AdminUtil"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%
if (UserServiceFactory.getUserService().getCurrentUser() != null) { %>
ようこそ! あなたは <b><%=UserServiceFactory.getUserService().getCurrentUser() .getNickname()%></b>
という名前でログインしています。<a href='<%=UserServiceFactory.getUserService().createLogoutURL("/")%>'>サインアウト</a><br>
<%
	if( AdminUtil.isAdminUser() ){
		%><a href='/admin'>admin</a><%
	}

} else {%>
こんにちは! こちらから<a href='<%=UserServiceFactory.getUserService().createLoginURL("/")%>'>サインイン</a> してください!
<%}%>
<br>
