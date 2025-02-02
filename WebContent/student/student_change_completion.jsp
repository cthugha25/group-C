<%-- 学生変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        <div><h3 class="title">得点管理システム</h3></div>
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="mo-4">
            <div class="student-management">
                <h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">学生情報変更</h2>
            </div>

            <div class="mb-3">
                <p class="form-label">変更が完了しました</p>
            </div>
            <div class="mb-3 form-link">
	            <a href="StudentCreate.action">学生一覧</a>
	        </div>
        </section>
    </c:param>
</c:import>