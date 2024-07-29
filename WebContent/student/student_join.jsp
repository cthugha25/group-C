<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
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
<h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">学生情報登録</h2>
</div>

 		<form action="StudentCreateExecute" method="post">

		<div class="mb-3">
				<label for="entYear" class="form-label">入学年度</label>
				<select class="form-control" id="entYear" name="ent_year" required>
				<c:forEach var="year" begin="2014" end="2034">
					<option value="${year}">${year}</option>
				</c:forEach>
				</select>
		</div>

		<div class="mb-3">
				<label for="studentNo" class="form-label">学生番号</label>
				<input type="text" class="form-control" id="studentNo" name="no" value="${no}" placeholder="学生番号を入力してください" maxlength="10" required>
		</div>

		<div class="mb-3">
				<label for="studentName" class="form-label">氏名</label>
				<input type="text" class="form-control" id="studentName" name="name" value="${name}" placeholder="氏名を入力してください" maxlength="30" required>
		</div>


		<div class="mb-3">
				<label for="studentName" class="form-label">ふりがな</label>
				<input type="text" class="form-control" id="hurigana" name="hurigana" value="${hurigana}" placeholder="ふりがなを入力してください" maxlength="30" required>
		</div>

		<div class="mb-3">
			<label for="classNum" class="form-label">クラス</label>
			<select class="form-control" id="classNum" name="classNum" required>
				<option value="121">121</option>
				<option value="131">131</option>
		</select>
		</div>

		<div class="mb-3">
			<label for="classNum" class="form-label">在学状況</label>
			<br>
			<div class="box-line">
			<label>在学中</label>
			<input type="checkbox" class="Attend-box" id="isAttend" name="isAttend" >
			</div>
		</div>

		<div class="mb-3">
			<label for="classNum" class="form-label">性別</label>
			<select class="form-control" id="gender" name="gender" required>
				<option value="男性">男性</option>
				<option value="女性">女性</option>
		</select>
		</div>

		<div class="mb-3 form-button">
				<button type="submit" class="btn btn-primary">登録して終了</button>
		</div>

		<div class="mb-3 form-link">
			<a href="StudentCreate.action">戻る</a>
		</div>
</form>
</section>
</c:param>
</c:import>