<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>POST-InsertTitleMessage</title>
<h3>유저 리스트</h3>
<c:forEach var="target" items="${targetlist}">
 url : ${target.url} ,  title : ${target.title} , status : ${target.status} ,  body : ${target.body} <br>
</c:forEach>

<a href="/">戻る</a>
