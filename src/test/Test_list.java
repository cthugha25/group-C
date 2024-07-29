package test;

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
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.TeacherDao;
import dao.TestListSubjectDao;

@WebServlet(urlPatterns={"/test/Test_list"})
public class Test_list extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			HttpSession session=request.getSession();
			if (session.getAttribute("teacher")==null) {
				String error="ログアウト状態ではシステムを使用できません。";
				request.setAttribute("error",error);
				request.getRequestDispatcher("../login/login.jsp")
					.forward(request, response);
			}
			TeacherDao teacher_dao=new TeacherDao();
			Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
			School school=new School();
			school.setCd(teacher.getSchool().getCd());
			TestListSubjectDao dao=new TestListSubjectDao();
			List<TestListSubject> ent_year_set=dao.AllEntYear_test(school);
			List<TestListSubject> class_num_set=dao.AllClassNum_test(school);
			List<TestListStudent> subject_set=dao.AllSubject_test(school);
			List<TestListStudent> no_set=dao.AllNo_test(school);

			request.setAttribute("num", 0);
        	request.setAttribute("teacher", session.getAttribute("teacher"));
			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);
			request.setAttribute("subject_set", subject_set);
			request.setAttribute("no_set", no_set);

			request.getRequestDispatcher("test_list.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
