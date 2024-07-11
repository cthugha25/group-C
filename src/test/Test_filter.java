package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
			// Daoインスタンス化
			TestListStudentDao dao=new TestListStudentDao();
			StudentDao stu_dao=new StudentDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");

			// 入力された学生番号と検索識別コード受け取り
			String studentNo=request.getParameter("f4");
			String f=request.getParameter("f");

			// メソッド実行
			Student student = stu_dao.get(studentNo, school);	// 学生情報取得
			tests = dao.filter(student);		// 学生別成績参照
			// セレクトボックス用データ取得
			List<Student> ent_year_set=stu_dao.AllEntYear(school);
			List<Student> class_num_set=stu_dao.AllClassNum(school);

			// 値セット
			request.setAttribute("f", f);
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
