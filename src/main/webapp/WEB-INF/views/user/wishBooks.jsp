<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>나의 희망도서</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-4">
    <c:choose>
    <%-- new 또는 edit 모드일 때만 폼 --%>
    <c:when test="${mode=='new' or mode=='edit'}">
        <h3>${mode=='new' ? '희망도서 신청' : '희망도서 수정'}</h3>
        <form action="${ctx}/user/wishBooks${mode=='new'?'/new':'/'+wish.wishId+'/edit'}"
              method="post" class="mb-4">
            <div class="mb-3">
                <label>제목</label>
                <input type="text" name="title" class="form-control"
                       value="${wish.title}" required/>
            </div>
            <div class="mb-3">
                <label>저자</label>
                <input type="text" name="author" class="form-control"
                       value="${wish.author}" required/>
            </div>
            <button class="btn btn-primary" type="submit">
                    ${mode=='new' ? '신청' : '수정'}
            </button>
            <a href="${ctx}/user/wishBooks" class="btn btn-secondary">취소</a>
        </form>
    </c:when>

    <%-- 기본 목록 모드 --%>
    <c:otherwise>
    <div class="d-flex justify-content-between mb-3">
        <h3>희망도서 목록</h3>
        <a href="${ctx}/user/wishBooks/new" class="btn btn-success">신청하기</a>
    </div>
    <%-- 목록 --%>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th><th>제목</th><th>저자</th><th>신청일</th><th>액션</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="w" items="${wishes}">
            <tr>
                <td>${w.wishId}</td>
                <td>${w.title}</td>
                <td>${w.author}</td>
                <td>${w.createdAtStr}</td>
                <td>
                    <form action="${ctx}/user/wishBooks/${w.wishId}/delete" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('삭제하시겠습니까?');">삭제</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%-- 페이지네이션 --%>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <%-- 이전 블록 --%>
            <c:url var="prevUrl" value="${ctx}/user/wishBooks">
                <c:param name="page" value="${pageData.startPage - 1}"/>
            </c:url>
            <li class="page-item ${pageData.hasPreviousBlock() ? '' : 'disabled'}">
                <a class="page-link" href="${prevUrl}">&laquo;</a>
            </li>
            <%-- 페이지 번호 --%>
            <c:forEach var="p" begin="${pageData.startPage}" end="${pageData.endPage}">
                <c:url var="pageUrl" value="${ctx}/user/wishBooks">
                    <c:param name="page" value="${p}"/>
                </c:url>
                <li class="page-item ${p == pageData.pageNumber ? 'active' : ''}">
                    <a class="page-link" href="${pageUrl}">${p}</a>
                </li>
            </c:forEach>
            <%-- 다음 블록 --%>
            <c:url var="nextUrl" value="${ctx}/user/wishBooks">
                <c:param name="page" value="${pageData.endPage + 1}"/>
            </c:url>
            <li class="page-item ${pageData.hasNextBlock() ? '' : 'disabled'}">
                <a class="page-link" href="${nextUrl}">&raquo;</a>
            </li>
        </ul>
    </nav>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>