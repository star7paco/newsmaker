<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>category-InsertTitleMessage</title>
<h3>category 리스트[${categorycount}]</h3>
<table>
<tr>
  <th>id</th>
  <th>name</th>
  <th>url</th>
  <th>rssUrl</th>
  <th>tag</th>
  <th>status</th>
</tr>
<c:forEach var="category" items="${categorylist}">
<tr>
  <td>${category.id}</td>
  <td>${category.name}</td>
  <td>${category.url}</td>
  <td>${category.rssUrl}</td>
  <td>${category.tag}</td>
  <td>${category.status}</td>
</tr>
</c:forEach>
</table>



<h3>새로운 유저와 메시지</h3>
<form action="/admin/category-add">
	name : <input type="text" name="name"><br>
	url : <input type="text" name="url"><br>
	rssUrl : <input type="text" name="rssUrl"><br>
	tag : <input type="text" name="tag"><br>
	status : <input type="text" name="status"><br>
	<input type="submit">
</form>


<a href="/">戻る</a>