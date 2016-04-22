<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>category-InsertTitleMessage</title>
<h3>category 리스트[${categorycount}]</h3>

<div class="container">
<table border="1">
<tr>
  <th>id</th>
  <th>name</th>
  <th>url</th>
  <th>rssUrl</th>
  <th>location</th>
  <th>date</th>
  <th>status</th>
  <th>clickCount</th>
</tr>
<c:forEach var="category" items="${categorylist}">
<tr>
  <td><a href="/admin/category-list/${category.id}/">${category.id}</a></td>
  <td>${category.name}</td>
  <td>${category.url}</td>
  <td>${category.rssUrl}</td>
  <td>${category.location}</td>
  <td>${category.date}</td>
  <td>${category.status}</td>
  <td>${category.clickCount}</td>
</tr>
</c:forEach>
</table>




<c:if test="${onecategory == null}" var="flg" />
<c:if test="${flg}" >
<h3>new</h3>
<form action="/admin/category-add" >
<table border="1">
 <tr><td>name :</td><td> <input type="text" name="name" ></td></tr>
 <tr><td>url :</td><td> <input type="text" name="url"></td></tr>
 <tr><td>rssUrl :</td><td> <input type="text" name="rssUrl"></td></tr>
 <tr><td>location :</td><td> <input type="text" name="location"></td></tr>
 <tr><td>status :</td><td> <input type="text" name="status"></td></tr>
 </table>
<input type="submit">
</form>
</c:if>

<c:if test="${!flg}" >
<h3>update</h3>
<form action="/admin/category-update" >
<table border="1">
 <tr><td>id :</td><td> ${onecategory.id} <input type="hidden" name="id" value="${onecategory.id}"></td></tr>
 <tr><td>name :</td><td> <input type="text" name="name" value="${onecategory.name}"></td></tr>
 <tr><td>url :</td><td> <input type="text" name="url"  value="${onecategory.url}"></td></tr>
 <tr><td>rssUrl :</td><td> <input type="text" name="rssUrl"  value="${onecategory.rssUrl}"></td></tr>
 <tr><td>location :</td><td> <input type="text" name="location"  value="${onecategory.location}"></td></tr>
 <tr><td>status :</td><td> <input type="text" name="status"  value="${onecategory.status}"></td></tr>
 </table>
<input type="submit">
</form>
</c:if>

<a href="/">戻る</a>
</div>