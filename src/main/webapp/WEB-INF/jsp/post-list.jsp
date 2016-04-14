<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
<%= AdSense.AutoTop %>

<hr>
<div class="bs-example">
<c:forEach var="post" items="${postList.content}">
<div class="media">
  <div class="media-top">
   <a href="/post/${post.id}">
     ${post.imgTag}
   </a>
  </div>
  <div class="media-body">
   <a href="/post/${post.id}">
    <h4 class="media-heading">${post.stringTitle}</h4>
    ${post.dateTime} <span class="badge">${post.clickCount}</span>
    </a>
  </div>
</div>
<hr>
</c:forEach>
</div>

<nav>
 <ul class="pager">
  <c:choose>
   <c:when test="${page == 0}">
    <li class="previous disabled"><a href="#"><span aria-hidden="true">&larr;</span>Newer</a></li></c:when>
   <c:otherwise>
    <li class="previous"><a href="/post-list/${page-1}"><span aria-hidden="true">&larr;</span>Newer</a></li></c:otherwise>
  </c:choose>
  <li class="page-no"> ${page}</li>
  <li class="next"><a href="/post-list/${page+1}">Older<span aria-hidden="true">&rarr;</span></a></li>
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

