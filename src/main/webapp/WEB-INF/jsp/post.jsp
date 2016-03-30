<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>news</title>
<%= AdSense.MbAdtop %>
<main>
<article>
 <div class="url"><a href="${post.url}">org page</a></div>
 <div class="title" style="font-size: 16px;">${post.stringTitle}</div>
</article>
<article>
 <div class="img">${post.imgTag}</div>
 <div class="body"><p>${post.stringBody}</p></div>
</article>

<%
if( AdminUtil.isAdminUser() ){
%>
<hr>

<article>
 <div class="orgtitle">${post.originalTitle}</div>
 <div class="orgbody"><p>${post.stringOriginalBody}</p></div>
</article>
<hr>
<%
}
%>


<article>
 <a href="/post-list/${page}">back</a>
</article>
</main>
<%= AdSense.MbAdfin %>
