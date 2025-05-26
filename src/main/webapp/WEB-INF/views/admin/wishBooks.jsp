<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:choose>
    <c:when test="${mode=='edit'}">
        <h3>희망도서 수정</h3>
        <form action="${ctx}/admin/wishBooks/${wish.wishId}/edit" method="post">
            <div class="mb-3">
                <label>제목</label>
                <input type="text" name="title" value="${wish.title}" required class="form-control"/>
            </div>
            <div class="mb-3">
                <label>저자</label>
                <input type="text" name="author" value="${wish.author}" required class="form-control"/>
            </div>
            <button class="btn btn-primary">수정</button>
            <a href="${ctx}/admin/wishBooks" class="btn btn-secondary">취소</a>
        </form>
    </c:when>
    <c:otherwise>
        <h3>희망도서 관리</h3>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th><th>회원</th><th>제목</th><th>저자</th><th>신청일</th><th>액션</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="w" items="${wishes}">
                <tr>
                    <td>${w.wishId}</td>
                    <td>${w.memberName}</td>
                    <td>${w.title}</td>
                    <td>${w.author}</td>
                    <td>${w.createdAtStr}</td>
                    <td>
                        <a href="${ctx}/admin/wishBooks/${w.wishId}/edit" class="btn btn-sm btn-warning">수정</a>
                        <form action="${ctx}/admin/wishBooks/${w.wishId}/delete"
                              method="post" style="display:inline;">
                            <button onclick="return confirm('삭제하시겠습니까?');"
                                    class="btn btn-sm btn-danger">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- 페이지네이션 include --%>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                    <%-- 이전 블록 --%>
                <c:url var="prevUrl" value="${ctx}/admin/wishBooks">
                    <c:param name="page" value="${pageData.startPage - 1}"/>
                </c:url>
                <li class="page-item ${pageData.hasPreviousBlock() ? '' : 'disabled'}">
                    <a class="page-link" href="${prevUrl}">&laquo;</a>
                </li>
                    <%-- 페이지 번호 --%>
                <c:forEach var="p" begin="${pageData.startPage}" end="${pageData.endPage}">
                    <c:url var="pageUrl" value="${ctx}/admin/wishBooks">
                        <c:param name="page" value="${p}"/>
                    </c:url>
                    <li class="page-item ${p == pageData.pageNumber ? 'active' : ''}">
                        <a class="page-link" href="${pageUrl}">${p}</a>
                    </li>
                </c:forEach>
                    <%-- 다음 블록 --%>
                <c:url var="nextUrl" value="${ctx}/admin/wishBooks">
                    <c:param name="page" value="${pageData.endPage + 1}"/>
                </c:url>
                <li class="page-item ${pageData.hasNextBlock() ? '' : 'disabled'}">
                    <a class="page-link" href="${nextUrl}">&raquo;</a>
                </li>
            </ul>
        </nav>
    </c:otherwise>
</c:choose>