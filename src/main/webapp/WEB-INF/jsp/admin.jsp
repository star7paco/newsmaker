<%@ page contentType="text/html; charset=UTF-8" %>
<title>Admin Page</title>

<h3>Admin Page</h3>
<table>
<tr><td>categoryCount</td><td>[${categoryCount}]</td></tr>
<tr><td>parserCount</td><td>[${parserCount}]</td></tr>
<tr><td>postCount</td><td>[${postCount}]</td></tr>
<tr><td>targetCount</td><td>[${targetCount}]</td></tr>
</table>

<a href="/post-list">post-list</a><br><br>
<br><br><br><br>
<a href="/admin/category-list">category-list</a><br><br>
<a href="/admin/target-list">target-list</a><br><br>
<a href="/admin/parser-list">parser-list</a><br><br>
<br><br><br><br>
-RUN-<br>
<a class="btn btn-primary btn-lg" role="button" href="/cron/rss-read">/rss-read</a><br><br>
<a class="btn btn-primary btn-lg" role="button" href="/cron/post-maker">post-maker</a><br><br>
<a class="btn btn-primary btn-lg" role="button" href="/cron/trans">trans</a><br><br>


