<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:choose>
    <%-- 신규/수정 폼 --%>
    <c:when test="${mode=='new' or mode=='edit'}">
        <h3>${mode=='new' ? '카테고리 등록' : '카테고리 수정'}</h3>
        <c:choose>
            <c:when test="${mode=='edit'}">
                <form action="${ctx}/admin/categories/${category.categoryId}/edit" method="post">
            </c:when>
            <c:otherwise>
                <form action="${ctx}/admin/categories" method="post">
            </c:otherwise>
        </c:choose>

        <div class="mb-3">
            <label class="form-label">이름</label>
            <input type="text" name="name" class="form-control"
                   value="${category.name}" required />
        </div>
        <div class="mb-3">
            <label class="form-label">부모 카테고리</label>
            <select name="parentId" class="form-select">
                <option value="" ${category.parentId == null?'selected':''}>(없음)</option>
                <c:forEach var="p" items="${parents}">
                    <option value="${p.categoryId}"
                        ${p.categoryId == category.parentId?'selected':''}>
                            ${p.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <button class="btn btn-primary" type="submit">
                ${mode=='new' ? '등록' : '수정'}
        </button>
        <a href="${ctx}/admin/categories" class="btn btn-secondary">취소</a>
        </form>
    </c:when>

    <%-- 목록 테이블 --%>
    <c:otherwise>
        <div class="d-flex justify-content-between mb-3">
            <h3>카테고리 관리</h3>
            <a href="${ctx}/admin/categories/new" class="btn btn-success">카테고리 등록</a>
        </div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th><th>이름</th><th>부모</th><th>액션</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${categories}">
                <tr>
                    <td>${c.categoryId}</td>
                    <td>${c.name}</td>
                    <td>${c.parentName}</td>
                    <td>
                        <a href="${ctx}/admin/categories/${c.categoryId}/edit"
                           class="btn btn-sm btn-warning">수정</a>
                        <form action="${ctx}/admin/categories/${c.categoryId}/delete"
                              method="post" style="display:inline;">
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('정말 삭제하시겠습니까?');">
                                삭제
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- 페이지네이션 --%>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:choose>
                    <c:when test="${pageData.hasPreviousBlock()}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${ctx}/admin/categories?page=${pageData.startPage - 1}">&laquo;</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled"><span class="page-link">&laquo;</span></li>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="p" begin="${pageData.startPage}" end="${pageData.endPage}">
                    <li class="page-item ${p == pageData.pageNumber ? 'active':''}">
                        <a class="page-link"
                           href="${ctx}/admin/categories?page=${p}">${p}</a>
                    </li>
                </c:forEach>

                <c:choose>
                    <c:when test="${pageData.hasNextBlock()}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${ctx}/admin/categories?page=${pageData.endPage + 1}">&raquo;</a>
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