<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%-- mode=new/edit 일 때: 폼 --%>
<c:choose>
    <c:when test="${mode=='new' or mode=='edit'}">
        <h3>${mode=='new' ? '도서 등록' : '도서 수정'}</h3>

        <c:choose>
            <c:when test="${mode=='edit'}">
                <form action="${ctx}/admin/books/${book.bookId}/edit" method="post">
            </c:when>
            <c:otherwise>
                <form action="${ctx}/admin/books" method="post">
            </c:otherwise>
        </c:choose>

        <div class="mb-3">
            <label class="form-label">제목</label>
            <input type="text" name="title" class="form-control"
                   value="${book.title}" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">저자</label>
            <input type="text" name="author" class="form-control"
                   value="${book.author}" />
        </div>
        <div class="mb-3">
            <label class="form-label">위치</label>
            <input type="text" name="location" class="form-control"
                   value="${book.location}" />
        </div>
        <div class="mb-3">
            <label class="form-label">비고</label>
            <textarea name="note" class="form-control">${book.note}</textarea>
        </div>
        <%-- 카테고리 드롭다운은 차후 추가 --%>
        <button class="btn btn-primary" type="submit">
                ${mode=='new' ? '등록' : '수정'}
        </button>
        <a href="${ctx}/admin/books" class="btn btn-secondary">취소</a>
        </form>
    </c:when>

    <%-- mode가 없으면: 목록 + 페이지네이션 --%>
    <c:otherwise>
        <div class="d-flex justify-content-between mb-3">
            <h3>도서관리</h3>
            <a href="${ctx}/admin/books/new" class="btn btn-success">도서 등록</a>
        </div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th><th>제목</th><th>저자</th><th>위치</th><th>비고</th><th>액션</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="b" items="${books}">
                <tr>
                    <td>${b.bookId}</td>
                    <td>${b.title}</td>
                    <td>${b.author}</td>
                    <td>${b.location}</td>
                    <td>${b.note}</td>
                    <td>
                        <a href="${ctx}/admin/books/${b.bookId}/edit" class="btn btn-sm btn-warning">수정</a>
                        <form action="${ctx}/admin/books/${b.bookId}/delete" method="post" style="display:inline;">
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('삭제하시겠습니까?');">삭제</button>
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
                <c:choose>
                    <c:when test="${pageData.hasPreviousBlock()}">
                        <li class="page-item">
                            <a class="page-link" href="${ctx}/admin/books?page=${pageData.startPage - 1}">&laquo;</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled"><span class="page-link">&laquo;</span></li>
                    </c:otherwise>
                </c:choose>

                <%-- 페이지 번호 --%>
                <c:forEach var="p" begin="${pageData.startPage}" end="${pageData.endPage}">
                    <li class="page-item ${p == pageData.pageNumber ? 'active' : ''}">
                        <a class="page-link" href="${ctx}/admin/books?page=${p}">${p}</a>
                    </li>
                </c:forEach>

                <%-- 다음 블록 --%>
                <c:choose>
                    <c:when test="${pageData.hasNextBlock()}">
                        <li class="page-item">
                            <a class="page-link" href="${ctx}/admin/books?page=${pageData.endPage + 1}">&raquo;</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled"><span class="page-link">&raquo;</span></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </c:otherwise>
</c:choose>