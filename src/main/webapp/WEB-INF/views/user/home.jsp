<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>사용자 홈</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>신신서가</h2>
        <!-- 로그아웃 버튼 -->
        <form action="${ctx}/logout" method="post" style="margin:0;">
            <button type="submit" class="btn btn-outline-secondary">로그아웃</button>
        </form>
    </div>
    <%-- NavTabs --%>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link ${activeTab=='books' ? 'active' : ''}" href="${ctx}/user/books?tab=books">도서목록</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='wish' ? 'active' : ''}" href="${ctx}/user/books?tab=wish">희망도서 신청</a>
        </li>
    </ul>

    <%-- Tab Content --%>
    <div class="mt-3">
        <c:choose>
            <%-- 도서목록 탭 --%>
            <c:when test="${activeTab=='books'}">
                <%@ include file="books.jsp" %>
            </c:when>
            <%-- 희망도서 신청 탭 --%>
            <c:when test="${activeTab=='wish'}">
                <%@ include file="wishForm.jsp" %>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>
