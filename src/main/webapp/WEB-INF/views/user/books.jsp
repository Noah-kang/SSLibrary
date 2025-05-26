<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%-- 검색 폼 --%>
<form method="get" action="${ctx}/user/books" class="row g-2 mb-3">
    <div class="col-auto">
        <select name="field" class="form-select">
            <option value="title"    ${field=='title'    ? 'selected':''}>제목</option>
            <option value="author"   ${field=='author'   ? 'selected':''}>저자</option>
            <option value="location" ${field=='location' ? 'selected':''}>위치</option>
            <option value="category" ${field=='category' ? 'selected':''}>카테고리</option>
        </select>
    </div>
    <div class="col-auto">
        <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="검색어 입력" />
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-primary">검색</button>
    </div>
</form>

<%-- 도서 리스트 --%>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>ID</th>
        <th>제목</th>
        <th>저자</th>
        <th>위치</th>
        <th>카테고리1</th>
        <th>카테고리2</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${books}">
        <tr>
            <td>${b.bookId}</td>
            <td>${b.title}</td>
            <td>${b.author}</td>
            <td>${b.location}</td>
            <td>${b.category1Name}</td>
            <td>${b.category2Name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%-- 페이지네이션 --%>
<c:url var="baseUrl" value="${ctx}/user/books" />
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">

        <%-- 이전 블록 --%>
        <c:url var="prevUrl" value="${baseUrl}">
            <c:param name="page" value="${pageData.startPage - 1}" />
            <c:if test="${not empty field}">
                <c:param name="field" value="${field}" />
                <c:param name="keyword" value="${keyword}" />
            </c:if>
        </c:url>
        <li class="page-item ${pageData.hasPreviousBlock() ? '' : 'disabled'}">
            <a class="page-link" href="${prevUrl}">&laquo;</a>
        </li>

        <%-- 페이지 번호 --%>
        <c:forEach var="p" begin="${pageData.startPage}" end="${pageData.endPage}">
            <c:url var="pageUrl" value="${baseUrl}">
                <c:param name="page" value="${p}" />
                <c:if test="${not empty field}">
                    <c:param name="field" value="${field}" />
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <li class="page-item ${p == pageData.pageNumber ? 'active' : ''}">
                <a class="page-link" href="${pageUrl}">${p}</a>
            </li>
        </c:forEach>

        <%-- 다음 블록 --%>
        <c:url var="nextUrl" value="${baseUrl}">
            <c:param name="page" value="${pageData.endPage + 1}" />
            <c:if test="${not empty field}">
                <c:param name="field" value="${field}" />
                <c:param name="keyword" value="${keyword}" />
            </c:if>
        </c:url>
        <li class="page-item ${pageData.hasNextBlock() ? '' : 'disabled'}">
            <a class="page-link" href="${nextUrl}">&raquo;</a>
        </li>

    </ul>
</nav>
