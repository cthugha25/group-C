package subject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import dao.SubjectDao;

@WebServlet(urlPatterns={"/subject/Subject_delete"})
public class Subject_delete extends HttpServlet {

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

			// 科目コード取得
			String subjectCd=request.getParameter("cd");

			// 科目情報表示メソッド実行
			Subject subject=dao.get(subjectCd, school);

			// リクエストに科目情報をセット
			request.setAttribute("subject", subject);

			// 科目削除画面にフォワード
			request.getRequestDispatcher("subject_delete.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
