<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.s7soft.gae.news.admin.AdminUtil"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>

<head>
 <meta charset="utf-8">
<title>${title}</title>
<meta name=”description=” content="${title}">
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-3871213-13', 'auto');
  ga('send', 'pageview');

</script>
<style type="text/css">
p {color:blue; line-height:1.5;}
h1 {font-size: 14pt;font-weight: normal;}
</style>
<!-- Bootstrap core CSS -->
<link href="/css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>


<div class="container">
<div class="header clearfix">
  <nav>
    <ul class="nav nav-pills pull-right">

<%
if (UserServiceFactory.getUserService().getCurrentUser() != null) { %>
<li role="presentation"><b><%=UserServiceFactory.getUserService().getCurrentUser() .getNickname()%></b>님 환영합니다.</li>
<li role="presentation"><a href='<%=UserServiceFactory.getUserService().createLogoutURL("/")%>'>Logout</a></li>
<%
	if( AdminUtil.isAdminUser() ){
		%><li role="presentation"><a href='/admin'>admin</a></li><%
	}

} else {%>
<li role="presentation">환영합니다. </li>
<li role="presentation"><a href='<%=UserServiceFactory.getUserService().createLoginURL("/")%>'>Login</a></li>
<%}%>

    </ul>
  </nav>
  <h3 class="text-muted">News</h3>
</div>

</div>




