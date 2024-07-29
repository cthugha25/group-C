package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;

@WebServlet(urlPatterns={"/login/Login_execute"})
public class Login_execute extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		try {
			// セッション
			HttpSession session=request.getSession();

			// 入力された教師idとパスワード取得
			String id = request.getParameter("id");
			String password = request.getParameter("password");


			// Daoインスタンス化
			TeacherDao dao=new TeacherDao();
			// ログインメソッド実行
			Teacher teacher=dao.login(id, password);

			if (teacher!=null) {	// 教師情報があればメニュー画面に
				session.setAttribute("teacher", teacher);
				session.setAttribute("id", id);
				request.getRequestDispatcher("../student/menu.jsp")
					.forward(request, response);
			} else {	// 無ければエラーメッセージ入れてログイン画面に
				String error="ログインに失敗しました。"
						+ "IDまたはパスワードが正しくありません。";
				request.setAttribute("error",error);
				request.setAttribute("id", id);
				request.setAttribute("password", password);
				request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			}

		} catch (Exception e) {
			// エラーページにフォワード
			request.getRequestDispatcher("../common/error.jsp")
				.forward(request, response);
		}
	}
}
