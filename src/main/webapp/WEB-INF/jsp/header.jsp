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
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-3871213-13', 'auto');
  ga('send', 'pageview');

</script>
<br>
