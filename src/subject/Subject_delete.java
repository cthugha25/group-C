package subject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import dao.TeacherDao;

@WebServlet(urlPatterns={"/subject/Subject_delete"})
public class Subject_delete extends HttpServlet {

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

			String subjectCd=request.getParameter("cd");

			SubjectDao dao=new SubjectDao();
			Subject subject=dao.get(subjectCd, school);

			request.setAttribute("teacher", session.getAttribute("teacher"));
			request.setAttribute("subject", subject);

			request.getRequestDispatcher("subject_delete.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
