<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>POST-InsertTitleMessage</title>
<h3>유저 리스트</h3>
<c:forEach var="target" items="${targetList}">
 url : ${target.url} ,  title : ${target.title} , status : ${target.status} ,  body : ${target.stringBody} <br>
</c:forEach>


<ul class="pager">
    <li class="previous">
        <a href="/admin/target-list/${page-1}">&larr; Newer Posts</a>
    </li>
    <li class="next">
        <a href="/admin/target-list/${page+1}">Older Posts &rarr;</a>
    </li>
</ul>



<a href="/">戻る</a>
