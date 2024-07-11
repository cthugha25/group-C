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
import dao.StudentDao;

// 成績参照の検索フォームの送信先
@WebServlet(urlPatterns={"/test/Test_list"})
public class Test_list extends HttpServlet {
	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// 検索前フラグ
			String f = "li";

			// Daoインスタンス化
			StudentDao dao=new StudentDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");

			// セレクトボックスに表示するデータ取得
			List<Student> ent_year_set=dao.AllEntYear(school);
			List<Student> class_num_set=dao.AllClassNum(school);

			// 値セット
			request.setAttribute("f", f);
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
