<%--科目削除完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:import url = "/common/base.jsp">
	<c:param name="title">
		<div class="title">
			<h2>得点管理システム</h2>
			<c:if test="${teacher!=null}">
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
			<c:choose>
				<%-- 削除成功 --%>
				<c:when test="${result==true}">
					<div class="student-management">
						<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">科目情報削除</h2>
					</div>
					<p>削除が完了しました</p>
					<%-- 科目一覧リンク --%>
					<a href="../subject/Subject_list">科目一覧</a>
				</c:when>
				<%-- 削除失敗 --%>
				<c:otherwise>
					<div class="none-student">エラーが発生しました</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>

