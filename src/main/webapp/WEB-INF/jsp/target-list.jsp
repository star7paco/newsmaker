<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>POST-InsertTitleMessage</title>
<h3>Target 리스트 (${count}) </h3>
<c:set var="pageUrl" value="/admin/target-list/" />


<form action="${pageUrl}${page}" >
<table border="1">
 <tr><td>status :</td><td> <input type="text" name="status" value="${status}"></td></tr>
 </table>
<input type="submit">
</form>

<table border="1">
<c:forEach var="target" items="${targetList.content}">
<tr>
  <td colspan="2">${target.id}</td>
</tr>
<tr>
  <td>status</td>
  <td>
   <form action="/admin/target-add" >
   <input type="hidden" name="id" value="${target.id}">
   <input type="text" name="status" value="${target.status}">
   <input type="submit">
   </form>
  </td>
</tr>
<tr>
  <td>url</td><td>${target.url}</td>
</tr>
<tr>
  <td>date</td><td>${target.dateTime}</td>
</tr>
<tr>
  <td>title</td><td>${target.title}</td>
</tr>
<tr>
  <td>body</td><td>${target.stringBody}</td>
</tr>
</c:forEach>
</table>






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
