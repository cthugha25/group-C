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

@WebServlet(urlPatterns={"/student/Student_list"})
public class Student_list extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			boolean isAttend = true;

			// Daoインスタンス化
			StudentDao dao=new StudentDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");
			school.setName("学校名");

			List<Student> ent_year_set=dao.AllEntYear();
			List<Student> class_num_set=dao.AllClassNum();
			List<Student> list=dao.filter(school, isAttend);

			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);
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
