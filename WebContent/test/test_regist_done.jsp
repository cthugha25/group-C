<%-- 成績登録完了JSP --%>
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
                <h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">成績管理</h2>
            </div>

            <div class="mb-3">
                <p class="form-label">登録が完了しました</p>
            </div>
            <div class="mb-3 form-link">
	                <a href="Test_regist_filter">戻る</a>　　
	                <a href="../test/Test_list">成績参照</a>
	        </div>
        </section>
    </c:param>
</c:import>
