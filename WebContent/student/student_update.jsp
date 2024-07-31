<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
 		pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
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
				<h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">学生情報変更</h2>
	        </div>

	        <form action="student_update" method="post">
	            <div class="mb-3">
	                <label for="entYear" class="form-label">入学年度</label>
	                <input type="text" class="form-control" id="entYear" name="entYear" value="${student.entYear}" readonly>
	            </div>
	            <div class="mb-3">
	                <label for="studentNo" class="form-label">学生番号</label>
	                <input type="text" class="form-control" id="studentNo" name="studentNo" value="${student.no}" readonly>
	            </div>
	            <div class="mb-3">
	                <label for="studentName" class="form-label">氏名</label>
	                <input type="text" class="form-control" id="studentName" name="studentName" value="${student.name}">
	                <c:if test="${not empty errorMessage}">
                        <div class="error-message">${errorMessage}</div>
                    </c:if>
	            </div>
	            <div class="mb-3">
					<label for="studentName" class="form-label">ふりがな</label>
					<input type="text" class="form-control" id="hurigana" name="hurigana" value="${student.hurigana}" maxlength="30" required>
				</div>
	            <div class="mb-3">
	                <label for="classNum" class="form-label">クラス</label>
	                <select class="form-control" id="classNum" name="classNum">
					    <c:forEach var="num" items="${class_num_set}">
							<%--現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
							<option value="${num.classNum}" <c:if test="${num.classNum==classNum}">selected</c:if>>${num.classNum}</option>
						</c:forEach>
	                </select>
	            </div>
				<div class="mb-3">
				    <label class="form-check-label" for="student-f3-check">在学中 　
				        <input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="True"
    					<c:if test="${!empty f3 }">checked</c:if> />
				    </label>
				</div>
				<div class="mb-3">
					<label for="classNum" class="form-label">性別</label>
					<select class="form-control" id="gender" name="gender" required>
						<option value="男性" <c:if test="${student.gender==\"男性\"}">selected</c:if>>男性</option>
						<option value="女性" <c:if test="${student.gender==\"女性\"}">selected</c:if>>女性</option>
					</select>
				</div>

	            <div class="mb-3 form-button">
	                <button type="submit" class="btn btn-primary">変更</button>
	            </div>
	            <div class="mb-3 form-link">
                    <a href="#" onclick="history.back()">戻る</a>
                </div>
			</form>

        </section>
    </c:param>
</c:import>
