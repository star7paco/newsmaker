<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%= AdSense.MbAdtop %>
<h3>news (${page})</h3>
<div align="center">
<main>
<article>
<c:forEach var="post" items="${postList.content}">
<p><a href="/post/${post.id}?page=${page}">${post.imgTag}${post.stringTitle}</a><i style="font-size: 10px;">${post.dateTime}(${post.clickCount})</i></p>
<br><br>
</c:forEach>
</article>

<%= AdSense.MbAdfin %>
</div>
<div>
<table border="0" width="95%">
 <tr>
  <td align="center">
   <c:if test="${page == 0}">
    &larr; 0 page
   </c:if>
   <c:if test="${page != 0}">
    <a href="/post-list/${page-1}">&larr; ${page-1} page</a>
   </c:if>
  </td>
  <td align="center">
       <a href="/post-list/${page+1}">${page+1} page &rarr;</a>
  </td>
 </tr>
</table>
</div>
</main>

