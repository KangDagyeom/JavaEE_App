<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 8/11/2025
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1>Sửa sách </h1>
<form method="post">
    <input type="number" name="id" value="${b.id}" hidden>

    <div class="mb-3">
        <label for="title" class="form-label">Tiêu đề</label>
        <input type="text" class="form-control" id="title" name="title"
               required value="${b.title}">
    </div>
    <div class="mb-3">
        <label for="author" class="form-label">Tác giả</label>
        <input type="text" class="form-control" id="author" name="author"
               required value="${b.author}">
    </div>
    <div class="mb-3">
        <label for="quantity" class="form-label">Số lượng</label>
        <input type="number" class="form-control" id="quantity" name="quantity" min="0"
               required value="${b.quantity}">
    </div>

    <button type="submit" class="btn btn-primary">Lưu</button>

</form>
</body>
</html>
