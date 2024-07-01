package subject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import dao.SubjectDao;

@WebServlet(urlPatterns={"/subject/Subject_list"})
public class Subject_list extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// Daoインスタンス化
			SubjectDao dao=new SubjectDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");
			school.setName("学校名");

			List<Subject> list=dao.selectAllSubject(school);

			// リクエストに科目リストをセット
			request.setAttribute("subjects", list);

			// 学生一覧にフォワード
			request.getRequestDispatcher("subject_list.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
