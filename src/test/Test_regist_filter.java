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
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TeacherDao;

@WebServlet(urlPatterns={"/test/Test_regist_filter"})
public class Test_regist_filter extends HttpServlet {

    public void doGet(
    		HttpServletRequest request, HttpServletResponse response
    		)throws ServletException, IOException {
    		PrintWriter out = response.getWriter();

        try {
        	// Daoインスタンス化
            StudentDao dao = new StudentDao();
            SubjectDao sub_dao = new SubjectDao();

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

            // 学年、クラス番号、教科名、回数のリストを取得
            List<Student> entYearSet = dao.AllEntYear(school);
            List<Student> classNumSet = dao.AllClassNum(school);
            List<Subject> subjectNameSet = sub_dao.selectAllSubject(school);

            // リクエストにセット
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classNumSet);
            request.setAttribute("subject_name_set", subjectNameSet);

            // 学生一覧にフォワード
        	request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        } catch (Exception e) {
            // ログに記録し、ユーザーにメッセージ表示
            e.printStackTrace(out);
        }
    }
}