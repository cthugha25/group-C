package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;

// 成績参照の検索フォームの送信先
@WebServlet(urlPatterns={"/test/Test_filter"})
public class Test_filter extends HttpServlet {
	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// 成績リスト
			List<TestListStudent> tests = null;
			// エラーメッセージ
			Map<String,String> errors = new HashMap<>();
			// Daoインスタンス化
			TestListStudentDao dao=new TestListStudentDao();
			StudentDao stu_dao=new StudentDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");
			school.setName("学校名");

			// 入力された学生番号受け取り
			String studentNo=request.getParameter("f4");

			// メソッド実行
			tests = dao.filter(school, studentNo);		// 学生別成績参照
			Student student = stu_dao.get(studentNo, school);	// 学生情報取得
			// セレクトボックス用データ取得
			List<Student> ent_year_set=stu_dao.AllEntYear(school);
			List<Student> class_num_set=stu_dao.AllClassNum(school);

			// 値セット
			request.setAttribute("tests", tests);
			request.setAttribute("student", student);
			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);


			// 学生一覧にフォワード
			request.getRequestDispatcher("test_list_student.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
