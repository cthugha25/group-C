<%-- 科目変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
		<div class="title">
        <h1>得点管理システム</h1>
        <c:if test="${teacher != null}">
            <div class="user-info">
                <p>${teacher.name}様</p>
                <a href="../login/Logout_execute">ログアウト</a>
            </div>
        </c:if>
    </div>
	</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="mo-4">
            <div class="student-management">
                <h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">科目変更</h2>
            </div>

            <form action="subject_update" method="post">
                <div class="mb-3">
                    <label for="subjectCd" class="form-label">科目コード</label>
                    <p>${subject.cd}</p>
                    <input type="hidden" name="subjectCd" value="${subject.cd}">
                </div>
                <div class="mb-3">
                    <label for="subjectName" class="form-label">科目名</label>
                    <input type="text" class="form-control" id="subjectName" name="subjectName" value="${subject.name}">
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger mt-2" role="alert">
                            ${errorMessage}
                        </div>
                    </c:if>
                </div>
                <div class="mb-3 form-button">
                    <button type="submit" class="btn btn-primary">変更</button>
                </div>
                <div class="mb-3 form-link">
                    <a type="link" onclick="history.back()"><font color="<%= "#2C7CFF" %>">戻る</font></a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
