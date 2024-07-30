<%-- 共通部分JSP --%>
<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
	<title>得点管理システム</title>
	<link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/bootstrap.min.css" />
</head>
    <style>
        .vertical-hr {
            width: 1.5px; /* 縦線の幅 */
            height: 400px; /* 縦線の高さ */
            background-color: #f5f5f5;
            border: none; /* ボーダーを消す */
            margin-right: 10px; /* 右側の余白 */
        }
    </style><body>
<div class="warrper">
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
					<li><a href="../test/Test_regist_filter">成績登録</a></li>
					<li><a href="../test/Test_list">成績参照</a></li>
				</ul>
				<a href="../subject/Subject_list">科目管理</a>
			</nav>
		</div>
		<hr style="width: 2px; height: 400px; background-color: #f5f5f5; border: none; margin-right: 10px;">
		${param.content}
	</div>
<footer>
	<div class="footer">©2024 TIC <br> 大原学園</div>
</footer>
</div>
</body>
</html>