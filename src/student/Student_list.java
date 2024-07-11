package student;

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

//初回の学生一覧ページ遷移でセレクトボックスのデータ表示
@WebServlet(urlPatterns={"/student/Student_list"})
public class Student_list extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// 在学フラグのデフォルトをtrue(在学中)にする
			boolean isAttend = true;
			String isAttendStr="True";

			// Daoインスタンス化
			StudentDao dao=new StudentDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");

			List<Student> ent_year_set=dao.AllEntYear(school);
			List<Student> class_num_set=dao.AllClassNum(school);
			List<Student> list=dao.filter(school, isAttend);

			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);
//			request.setAttribute("f1", "0");
//			request.setAttribute("f2", "0");
			request.setAttribute("f3", isAttendStr);
			// リクエストに学生リストをセット
			request.setAttribute("students", list);

			// 学生一覧にフォワード
			request.getRequestDispatcher("student_list.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
