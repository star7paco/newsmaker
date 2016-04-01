<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%= AdSense.MbAdtop %>
<div class="container">
<div class="panel panel-default">
 <div class="panel-heading"><h1>${post.stringTitle}</h1></div>
  <div class="panel-body">
    ${post.imgTag}
    <p>${post.stringBody}</p>
  </div>
</div>

<hr>

<%
if( AdminUtil.isAdminUser() ){
%>


<div class="panel panel-default">
 <div class="panel-heading"><h1>${post.originalTitle}</h1></div>
  <div class="panel-body">
    <p>${post.stringOriginalBody}</p>
  </div>
</div>


<%
}
%>

<nav>
  <ul class="pager">
    <li><a href="/post-list/${page}">back</a></li>
  </ul>
</nav>
<%= AdSense.MbAdfin %>
</div>