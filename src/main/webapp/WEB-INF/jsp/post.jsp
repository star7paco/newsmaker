<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>news</title>
<%= AdSense.MbAdtop %>
<div class="url"><a href="${post.url}">org page</a></div>
 <div class="title">${post.title}</div>
 <div class="body">${post.stringBody} </div>
<br>
<a href="/post-list">back</a>
<%= AdSense.MbAdfin %>

