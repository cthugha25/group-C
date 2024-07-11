<%--科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:import url = "/common/base.jsp">
	<c:param name="title">
		<div class="title"><h3>得点管理システム</h3></div>
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="mo-4">
			<div class="student-management">
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">科目情報削除</h2>
			</div>

			<%-- 削除フォーム --%>
			<p>「${subject.name}(${subject.cd})」を削除してもよろしいですか</p>
			<form method="get" action="Subject_delete_Execute">
				<input type="hidden" name="cd" value="${subject.cd}">
				<input type="submit" class="btn btn-secondary" id="filter-button" value="削除">
			</form>

			<%-- 前のページに戻るリンク --%>
			<a href="#" onclick="history.back()">戻る</a>

		</section>
	</c:param>
</c:import>

