<%--学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@page import="bean.TestListStudent, bean.TestListSubject, bean.TestListAverage, java.util.List" %>
<% String num = (String)request.getAttribute("num"); %>
<% List<TestListAverage> list=(List<TestListAverage>)request.getAttribute("list"); %>
<% List<TestListAverage> subject=(List<TestListAverage>)request.getAttribute("subject"); %>
<% String year = (String)request.getAttribute("year"); %>
<% String classnum = (String)request.getAttribute("classnum"); %>
<% int average = (int)request.getAttribute("average"); %>
<% int gradeaverage = (int)request.getAttribute("gradeaverage"); %>
<% String scorkind = (String)request.getAttribute("scorkind"); %>
<% List<TestListStudent> tests=(List<TestListStudent>)request.getAttribute("tests"); %>
<%String titleextra = (String)request.getAttribute("title"); %>


<c:import url = "/common/base.jsp">
	<c:param name="title">
		<div class="title"><h1>得点管理システム</h1>
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
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">テスト情報一覧</h2>
			</div>
			<a href="../test/Test_list">成績参照</a><br>
					<c:choose>
						<c:when test="${list.size()>0}">
							<form action="test_average" method="get">
								第<%=num %>回テスト結果(<%=scorkind %>)　クラス:<%=classnum %>　クラス平均:<%=average %>　学年平均:<%=gradeaverage %>
								<div style="float: right;">
								<select class="form-select" id ="subject-f2-select" name="search">
									<c:forEach var="subject" items="${subject }">
										<option value="${subject.subject }" <c:if test="${subject.subject==search }">selected</c:if>>${subject.subject }</option>
									</c:forEach>
									<option value="合計点" selected>合計点</option>
								</select>
								<input type="hidden" name="year" value="<%=year%>">
								<input type="hidden" name="classnum" value="<%=classnum%>">
								<input type="hidden" name="num" value="<%=num%>">
								<button class="btn btn-secondary" name="sub">検索</button>
								</div>
							</form>
							<table class="table table-hover">
								<tr>
									<th>入学年度</th>
									<th>学生番号</th>
									<th>氏名</th>
									<th>点数</th>
								</tr>
								<c:forEach var="test" items="${list}">
									<tr>
										<td>${test.entYear}</td>
										<td>${test.studentNo}</td>
										<td>${test.studentName}</td>
										<td>${test.points}</td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<c:otherwise>
							テスト情報が存在しませんでした
						</c:otherwise>
					</c:choose>
		</section>
	</c:param>
</c:import>

