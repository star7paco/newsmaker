<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>parser-InsertTitleMessage</title>
<h3>parser 리스트[${parsercount}]</h3>

<c:if test="${parserlist != null}" >
<table border="1">
<tr>
  <th>id</th>
  <th>key</th>
  <th>name</th>
  <th>newsLinkTag</th>
  <th>titleTag</th>
  <th>bodyTag</th>

  <th>startTitle</th>
  <th>endTitle</th>
  <th>startBody</th>
  <th>endBody</th>

  <th>closing</th>
  <th>date</th>
  <th>status</th>
</tr>
<c:forEach var="parser" items="${parserlist}">
<tr>
  <td><a href="/admin/parser-list/${parser.id}/">${parser.id}</a></td>
  <td>${parser.key}</td>
  <td>${parser.name}</td>
  <td>${parser.newsLinkTag}</td>
  <td>${parser.titleTag}</td>
  <td>${parser.bodyTag}</td>

  <td>${parser.startTitle}</td>
  <td>${parser.endTitle}</td>
  <td>${parser.startBody}</td>
  <td>${parser.endBody}</td>

  <td>${parser.closing}</td>
  <td>${parser.date}</td>
  <td>${parser.status}</td>
</tr>
</c:forEach>
</table>
</c:if>


<c:if test="${oneparser == null}" var="flg" />
<c:if test="${flg}" >
<h3>new</h3>
<form action="/admin/parser-add" >
<table border="1">
 <tr><td>key :</td><td> <input type="text" name="key"></td></tr>
 <tr><td>name :</td><td> <input type="text" name="name" ></td></tr>
 <tr><td>newsLinkTag :</td><td> <input type="text" name="newsLinkTag"></td></tr>
 <tr><td>titleTag :</td><td> <input type="text" name="titleTag"></td></tr>
 <tr><td>bodyTag :</td><td> <input type="text" name="bodyTag"></td></tr>

 <tr><td>startTitle :</td><td> <input type="text" name="startTitle"></td></tr>
 <tr><td>endTitle :</td><td> <input type="text" name="endTitle"></td></tr>
 <tr><td>startBody :</td><td> <input type="text" name="startBody"></td></tr>
 <tr><td>endBody :</td><td> <input type="text" name="endBody"></td></tr>

 <tr><td>closing :</td><td> <input type="text" name="closing"></td></tr>
 <tr><td>status :</td><td> <input type="text" name="status"></td></tr>
 </table>
<input type="submit">
</form>
</c:if>

<c:if test="${!flg}" >
<h3>update</h3>
<form action="/admin/parser-update" >
<table border="1">
 <tr><td>id :         </td><td> ${parser.id}<input type="hidden" name="id"  value="${oneparser.id}"> </td></tr>
 <tr><td>key :        </td><td> <input type="text" name="key"  value="${oneparser.key}"></td></tr>
 <tr><td>name :       </td><td> <input type="text" name="name" value="${oneparser.name}"></td></tr>
 <tr><td>newsLinkTag :</td><td> <input type="text" name="newsLinkTag"  value="${oneparser.newsLinkTag}"></td></tr>
 <tr><td>titleTag :   </td><td> <input type="text" name="titleTag"  value="${oneparser.titleTag}"></td></tr>
 <tr><td>bodyTag :    </td><td> <input type="text" name="bodyTag"  value="${oneparser.bodyTag}"></td></tr>

 <tr><td>startTitle :    </td><td> <input type="text" name="bodyTag"  value="${oneparser.startTitle}"></td></tr>
 <tr><td>endTitle :    </td><td> <input type="text" name="endTitle"  value="${oneparser.endTitle}"></td></tr>
 <tr><td>startBody :    </td><td> <input type="text" name="startBody"  value="${oneparser.startBody}"></td></tr>
 <tr><td>endBody :    </td><td> <input type="text" name="endBody"  value="${oneparser.endBody}"></td></tr>

 <tr><td>closing :    </td><td> <input type="text" name="closing"  value="${oneparser.closing}"></td></tr>
 <tr><td>status :     </td><td> <input type="text" name="status"  value="${oneparser.status}"></td></tr>
 </table>
<input type="submit">
</form>
</c:if>

<a href="/">戻る</a>