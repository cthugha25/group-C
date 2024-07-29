package student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import dao.TeacherDao;

@WebServlet(urlPatterns={"/student/Student_update_while"})
public class Student_update_while extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// ログイン状態チェック　未ログインならログインページに
			HttpSession session=request.getSession();
			if (session.getAttribute("teacher")==null) {
				String error="ログアウト状態ではシステムを使用できません。";
				request.setAttribute("error",error);
				request.getRequestDispatcher("../login/login.jsp")
					.forward(request, response);
			}

			// ログイン中の教師情報を取得して学校コードをセット
			TeacherDao teacher_dao=new TeacherDao();
			Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
			School school=new School();
			school.setCd(teacher.getSchool().getCd());

			// 在学フラグのデフォルトをtrue(在学中)にする
			boolean isAttend = true;
//			String isAttendStr="True";

			// 入学年度、クラス、在学フラグの値受け取り
			String[] isAttendStr = request.getParameterValues("f3");

			// 在学チェックボックスがチェックされていなかった場合在学フラグをfalseにする
			if (isAttendStr == null) {
				isAttend = false;
			}

			// Daoインスタンス化
			StudentDao dao=new StudentDao();

			String student_cd = request.getParameter("no");

			Student student=dao.get(student_cd, school);
			List<Student> class_num_set=dao.AllClassNum(school);
			List<Student> list=dao.filter(school, isAttend);

			request.setAttribute("teacher", session.getAttribute("teacher"));
            request.setAttribute("student", student);
            request.setAttribute("f2", student.getClassNum());
        	request.setAttribute("class_num_set", class_num_set);
        	request.setAttribute("f1", "0");
			request.setAttribute("f2", "0");
			request.setAttribute("f3", isAttendStr);
			// リクエストに学生リストをセット
			request.setAttribute("students", list);

			request.getRequestDispatcher("student_update.jsp")

				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}

