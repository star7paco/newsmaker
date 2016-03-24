<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>POST-InsertTitleMessage</title>
<h3>유저 리스트</h3>
<c:forEach var="post" items="${postlist}">
 post-name : ${post.title} ,  post-message : ${post.body} <br>
</c:forEach>

<h3>새로운 유저와 메시지</h3>
<form action="/post-add">
 tatle : <input type="text" name="title"><br>
 body : <input type="text" name="body"><br> <input type="submit">
</form>

<a href="/">戻る</a>
