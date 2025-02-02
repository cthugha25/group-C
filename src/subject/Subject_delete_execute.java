package subject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectDao;

@WebServlet(urlPatterns={"/subject/Subject_delete_execute"})
public class Subject_delete_execute extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// ログイン情報取得
			HttpSession session=request.getSession();

			// 科目コード取得
			String subjectCd=request.getParameter("cd");

			// 削除メソッド実行
			SubjectDao dao=new SubjectDao();
			boolean result=dao.delete(subjectCd);

			// リクエストに実行結果をセット
			request.setAttribute("result", result);

			// 科目削除完了画面にフォワード
			request.setAttribute("teacher", session.getAttribute("teacher"));
			request.getRequestDispatcher("subject_delete_done.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
