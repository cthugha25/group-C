package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.TestListStudent;
import bean.TestListSubject;
import dao.TestListSubjectDao;

@WebServlet(urlPatterns={"/test/test_list"})
public class Test_list extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			TestListSubjectDao dao=new TestListSubjectDao();
			List<TestListSubject> ent_year_set=dao.AllEntYear_test();
			List<TestListSubject> class_num_set=dao.AllClassNum_test();
			List<TestListStudent> subject_set=dao.AllSubject_test();
			List<TestListStudent> no_set=dao.AllNo_test();

			request.setAttribute("num", 0);
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
