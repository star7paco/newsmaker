<%@page import="com.s7soft.gae.news.adsense.AdSense"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
<%= AdSense.PostTop %>
<div class="panel panel-default">
 <div class="panel-heading">${post.stringTitle}</div>
 <div class="panel-body">
   [${category.name}] ${post.dateTime} <span class="badge">${post.clickCount}</span>
   <p>${post.stringBody}</p>
   <br>
   <a href="${post.url}">원문보기</a>
 </div>
</div>



<div class="row">
<c:forEach var="post" items="${hotpost}">
  <div class="col-sm-6 col-md-4">
    <div class="thumbnail">
    ${post.imgTag}
    <div class="caption">
     <a href="/post/${post.id}">
     ${post.stringTitle}
     </a>
    </div>
    </div>
  </div>
</c:forEach>
</div>





<hr>

<%
if( AdminUtil.isAdminUser() ){
%>
<div class="panel panel-default">
 <div class="panel-heading"><h1>${post.originalTitle}</h1></div>
  <div class="panel-body">
    <p>${post.stringOriginalBody}</p>
    <br>
    <form action="/admin/post-update" >
     <input type="hidden" name="id" value="${post.id}">
     status:<input type="text" name="status" value="${post.status}">
     <input type="submit">
    </form>
  </div>
</div>
<%
}
%>
<%= AdSense.PostEnd %>
<nav>
  <ul class="pager">
    <li><a href="/post-list?p=${page}">back</a></li>
  </ul>
</nav>
</div>
