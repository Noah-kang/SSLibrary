<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="d-flex justify-content-between mb-3">
    <h3>회원관리</h3>
    <a href="${ctx}/admin/members/new" class="btn btn-success">회원 등록</a>
</div>

<c:choose>
    <%-- 신규/수정 폼 --%>
    <c:when test="${mode=='new' or mode=='edit'}">
        <h3>${mode=='new' ? '회원 등록' : '회원 수정'}</h3>
        <c:choose>
            <c:when test="${mode == 'edit'}">
                <form action="${ctx}/admin/members/${member.memberId}/edit" method="post">
            </c:when>
            <c:otherwise>
                <form action="${ctx}/admin/members" method="post">
            </c:otherwise>
        </c:choose>

            <div class="mb-3">
                <label class="form-label">이름</label>
                <input type="text" name="name" class="form-control" value="${member.name}" required />
            </div>
            <div class="mb-3">
                <label class="form-label">이메일</label>
                <input type="email" name="email" class="form-control" value="${member.email}" required />
            </div>
            <div class="mb-3">
                <label class="form-label">비밀번호</label>
                <input type="password" name="password" class="form-control" />
            </div>
            <div class="mb-3">
                <label class="form-label">전화번호</label>
                <!-- required 속성 추가 -->
                <input type="text" name="phone"
                       class="form-control"
                       value="${member.phone}"
                       placeholder="010-1234-5678 형식으로 입력"
                       required />
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" name="isBlocked" class="form-check-input"
                    ${member.blocked ? 'checked' : ''} /> 차단
            </div>
            <button class="btn btn-primary" type="submit">
                    ${mode=='new' ? '등록' : '수정'}
            </button>
            <a href="${ctx}/admin/members" class="btn btn-secondary">취소</a>
        </form>
    </c:when>

    <%-- 목록 테이블 + 페이지네이션 --%>
    <c:otherwise>
        <%-- 기존 테이블 코드 --%>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th><th>이름</th><th>이메일</th><th>전화</th><th>권한</th><th>등록일</th><th>액션</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${members}">
                <tr>
                    <td>${m.memberId}</td>
                    <td>${m.name}</td>
                    <td>${m.email}</td>
                    <td>${m.phone}</td>
                    <td>${m.role}</td>
                    <td>${m.createdAtFormatted}</td>
                    <td>
                        <a href="${ctx}/admin/members/${m.memberId}/edit" class="btn btn-sm btn-warning">수정</a>
                        <!-- 삭제는 form+POST -->
                        <form action="${ctx}/admin/members/${m.memberId}/delete"
                              method="post" style="display:inline;">
                            <button type="submit"
                                    class="btn btn-sm btn-danger"
                                    onclick="return confirm('정말 삭제하시겠습니까?');">
                                삭제
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%-- 페이지네이션 코드 --%>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <%-- 이전 버튼 --%>
                <c:choose>
                    <c:when test="${pageData.hasPrevious()}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${ctx}/admin/members?page=${pageData.pageNumber-1}">«</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <%-- span 쓰면 클릭 방지 --%>
                            <span class="page-link">«</span>
                        </li>
                    </c:otherwise>
                </c:choose>

                <%-- 페이지 번호 --%>
                <c:forEach var="p" begin="1" end="${pageData.totalPages}">
                    <li class="page-item ${p == pageData.pageNumber ? 'active' : ''}">
                        <a class="page-link" href="${ctx}/admin/members?page=${p}">${p}</a>
                    </li>
                </c:forEach>

                <%-- 다음 버튼 --%>
                <c:choose>
                    <c:when test="${pageData.hasNext()}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${ctx}/admin/members?page=${pageData.pageNumber+1}">»</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <span class="page-link">»</span>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </c:otherwise>
</c:choose>




