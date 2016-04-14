<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.s7soft.gae.news.admin.AdminUtil"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>


<head>
<title>${title}</title>
<meta charset="UTF-8" />
<meta name="description" content="${title}">
<meta name="viewport" content="width=device-width, initial-scale=1">



  <c:choose>
   <c:when test="${keywords != null}">
     <meta name="keywords" content="일본,뉴스,${keywords}">
    </c:when>
   <c:otherwise>
     <meta name="keywords" content="일본,뉴스">
    </c:otherwise>
  </c:choose>


<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-3871213-13', 'auto');
  ga('send', 'pageview');

</script>
<!-- Bootstrap core CSS -->
<link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
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
  <h3 class="text-muted"><a href="/">일본 뉴스</a></h3>
</div>

</div>




