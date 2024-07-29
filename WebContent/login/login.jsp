<%--ログイン画面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
	<head>
		<title>得点管理システム</title>
		<link rel="stylesheet" href="../css/style.css">
		<link rel="stylesheet" href="../css/signin.css">
		<link rel="stylesheet" href="../css/bootstrap.min.css" />
		<script src="../js/password.js"></script>
	</head>

	<body>
		<div class="title">
			<h1>得点管理システム</h1>
		</div>

		<div class="box">
			<h2>ログイン</h2>

			<c:if test="${error!=null}">
				<ul>
					<li>${error}</li>
				</ul>
			</c:if>

			<form action="../login/Login_execute" method="post" class="login_form">
				<p>ID</p>
				<input type="text" name="id" id="id" placeholder="半角でご入力ください" max="20" <c:if test="${id!=null}">value="${id}"</c:if> required>
				<p>パスワード</p>
				<input type="password" name="password" id="password" placeholder="20文字以内の半角英数字でご入力ください" max="20" <c:if test="${password!=null}">value="${password}"</c:if> required>
				<label><input type="checkbox" id="chk_d_ps">パスワードを表示</label>
				<br>
				<input type="submit" id="login" value="ログイン">
			</form>
		</div>
	</body>

	<footer>
		<div class="footer">©2024 TIC <br> 大原学園</div>
	</footer>
</html>
