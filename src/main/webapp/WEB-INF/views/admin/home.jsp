<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지 - 신신서가</title>
    <!-- Bootstrap CDN CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
</head>
<body>
<nav>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link ${activeTab=='books' ? 'active' : ''}"
               href="${ctx}/admin/books">도서관리</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='members' ? 'active' : ''}"
               href="${ctx}/admin/members">회원관리</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='categories' ? 'active' : ''}"
               href="${ctx}/admin/categories">카테고리관리</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='wishBooks' ? 'active' : ''}"
               href="${ctx}/admin/wish-books">희망도서관리</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='reservations' ? 'active' : ''}"
               href="${ctx}/admin/reservations">예약중인 도서</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='rentals' ? 'active' : ''}"
               href="${ctx}/admin/rentals">대출목록</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='stats' ? 'active' : ''}"
               href="${ctx}/admin/stats">통계</a>
        </li>
    </ul>
</nav>

<div class="container mt-4">
    <c:choose>
        <c:when test="${activeTab=='books'}">
            <jsp:include page="books.jsp"/>
        </c:when>
        <c:when test="${activeTab=='members'}">
            <jsp:include page="members.jsp"/>
        </c:when>
        <c:when test="${activeTab=='categories'}">
            <jsp:include page="categories.jsp"/>
        </c:when>
        <c:when test="${activeTab=='wishBooks'}">
            <jsp:include page="wishBooks.jsp"/>
        </c:when>
        <c:when test="${activeTab=='reservations'}">
            <jsp:include page="reservations.jsp"/>
        </c:when>
        <c:when test="${activeTab=='rentals'}">
            <jsp:include page="rentals.jsp"/>
        </c:when>
        <c:when test="${activeTab=='stats'}">
            <jsp:include page="stats.jsp"/>
        </c:when>
    </c:choose>
</div>

<!-- Bootstrap CDN JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
</body>
</html>