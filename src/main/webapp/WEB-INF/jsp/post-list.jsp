<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>news</title>
<%= AdSense.MbAdtop %>
<h3>news (${page})</h3>
<table>
<c:forEach var="post" items="${postList.content}">
<tr>
 <td>${post.dateTime}</td>
 <td><a href="/post/${post.id}?page=${page}">${post.title}</a></td>
 <td>${post.clickCount}</td>
</tr>
</c:forEach>
</table>
<%= AdSense.MbAdfin %>
<div>
<table border="1" width="95%">
 <tr>
  <td>
       <a href="/post-list/${page-1}">&larr; ${page-1} page</a>
  </td>
  <td>
       <a href="/post-list/${page+1}">${page+1} page &rarr;</a>
  </td>
 </tr>
</table>
</div>

