<%--エラーページ--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:import url = "/common/base.jsp">
	<c:param name="title">
		<div class="title">
			<h1>得点管理システム</h1>
			<c:if test="${teacher!=null}">
				<p>${teacher.name }様</p>
				<a href="../login/Logout_execute">ログアウト</a>
			</c:if>
		</div>
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="mo-4">
			<p>エラーが発生しました</p>
		</section>
	</c:param>
</c:import>

