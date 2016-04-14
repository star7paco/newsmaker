<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>POST-InsertTitleMessage</title>
<h3>Target 리스트</h3>
<c:set var="pageUrl" value="/admin/target-list/" />

<c:forEach var="target" items="${targetList.content}">
 url : ${target.url} ,  title : ${target.title} , status : ${target.status} ,  body : ${target.stringBody} <br>
</c:forEach>


<form action="${pageUrl}${page}" >
<table border="1">
 <tr><td>status :</td><td> <input type="text" name="status" value="${status}"></td></tr>
 </table>
<input type="submit">
</form>


<ul class="pager">
    <li class="previous">
        <a href="${pageUrl}${page-1}">&larr; Newer Posts</a>
    </li>
    <li class="next">
        <a href="${pageUrl}${page+1}">Older Posts &rarr;</a>
    </li>
</ul>
<nav>
 <ul class="pager">
  <c:choose>
   <c:when test="${page == 0}">
    <li class="previous disabled"><a href="#"><span aria-hidden="true">&larr;</span>Newer</a></li></c:when>
   <c:otherwise>
    <li class="previous"><a href="${pageUrl}${page-1}?status=${status}"><span aria-hidden="true">&larr;</span>Newer</a></li></c:otherwise>
  </c:choose>
  <li class="page-no"> ${page}</li>
  <li class="next"><a href="${pageUrl}${page+1}?status=${status}">Older<span aria-hidden="true">&rarr;</span></a></li>
 </ul>
</nav>



<a href="/">戻る</a>
