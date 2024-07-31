package subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.SubjectDao;
import dao.TeacherDao;

@WebServlet("/subject/Subject_Complete")
public class Subject_Complete extends HttpServlet {
	public void doPost (
			HttpServletRequest request, HttpServletResponse response
		) throws ServletException, IOException {
			HttpSession session=request.getSession();
			try {
				// ログイン状態チェック　未ログインならログインページに
				if (session.getAttribute("teacher")==null) {
					String error="ログアウト状態ではシステムを使用できません。";
					request.setAttribute("error",error);
					request.getRequestDispatcher("../login/login.jsp")
						.forward(request, response);
				}

				// ログイン中の教師情報を取得して学校コードとidをセット
				TeacherDao teacher_dao=new TeacherDao();
				Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
				School school=new School();
				school.setCd(teacher.getSchool().getCd());
				teacher.setId(session.getAttribute("id").toString());

				Map<String,String> errors = new HashMap<>();

				// フォームからのデータを取得
		        String subjectCode = request.getParameter("subjectCode");
		        String subjectName = request.getParameter("subjectName");

		        SubjectDao dao=new SubjectDao();
		     // 科目コード文字数チェック
		        if (subjectCode.length() < 3) {
		        	errors.put("error", "科目コードは3文字で入力してください");
		        	request.setAttribute("errors", errors);
		        	request.getRequestDispatcher("subject_join.jsp")
		        		.forward(request, response);
		        }
		        // 科目コード重複チェック
		        if (dao.get(subjectCode, school)!=null) {
		        	errors.put("error", "科目コードが重複しています");
		        	request.setAttribute("errors", errors);
		        	request.getRequestDispatcher("subject_join.jsp")
		        		.forward(request, response);
		        }

		        int line=dao.insertSubject(school,subjectCode,subjectName,teacher);
		        if (line>0) {
		        	request.setAttribute("teacher", session.getAttribute("teacher"));
		        	request.getRequestDispatcher("subject_complete.jsp").forward(request, response);
		        }
			} catch (Exception e) {
				request.setAttribute("teacher", session.getAttribute("teacher"));
				request.getRequestDispatcher("../common/error.jsp")
					.forward(request, response);
			}
		}
}
