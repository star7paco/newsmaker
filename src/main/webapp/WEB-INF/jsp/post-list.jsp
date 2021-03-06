<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@page import="java.util.Map"%>
<%@page import="com.s7soft.gae.news.util.CommanUtil"%>
<%@page import="com.s7soft.gae.news.domain.CategoryClass"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <c:choose>
   <c:when test="${category != null}">
    <c:set var="pageUrl" value="/${cookie.lang.value}/post-list?c=${category.id}&p=" />
   </c:when>
   <c:otherwise>
    <c:set var="pageUrl" value="/${cookie.lang.value}/post-list?p=" />
   </c:otherwise>
  </c:choose>

<div class="container">
<%= AdSense.ListTop %>
<hr>

<div class="bs-example">
<c:forEach var="post" items="${postList.content}">
<div class="media">
  <div class="media-left col-xs-4">
   <a href="/post/${post.id}">
     ${post.listImgTag}
   </a>
  </div>
  <div class="media-body">
   <a href="/post/${post.id}"><h4 class="media-heading">${post.stringTitle}</h4></a>
   <a href="/${cookie.lang.value}/post-list?c=${post.categoryId}">&lt;${post.categoryName}&gt;</a>${post.dateTime} <span class="badge">${post.clickCount}</span>
  </div>
</div>
<hr>
</c:forEach>
</div>
<%= AdSense.ListEnd %>
<nav>
 <ul class="pager">
  <c:choose>
   <c:when test="${page == 0}">
    <li class="previous disabled"><a href="#"><span aria-hidden="true">&larr;</span>Newer</a></li></c:when>
   <c:otherwise>
    <li class="previous"><a href="${pageUrl}${page-1}"><span aria-hidden="true">&larr;</span>Newer</a></li></c:otherwise>
  </c:choose>
  <li class="page-no"> ${page} / ${postList.size} </li>
  <li class="next"><a href="${pageUrl}${page+1}">Older<span aria-hidden="true">&rarr;</span></a></li>
 </ul>
</nav>
<div class=".col-xs-12 .col-md-8">
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
</div>

</div>

