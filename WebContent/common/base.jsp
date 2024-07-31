<%-- 共通部分JSP --%>
<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
<head>
	<title>得点管理システム</title>
	<link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/bootstrap.min.css" />
</head>
<div class="warrper">
	${param.title}
	${param.scripts}
	<div class="container">
		<c:if test="${teacher != null}">
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
			<hr style="position: absolute;
			    left: 20%;
			    top: 6;
			    width: 2px;
			    height: 65%;
			    border: none;
			    border-left: 2px solid #f5f5f5;">
    	</c:if>
		${param.content}
	</div>
<footer>
	<div class="footer">©2024 TIC <br> 大原学園</div>
</footer>
</div>
</body>
</html>