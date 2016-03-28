<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>news</title>
<%= AdSense.MbAdtop %>
<h3>news</h3>
<c:forEach var="post" items="${postList}">
<a href="post/${post.id}">${post.title}</a> <br>
</c:forEach>

<ul class="pager">
    <li class="previous">
        <a href="/post-list/${page-1}">&larr; Newer Posts</a>
    </li>
    <li class="next">
        <a href="/post-list/${page+1}">Older Posts &rarr;</a>
    </li>
</ul>

<%= AdSense.MbAdfin %>
<a href="/">戻る</a>
