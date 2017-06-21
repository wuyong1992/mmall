<%--
  Created by IntelliJ IDEA.
  User: wuyong
  Date: 2017/6/21
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
</head>
<body>
<h2>Hello World!</h2>

springMVC上传测试
<form action="/manage/product/upload.do" name="file1" enctype="multipart/form-data" method="post">
    <input type="file" name="upload_file">
    <input type="submit" value="上传">
</form>

<hr>

富文本上传测试
<form action="/manage/product/richtext_img_upload.do" name="file2" enctype="multipart/form-data" method="post">
    <input type="file" name="upload_file">
    <input type="submit" value="上传">
</form>
</body>
</html>
