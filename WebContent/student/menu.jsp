<%--メニュー画面 --%>
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
			<div class="student-management">
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">メニュー</h2>
			</div>

			<div class="block-area">
			<div class="student" >
				<p><a href="../student/Student_list">学生管理</a><p>
			</div>
			<div class="Grades">
				<div class="Grade-management" >
					<p>成績管理</p>
				</div>
				<div class="Grades-Registration" >
					<p><a href="../test/Test_regist_filter">成績登録</a></p>
				</div>
				<div class="Grades-See" >
					<p><a href="../test/Test_list">成績参照</a></p>
				</div>
			</div>
			<div class="Course-Management" >
				<p><a href="../subject/Subject_list">科目管理</a></p>
			</div>
			</div>
		</section>
	</c:param>



</c:import>

