package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns={"/login/Logout_execute"})
public class Logout_execute extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			HttpSession session=request.getSession();
			// ログイン中の場合セッションから情報削除してログアウト画面へ
			if (session.getAttribute("teacher")!=null) {
				session.removeAttribute("teacher");
				session.removeAttribute("id");
				request.getRequestDispatcher("logout.jsp")
					.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
