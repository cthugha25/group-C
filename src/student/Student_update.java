package student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;  // Schoolクラスのインポート
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import dao.TeacherDao;
import tool.Page;

@WebServlet("/student/student_update")
public class Student_update extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Page.header(out);

            try {
            	// ログイン状態チェック　未ログインならログインページに
    			HttpSession session=request.getSession();
    			if (session.getAttribute("teacher")==null) {
    				String error="ログアウト状態ではシステムを使用できません。";
    				request.setAttribute("error",error);
    				request.getRequestDispatcher("../login/login.jsp")
    					.forward(request, response);
    			}

    			// ログイン中の教師情報を取得して学校コードをセット
    			TeacherDao teacher_dao=new TeacherDao();
    			Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
    			School school=new School();
    			school.setCd(teacher.getSchool().getCd());

                // リクエストからパラメータを取得
                String studentNo = request.getParameter("studentNo");
                String studentName = request.getParameter("studentName");
                int entYear = Integer.parseInt(request.getParameter("entYear"));
                String classNum = request.getParameter("classNum");
                boolean isAttend = request.getParameter("f3") != null;
                String hurigana = request.getParameter("hurigana");
                String gender = request.getParameter("gender");

                // バリデーションチェック
                String errorMessage = null;
                if (studentName == null || studentName.trim().isEmpty()) {
                    errorMessage = "氏名を入力してください。";
                } else if (studentName.length() > 10) {
                    errorMessage = "氏名は10文字以内で入力してください。";
                }

                if (errorMessage != null) {
                    // エラーメッセージとユーザーが入力したデータをリクエストにセット
                    request.setAttribute("errorMessage", errorMessage);
                    Student student = new Student();
                    student.setNo(studentNo);
                    student.setName(studentName);
                    student.setEntYear(entYear);
                    student.setClassNum(classNum);
                    student.setAttend(isAttend);
                    student.setHurigana(hurigana);
                    student.setGender(gender);
                    request.setAttribute("student", student);

                    // Schoolオブジェクトを作成し、プロパティを設定
                    request.setAttribute("teacher", session.getAttribute("teacher"));
                    request.setAttribute("class_num_set", new StudentDao().AllClassNum(school));

                    request.getRequestDispatcher("student_update.jsp").forward(request, response);
                    return;
                }

                // Studentオブジェクトを作成し、取得したパラメータを設定
                Student student = new Student();
                student.setNo(studentNo);
                student.setName(studentName);
                student.setEntYear(entYear);
                student.setClassNum(classNum);
                student.setAttend(isAttend);
                student.setHurigana(hurigana);
                student.setGender(gender);

                // 学生情報をデータベースに更新
                StudentDao dao = new StudentDao();
                int linesUpdated = dao.update(student);

                if (linesUpdated > 0) {
                	request.setAttribute("teacher", session.getAttribute("teacher"));
                    request.getSession().setAttribute("message", "変更が完了しました");
                    response.sendRedirect("student_update_done.jsp");
                } else {
                	request.setAttribute("teacher", session.getAttribute("teacher"));
                    request.getSession().setAttribute("message", "変更に失敗しました");
                    response.sendRedirect("student_update.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace(out);
            }
            Page.footer(out);
        }
    }
}
