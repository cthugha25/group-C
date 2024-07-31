package student;

import java.io.IOException;
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

//初回の学生一覧ページ遷移でセレクトボックスのデータ表示
@WebServlet(urlPatterns={"/student/Student_list"})
public class Student_list extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
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

			// 入学年度、クラス番号、学生一覧表示メソッド実行
			StudentDao dao=new StudentDao();
			List<Student> ent_year_set=dao.AllEntYear(school);
			List<Student> class_num_set=dao.AllClassNum(school);
			List<Student> list=dao.filter(school);

			// リクエストに値をセット
			request.setAttribute("teacher", session.getAttribute("teacher"));
			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);
			request.setAttribute("f3", "");
			request.setAttribute("students", list);

			// 学生一覧にフォワード
			request.getRequestDispatcher("student_list.jsp")
				.forward(request, response);

		} catch (Exception e) {
			// エラーページにフォワード
			request.getRequestDispatcher("../common/error.jsp")
				.forward(request, response);
		}
	}
}
