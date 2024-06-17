<%-- 共通部分JSP --%>
<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="../css/bootstrap.min.css" />
	<link rel="stylesheet" href="../css/style.css">
</head>

<body>

${param.title}
${param.menubar}
${param.scripts}

<div class="container">
	<div class="sidebar">
		<nav>
			<a href="menu.jsp">メニュー</a>
			<br>
			<a href="Student_list">学生管理</a>
			<br>
			<label>成績管理</label>
			<ul>
				<li><a href="#!">成績登録</a></li>
				<li><a href="#!">成績参照</a></li>
			</ul>
			<a href="#!">科目管理</a>
		</nav>
	</div>

</div>

<div class="block-area">
	${param.content}
	${param.student}
	${param.Grades}
	${param.menu}

</div>
</body>

<footer>
	<div class="footer">©2024 TIC <br> 大原学園</div>
</footer>
</html>
