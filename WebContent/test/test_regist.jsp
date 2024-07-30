<%-- 成績登録JSP --%>
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
				<h2 class="h3 mb-3 fw-norma  bg-opacity-10 py-2 px-4">成績管理</h2>
			</div>

			<form method="get" action="Test_regist">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
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

					<div class="col-4">
						<label class="form-label"  for="student-f2-select">科目</label>
						<select class="form-select" id ="student-f2-select" name="f3">
							<option value="0">---------------</option>
							<c:forEach var="subject" items="${subject_name_set }">
								<%--現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject.cd }" <c:if test="${subject.cd==f3 }">selected</c:if>>${subject.name }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label"  for="student-f2-select">回数</label>
						<select class="form-select" id ="student-f2-select" name="f4">
							<option value="0">---------------</option>
							<option value="1">1</option>
							<option value="2">2</option>
						</select>
					</div>

					<div class="col-2 text-center">
						<input type="submit" class="btn btn-secondary" id="filter-button" value="検索">
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>

			<c:choose>
				<c:when test="${all_info.size()>0}">
					<form method="get" action="Test_regist_execute">
						<div>科目：${subject.name}（${test_num}回）</div>
						<table class="table table-hover">
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>点数</th>
							</tr>
							<c:forEach var="info" items="${all_info}">
								<tr>
									<td>${info.student.entYear}</td>
									<td>${info.student.classNum}</td>
									<td>${info.student.no}</td>
									<td>${info.student.name}</td>
									<td>
										<c:choose>
											<c:when test="${test.point }">
												<input type="number" name="point" min="1" max="100"  value="${info.point }">
											</c:when>
											<c:otherwise>
												<input type="number" name="point" min="1" max="100"  value="${info.point }">
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
										<%--他のサーブレットに必要な情報をhiddenで隠す --%>
										<input type="hidden" name="student_no" value="${info.student.no}">
										<input type="hidden" name="f3" value="${subject.cd}">
										<input type="hidden" name="school_cd" value="${info.school.cd }">
										<input type="hidden" name="f4" value="${test_num}">
										<input type="hidden" name="class_Num" value="${info.student.classNum}">
							</c:forEach>
						</table>
						<div class="col-2 text-center">
							<input type="submit" class="btn btn-secondary" id="filter-button" value="登録して終了">
						</div>

					</form>
				</c:when>
				<c:otherwise>
					<div class="none-student">学生情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>

