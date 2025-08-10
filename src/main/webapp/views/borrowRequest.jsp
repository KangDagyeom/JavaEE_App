<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Yêu cầu mượn sách của tôi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h3 class="mb-4">Yêu cầu mượn sách của bạn</h3>

    <c:if test="${empty borrowRequests}">
        <div class="alert alert-info">Bạn chưa gửi yêu cầu mượn sách nào.</div>
    </c:if>

    <c:if test="${not empty borrowRequests}">
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Tên sách</th>
                <th>Tác giả</th>
                <th>Ngày yêu cầu</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="req" items="${borrowRequests}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${req.book.title}</td>
                    <td>${req.book.author}</td>
                    <td>${req.requestDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${req.status == 'PENDING'}">
                                <span class="badge bg-warning text-dark">Đang chờ</span>
                            </c:when>
                            <c:when test="${req.status == 'APPROVED'}">
                                <span class="badge bg-success">Đã duyệt</span>
                            </c:when>
                            <c:when test="${req.status == 'REJECTED'}">
                                <span class="badge bg-danger">Từ chối</span>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

</body>
</html>
