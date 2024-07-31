package student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Class_num;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.TeacherDao;
import tool.Page;

@WebServlet(urlPatterns={"/student/StudentCreate"})
public class StudentCreate extends HttpServlet {

    public void doGet(
		HttpServletRequest req, HttpServletResponse res
	) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        Page.header(out);
        try {
        	// ログイン状態チェック　未ログインならログインページに
			HttpSession session=req.getSession();
			if (session.getAttribute("teacher")==null) {
				String error="ログアウト状態ではシステムを使用できません。";
				req.setAttribute("error",error);
				req.getRequestDispatcher("../login/login.jsp")
					.forward(req, res);
			}

			// ログイン中の教師情報を取得して学校コードをセット
			TeacherDao teacher_dao=new TeacherDao();
			Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
			School school=new School();
			school.setCd(teacher.getSchool().getCd());

//          入学年度のリスト作成
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            List<Integer> entYears = new ArrayList<>();
            for (int year = currentYear-10; year <= currentYear+10; year++) {
                entYears.add(year);
            }

//          クラス番号
            ClassNumDao classNumDao = new ClassNumDao();
            List<Class_num> classnumms = classNumDao.getAllClassNums(school);
//            StudentDao dao=new StudentDao();
//            List<Student> class_num_set=dao.AllClassNum(school);

//			jspに渡すための属性を設定
            req.setAttribute("teacher", session.getAttribute("teacher"));
            req.setAttribute("entYears", entYears);
            req.setAttribute("classnumms", classnumms);
//            req.setAttribute("class_num_set", class_num_set);

            req.getRequestDispatcher("/student/student_join.jsp")
            	.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace(out);
            System.out.println("エラー");
        }
    }
}
