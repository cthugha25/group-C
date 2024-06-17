<%--学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:import url = "/common/base1.jsp">
	<c:param name="title">
		<div class="title"><h3>得点管理システム</h3></div>
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="menubar">
		<div class="title-menu">
			<h3>メニュー</h3>
		</div>
	</c:param>

	<c:param name="student">
				<div class="student" >
					<p><a href="Student_list">学生管理</a><p>
				</div>
	</c:param>

	<c:param name="Grades">
		<div class="Grades">
				<div class="Grade-management" >
					<p><a href="###">成績管理</a></p>
				</div>
				<div class="Grades-Registration" >
					<p><a href="###">成績登録</a></p>
				</div>
				<div class="Grades-See" >
					<p><a href="###">成績参照</a></p>
				</div>
		</div>
	</c:param>

	<c:param name="menu">
				<div class="Course-Management" >
					<p><a href="###">科目管理</a></p>
				</div>
	</c:param>



</c:import>

