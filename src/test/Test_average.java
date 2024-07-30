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
import bean.TestListAverage;
import dao.TeacherDao;
import dao.TestListAverageDao;

@WebServlet(urlPatterns={"/test/test_average"})
public class Test_average extends HttpServlet {

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
			if(request.getParameter("sub") != null){
				TestListAverageDao avg = new TestListAverageDao();
				String year = request.getParameter("year");
				String classnum = request.getParameter("classnum");
				String num = request.getParameter("num");
				String search = request.getParameter("search");
				List<TestListAverage> list = avg.test_averagefilter(year, classnum, num, search, school);
				int average = avg.average(year, classnum, num, search, school);
				int gradeaverage = avg.gradeaverage(year, num, search, school);
				request.setAttribute("list", list);
				request.setAttribute("average", average);
				request.setAttribute("gradeaverage", gradeaverage);
	        	request.setAttribute("teacher", session.getAttribute("teacher"));

				List<TestListAverage> subject = avg.allsubject(year, num, school);

				request.setAttribute("subject", subject);
				request.setAttribute("year", year);
				request.setAttribute("classnum", classnum);
				request.setAttribute("num", num);
				request.setAttribute("scorkind", search);
				request.getRequestDispatcher("test_average.jsp")
					.forward(request, response);
			}else{
				String search = "合計点";
				String num = request.getParameter("num");
				String year = request.getParameter("year");
				String classnum = request.getParameter("classnum");
				TestListAverageDao dao=new TestListAverageDao();
				List<TestListAverage> list = dao.test_averagefilter(year, classnum, num, search, school);
				int average = dao.average(year, classnum, num, search, school);
				int gradeaverage = dao.gradeaverage(year, num, search, school);
				List<TestListAverage> subject = dao.allsubject(year, num, school);
	        	request.setAttribute("teacher", session.getAttribute("teacher"));

				request.setAttribute("list", list);
				request.setAttribute("subject", subject);
				request.setAttribute("average", average);
				request.setAttribute("gradeaverage", gradeaverage);
				request.setAttribute("num", num);
				request.setAttribute("year", year);
				request.setAttribute("classnum", classnum);
				request.setAttribute("scorkind", search);

				request.getRequestDispatcher("test_average.jsp")
					.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}