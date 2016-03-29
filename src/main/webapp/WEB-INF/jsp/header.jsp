<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.s7soft.gae.news.admin.AdminUtil"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<style type="text/css">
p {color:blue; line-height:1.5;}
h1 {font-size: 14pt;font-weight: normal;}
</style>
<%
if (UserServiceFactory.getUserService().getCurrentUser() != null) { %>
ようこそ! あなたは <b><%=UserServiceFactory.getUserService().getCurrentUser() .getNickname()%></b>
という名前でログインしています。<a href='<%=UserServiceFactory.getUserService().createLogoutURL("/")%>'>サインアウト</a>
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
<!-- google Site in Search -->
<script>
  (function() {
    var cx = '009245048745248643837:8xh5ivi5mhi';
    var gcse = document.createElement('script');
    gcse.type = 'text/javascript';
    gcse.async = true;
    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
        '//cse.google.com/cse.js?cx=' + cx;
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(gcse, s);
  })();
</script>
<gcse:search></gcse:search>

