<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap" %>
<% HashMap errors=(HashMap)request.getAttribute("errors"); %>
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
            <div class="subject-management">
                <h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">科目情報登録</h2>
            </div>

            <form action="Subject_Complete" method="post" accept-charset="UTF-8">
                <div class="mb-3">
                    <label for="subjectCode" class="form-label">科目コード</label>
                    <input type="text" class="form-control" id="subjectCode" name="subjectCode" placeholder="科目コードを入力してください" maxlength="10" required>
                	<div class="mt-2 text-warning">${errors.get("error")}</div>
                </div>
                <div class="mb-3">
                    <label for="subjectName" class="form-label">科目名</label>
                    <input type="text" class="form-control" id="subjectName" name="subjectName" placeholder="科目名を入力してください" maxlength="30" required>
                </div>
                <div class="mb-3 form-button">
                    <button type="submit" class="btn btn-primary">登録</button>
                </div>
                <div class="mb-3 form-link">
                    <a href="Subject_list">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
