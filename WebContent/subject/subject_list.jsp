<%--科目一覧JSP --%>
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
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">科目管理</h2>
			</div>
			<c:choose>
				<%-- 科目情報あり --%>
				<c:when test="${subjects.size()>0}">
					<div>検索結果：${subjects.size()}件</div>
					<table class="table table-hover">
						<tr>
							<th>科目コード</th>
							<th>科目名</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="subject" items="${subjects}">
							<tr>
								<td>${subject.cd}</td>
								<td>${subject.name}</td>
								<%-- 科目情報変更リンク --%>
								<td><a href="StudentUpdate.action?no=${student.no}">変更</a></td>
								<%-- 科目情報削除リンク --%>
								<td><a href="Subject_delete?cd=${subject.cd}">削除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<%-- 科目情報なし --%>
				<c:otherwise>
					<div class="none-student">科目情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>

