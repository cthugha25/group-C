<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
 		pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">
		<div><h3 class="title">得点管理システム</h3></div>
	</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="mo-4">
        <div class="student-management">
			<h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">学生情報変更</h2>
        </div>

	        <form action="updateStudent" method="post">
	            <div class="mb-3">
	                <label for="entYear" class="form-label">入学年度</label>
	                <input type="text" class="form-control" id="entYear" name="entYear" value="${ent_year}" readonly>
	            </div>
	            <div class="mb-3">
	                <label for="studentNo" class="form-label">学生番号</label>
	                <input type="text" class="form-control" id="studentNo" name="studentNo" value="${no}" readonly>
	            </div>
	            <div class="mb-3">
	                <label for="studentName" class="form-label">氏名</label>
	                <input type="text" class="form-control" id="studentName" name="studentName" value="${name}">
	            </div>
	            <div class="mb-3">
	                <label for="classNum" class="form-label">クラス</label>
	                <select class="form-control" id="classNum" name="classNum">
	                    <c:forEach var="num" items="${class_num_set}">
					    <%--現在のnumrと選択されていたf2が一致していた場合selectedを追記 --%>
					    <option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
				    </c:forEach>
	                </select>
	            </div>
	            <div class="mb-3 d-flex align-items-center">
	            	<label class="form-check-label me-2" for="student-f3-check">在学中</label>
						<%--パラメータf3が存在している場合checkboxを追記 --%>
						<input class="mb-3 d-flex align-items-center" type="checkbox"
						id="student-f3-check" name="f3" value="t"
						<c:if test="${!empty f3 }">checked</c:if> />
	            </div>
	            <div class="mb-3 form-button">
	                <button type="submit" class="btn btn-primary">変更</button>
	            </div>
	             <div class="mb-3 form-link">
	                <a href="StudentCreate.action">戻る</a>
	            </div>
			</form>

        </section>
    </c:param>
</c:import>
