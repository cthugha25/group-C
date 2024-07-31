package subject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
import tool.Page;

@WebServlet("/subject/subject_update")
public class Subject_update extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Page.header(out);

            try {
            	HttpSession session=request.getSession();
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

                // リクエストからパラメータを取得
                String subjectCd = request.getParameter("subjectCd");
                String subjectName = request.getParameter("subjectName");

                // バリデーションチェック
                String errorMessage = null;
                if (subjectName == null || subjectName.trim().isEmpty()) {
                    errorMessage = "科目名を入力してください。";
                } else if (subjectName.length() > 20) {
                    errorMessage = "科目名は20文字以内で入力してください。";
                }

                if (errorMessage != null) {
                    // エラーメッセージとユーザーが入力したデータをリクエストにセット
                	request.setAttribute("teacher", session.getAttribute("teacher"));
                    request.setAttribute("errorMessage", errorMessage);
                    Subject subject = new Subject();
                    subject.setCd(subjectCd);
                    subject.setName(subjectName);
                    request.setAttribute("subject", subject);

                    request.getRequestDispatcher("subject_update.jsp").forward(request, response);
                    return;
                }

                // Subjectオブジェクトを作成し、取得したパラメータを設定
                Subject subject = new Subject();
                subject.setCd(subjectCd);
                subject.setName(subjectName);

                SubjectDao dao = new SubjectDao();
                // 更新前に選択した科目が削除された場合
                if (dao.get(subjectCd, school)==null) {
                	Map<String,String> errors = new HashMap<>();
                	errors.put("error", "科目が存在していません");
		        	request.setAttribute("errors", errors);
		        	request.getRequestDispatcher("subject_update.jsp")
		        		.forward(request, response);
                }

                // 科目情報をデータベースに更新
                int linesUpdated = dao.update(subject);

                if (linesUpdated > 0) {
                	request.setAttribute("teacher", session.getAttribute("teacher"));
                    request.getSession().setAttribute("message", "変更が完了しました");
                    response.sendRedirect("subject_update_done.jsp");
                } else {
                	request.setAttribute("teacher", session.getAttribute("teacher"));
                    request.getSession().setAttribute("message", "変更に失敗しました");
                    response.sendRedirect("subject_update.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace(out);
            }
            Page.footer(out);
        }
    }
}