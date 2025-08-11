<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách Yêu cầu mượn sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4 text-center">📋 Danh sách Yêu cầu mượn sách</h2>

    <table class="table table-bordered table-striped table-hover align-middle">
        <thead class="table-dark text-center">
        <tr>
            <th>#</th>
            <th>Tên Sinh viên</th>
            <th>Tên Sách</th>
            <th>Ngày gửi</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty borrowRequests}">
                <c:forEach var="req" items="${borrowRequests}" varStatus="status">
                    <tr>
                        <td class="text-center">${req.id}</td>
                        <td>${req.user.name}</td>
                        <td>${req.book.title}</td>
                        <td>${req.requestDate}</td>
                        <td class="text-center">
  <span class="badge
    ${req.status == 'PENDING' ? 'bg-warning text-dark' :
      (req.status == 'APPROVED' ? 'bg-success' :
        (req.status == 'REJECTED' ? 'bg-danger' : 'bg-secondary'))}">
          ${req.status}
  </span>
                        </td>

                        <td>
                            <form action="/th04416/admin/update_request" method="post" class="d-inline">
                                <input type="hidden" name="id" value="${req.id}">
                                <input type="hidden" name="action" value="APPROVED">
                                <button type="submit" class="btn btn-primary btn-sm">Duyệt</button>
                            </form>
                            <form action="/th04416/admin/update_request" method="post" class="d-inline ms-1">
                                <input type="hidden" name="id" value="${req.id}">
                                <input type="hidden" name="action" value="REJECTED">
                                <button type="submit" class="btn btn-danger btn-sm">Từ chối</button>
                            </form>
                        </td>

                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-center text-muted">Chưa có yêu cầu mượn sách nào.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <a href="/th04416/dashboard" class="btn btn-primary">Quay lại Dashboard</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
