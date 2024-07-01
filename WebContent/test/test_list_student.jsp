<%--成績参照結果JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
			pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:import url = "/common/base.jsp">
	<c:param name="title">
		<div class="title"><h3>得点管理システム</h3></div>
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="mo-4">
			<div class="student-management">
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>
			</div>

			<%-- 科目別成績参照用フォーム --%>
			<form method="get" action="Test_filter">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<p>科目情報</p>
					<%-- 入学年度セレクトボックス --%>
					<div class="col-4">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">---------------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%--現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year.entYear }" <c:if test="${year.entYear==f1 }">selected</c:if>>${year.entYear }</option>
							</c:forEach>
						</select>
					</div>

					<%-- クラス番号セレクトボックス --%>
					<div class="col-4">
						<label class="form-label"  for="student-f2-select">クラス</label>
						<select class="form-select" id ="student-f2-select" name="f2">
							<option value="0">---------------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%--現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num.classNum }" <c:if test="${num.classNum==f2 }">selected</c:if>>${num.classNum }</option>
							</c:forEach>
						</select>
					</div>

					<%-- 科目セレクトボックス --%>
					<div class="col-4">
						<label class="form-label"  for="student-f2-select">科目</label>
						<select class="form-select" id ="student-f2-select" name="f3">
							<option value="0">---------------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%--現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num.classNum }" <c:if test="${num.classNum==f3 }">selected</c:if>>${num.classNum }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 text-center">
						<input type="submit" class="btn btn-secondary" id="filter-button" value="検索">
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>

			<%-- 学生別成績参照用フォーム --%>
			<form method="get" action="Test_filter">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<p>学生情報</p>
					<%-- 学生番号テキストボックス --%>
					<div class="col-4">
						<label class="form-label" for="student-f1-select">学生番号</label>
						<input type="number" name="f4" placeholder="学生番号を入力してください" maxlength="10" required>
					</div>

					<div class="col-2 text-center">
						<input type="submit" class="btn btn-secondary" id="filter-button" value="検索">
					</div>
				</div>
			</form>

			<c:choose>
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
					<div class="none-student">成績情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>

