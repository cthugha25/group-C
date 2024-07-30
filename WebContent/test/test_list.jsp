<%--学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@page import="bean.TestListStudent, bean.TestListSubject, java.util.List" %>
<% int num = (int)request.getAttribute("num"); %>
<% List<TestListSubject> list=(List<TestListSubject>)request.getAttribute("list"); %>
<% String year = (String)request.getAttribute("year"); %>
<% String classnum = (String)request.getAttribute("classnum"); %>
<% String subject = (String)request.getAttribute("subject"); %>
<% List<TestListSubject> ent_year_set=(List<TestListSubject>)request.getAttribute("ent_year_set"); %>
<% List<TestListSubject> class_num_set=(List<TestListSubject>)request.getAttribute("class_num_set"); %>
<% List<TestListStudent> subject_set=(List<TestListStudent>)request.getAttribute("subject_set"); %>
<% List<TestListStudent> tests=(List<TestListStudent>)request.getAttribute("tests"); %>
<%String titleextra = (String)request.getAttribute("title"); %>

<%String title = "成績一覧"; %>
<%if(num != 0){ %>
	<%title += titleextra; %>
<%} %>


<c:import url = "/common/base.jsp">
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
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4"><%=title %></h2>
			</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				<form action="test_filter" method="get" style="width: 950px;max-width: 100%;height: auto;">
					<table>
					<td>　　　科目情報&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="col-4">
							<div class="ochawan-subtitle"><label class="form-label" for="student-f1-select">入学年度</label></div>
							<select class="form-select" id="subject-f1-select" name="f1" style="width: 100px;">
								<% for (TestListSubject ent_year : ent_year_set) { %>
									<option value=<%=ent_year.getEntYear() %> <c:if test="${year==f1 }">selected</c:if>><%=ent_year.getEntYear() %></option>
								<% } %>
								<option value="null" selected>--------</option>
							</select>
						</div>
					</td>
					<td>
						<div class="col-4">
							<div class="ochawan-subtitle"><label class="form-label"  for="student-f2-select">クラス</label></div>
							<select class="form-select" id ="subject-f2-select" name="f2" style="width: 100px;">
								<% for (TestListSubject number : class_num_set) { %>
									<option value=<%=number.getClassNum() %> <c:if test="${num==f2 }">selected</c:if>><%=number.getClassNum() %></option>
								<% } %>
								<option value="null" selected>--------</option>
							</select>
						</div>
					</td>
					<td>
						<div class="col-4">
							<div class="ochawan-subtitle"><label class="form-label" for="student-f3-select">科目</label></div>
							<select class="form-select" id="subject-f3-select" name="f3" style="width: 180px;">
								<% for (TestListStudent name : subject_set) { %>
									<option value=<%=name.getSubjectName() %> <c:if test="${num==f3 }">selected</c:if>><%=name.getSubjectName() %></option>
								<% } %>
								<option value="null" selected>--------</option>
							</select>
						</div>
					</td>
					<td>
						<div class="col-2 text-center">
							<p><button class="btn btn-secondary" id="subjectfilter-button" name="sj">検索</button><br></p>
						</div>
					</td>
				</form>
					</table>
						<%if(num == 11){ %>
						<table>
						<td><div class="textarea1"><font color="Gold">　　　　入学年度とクラスと科目を選択してください</font></div></td>
						</table>
						<%} %>
				<hr style="margin-left: 20px; margin-right: 20px; color="#f5f5f5" size="2">
				<form action="test_filter" method="get" style="width: 950px;max-width: 100%;height: auto;">
					<table>
						<td>　　　学生情報&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
						<div class="col-4">
							<div class="ochawan-subtitle"><label class="form-label" for="student-f4-select" >学生番号</label></div>
							<input type="number" id="student-f4-select" name="f4" style="width: 250px;" placeholder="学生番号を入力してください" maxlength="10" required>
						</div>
						</td>
						<td>
						<div class="col-2 text-center">
							<button class="btn btn-secondary" id="studentfilter-button" name="st">検索</button>
						</div>
						<div class="mt-2 text-warning">${erroers.get("f1")}</div>
						</td>
					</table>
				</form>
				</div>
				<%if(num == 1){ %>
					<%if(list.size() > 0) {%>
					科目：<%=subject %>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th><a title="テスト情報表示" style="color: black;text-decoration: none;"" href="<%= request.getContextPath() %>/test/test_average?num=1&year=<%= year %>&classnum=<%= classnum %>">1回</a></th>
							<th><a title="テスト情報表示" style="color: black;text-decoration: none;"" href="<%= request.getContextPath() %>/test/test_average?num=2&year=<%= year %>&classnum=<%= classnum %>">2回</a></th>
						</tr>
						<%for (TestListSubject test : list){ %>
							<tr>
								<td><%=test.getEntYear() %></td>
								<td><%=test.getClassNum() %></td>
								<td><%=test.getStudentNo() %></td>
								<td><%=test.getStudentName() %></td>
									<%if(Integer.parseInt(test.getPoint(1)) != 9999){ %>
										<th><%=test.getPoint(1) %></th>
									<%} else{ %>
										<th>-</th>
									<%} %>
									<%if(Integer.parseInt(test.getPoint(2)) != 9999){ %>
										<th><%=test.getPoint(2) %></th>
									<%} else{ %>
										<th>-</th>
									<%} %>
							</tr>
						<%} %>
					</table>
					<%}else{ %>
						成績情報が存在しませんでした
					<%} %>
				<%}else if(num == 11 || num == 22){ %>
					<font color="Cyan">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</font>
				<%}else if (num == 2){ %>
					<c:choose>
						<c:when test="${student==null}">
							学生情報が存在しません
						</c:when>
						<c:when test="${tests.size()>0}">
							<p>氏名：${student.name }(${student.no })</p>
							<table class="table table-hover">
								<tr>
									<th>科目名</th>
									<th>科目コード</th>
									<th>回数</th>
									<th>点数</th>
								</tr>
								<c:forEach var="test" items="${tests}">
									<tr>
										<td>${test.subjectName}</td>
										<td>${test.subjectCd}</td>
										<td>${test.num}</td>
										<td>${test.point}</td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<c:otherwise>
							氏名：${student.name }(${student.no })
							<br>
							成績情報が存在しませんでした
						</c:otherwise>
					</c:choose>
				<%} %>
		</section>
	</c:param>
</c:import>
