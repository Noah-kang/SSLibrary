<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:url var="baseUrl" value="/admin/books" />


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
        <%-- 카테고리 드롭다운 --%>
        <div class="mb-3">
            <label class="form-label">1차 카테고리</label>
            <select id="category1" name="category1Id" class="form-select" required>
                <option value="">(선택)</option>
                <c:forEach var="c" items="${parentCategories}">
                    <option value="${c.categoryId}"
                        ${book.category1Id == c.categoryId ? 'selected' : ''}>
                            ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">2차 카테고리</label>
            <select id="category2" name="category2Id" class="form-select">
                <option value="">(없음)</option>
                <c:if test="${not empty book.category1Id}">
                    <c:forEach var="c" items="${allCategories}">
                        <c:if test="${c.parentId == book.category1Id}">
                            <option value="${c.categoryId}"
                                ${c.categoryId == book.category2Id ? 'selected':''}>
                                    ${c.name}
                            </option>
                        </c:if>
                    </c:forEach>
                </c:if>
            </select>
        </div>

        <button class="btn btn-primary" type="submit">
                ${mode=='new' ? '등록' : '수정'}
        </button>
        <a href="${ctx}/admin/books" class="btn btn-secondary">취소</a>
        </form>

        <%-- JS: 1차 변경 시 2차 옵션 갱신 --%>
        <script>
            const allCategories = [
                <c:forEach var="c" items="${allCategories}">
                {
                    id: ${c.categoryId},
                    name: '${c.name}',
                    parentId: <c:choose>
                        <c:when test="${c.parentId != null}">
                        ${c.parentId}
                        </c:when>
                        <c:otherwise>
                        null
                    </c:otherwise>
                    </c:choose>
                }<c:if test="${!c_last}">,</c:if>
                </c:forEach>
            ];
            const cat1 = document.getElementById('category1');
            const cat2 = document.getElementById('category2');

            cat1.addEventListener('change', () => {
                const pid = parseInt(cat1.value);
                // 기존 옵션 지우고 기본 옵션 추가
                cat2.innerHTML = '<option value="">(없음)</option>';
                // 선택된 부모에 맞는 자식 필터하여 추가
                allCategories.filter(c => c.parentId === pid)
                    .forEach(c => {
                        const opt = document.createElement('option');
                        opt.value = c.id;
                        opt.textContent = c.name;
                        cat2.appendChild(opt);
                    });
            });
        </script>
    </c:when>

    <%-- mode가 없으면: 목록 + 페이지네이션 --%>
    <c:otherwise>
        <div class="d-flex justify-content-between mb-3">
            <h3>도서관리</h3>
            <a href="${ctx}/admin/books/new" class="btn btn-success">도서 등록</a>
        </div>
        <%-- 검색 --%>
        <form method="get" action="${ctx}/admin/books" class="row g-2 mb-3">
            <div class="col-auto">
                <select name="field" class="form-select">
                    <option value="title"    ${field=='title'    ? 'selected':''}>제목</option>
                    <option value="author"   ${field=='author'   ? 'selected':''}>저자</option>
                    <option value="location" ${field=='location' ? 'selected':''}>위치</option>
                    <option value="category" ${field=='category' ? 'selected':''}>카테고리</option>
                </select>
            </div>
            <div class="col-auto">
                <input type="text" name="keyword" value="${keyword}"
                       class="form-control" placeholder="검색어 입력" />
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">검색</button>
            </div>
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th><th>제목</th><th>저자</th><th>위치</th><th>비고</th><th>카테고리1</th><th>카테고리2</th><th>액션</th>
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
                    <td>${b.category1Name}</td>
                    <td>${b.category2Name}</td>
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

                <!-- 이전 블록 링크 -->
                <c:url var="prevUrl" value="${baseUrl}">
                    <c:param name="page" value="${pageData.startPage - 1}" />
                    <c:if test="${not empty field}">
                        <c:param name="field"   value="${field}"   />
                        <c:param name="keyword" value="${keyword}" />
                    </c:if>
                </c:url>
                <li class="page-item ${pageData.hasPreviousBlock() ? '' : 'disabled'}">
                    <a class="page-link" href="${prevUrl}">&laquo;</a>
                </li>

                <!-- 페이지 번호 -->
                <c:forEach var="p" begin="${pageData.startPage}" end="${pageData.endPage}">
                    <c:url var="pageUrl" value="${baseUrl}">
                        <c:param name="page" value="${p}" />
                        <c:if test="${not empty field}">
                            <c:param name="field"   value="${field}"   />
                            <c:param name="keyword" value="${keyword}" />
                        </c:if>
                    </c:url>
                    <li class="page-item ${p == pageData.pageNumber ? 'active':''}">
                        <a class="page-link" href="${pageUrl}">${p}</a>
                    </li>
                </c:forEach>

                <!-- 다음 블록 링크 -->
                <c:url var="nextUrl" value="${baseUrl}">
                    <c:param name="page" value="${pageData.endPage + 1}" />
                    <c:if test="${not empty field}">
                        <c:param name="field"   value="${field}"   />
                        <c:param name="keyword" value="${keyword}" />
                    </c:if>
                </c:url>
                <li class="page-item ${pageData.hasNextBlock() ? '' : 'disabled'}">
                    <a class="page-link" href="${nextUrl}">&raquo;</a>
                </li>

            </ul>
        </nav>
    </c:otherwise>
</c:choose>