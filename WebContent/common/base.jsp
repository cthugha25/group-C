<%-- 共通部分JSP --%>
<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/bootstrap.min.css" />
</head>

<body>
	${param.title}
	${param.scripts}
	<div class="container">
		<div class="sidebar">
			<nav>
				<a href="../student/menu.jsp">メニュー</a>
				<br>
				<a href="../student/Student_list">学生管理</a>
				<br>
				<label>成績管理</label>
				<ul>
					<li><a href="#!">成績登録</a></li>
					<li><a href="../test/Test_list">成績参照</a></li>
				</ul>
				<a href="../subject/Subject_list">科目管理</a>
			</nav>
		</div>
		${param.content}
	</div>
</body>

<footer>
	<div class="footer">©2024 TIC <br> 大原学園</div>
</footer>
</html>
