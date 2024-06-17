<%-- 学生登録JSP --%>
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
                <h2 class="h3 mb-3 fw-normal bg-opacity-10 py-2 px-4">学生情報登録</h2>
            </div>

            <form action="registerStudent" method="post">
                <div class="mb-3">
                    <label for="entYear" class="form-label">入学年度</label>
                    <select class="form-control" id="entYear" name="ent_year">
                        <c:forEach var="year" items="${yearList}">
                            <%--現在のnumrと選択されていたf2が一致していた場合selectedを追記 --%>
                            <option value="${year}" <c:if test="${year==f2 }">selectes</c:if>>${year }</option>
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
                    <label for="classNum" class="form-label">クラス</label>
                     <select class="form-control" id="classNum" name="classNum">
	                    <c:forEach var="num" items="${class_num_set }">
					    <%--現在のnumrと選択されていたf2が一致していた場合selectedを追記 --%>
					    <option value="${num }" <c:if test="${num==f2 }">selectes</c:if>>${num }</option>
				    </c:forEach>
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
