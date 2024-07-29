package student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import dao.TeacherDao;
import tool.Page;

@WebServlet(urlPatterns={"/student/StudentCreateExecute"})
public class StudentCreateExecute extends HttpServlet {

	public void doPost (
		HttpServletRequest req, HttpServletResponse res
	) throws ServletException, IOException {
		PrintWriter out=res.getWriter();
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

			//入学年度
			int ent_Year = Integer.parseInt(req.getParameter("ent_year"));
			//学生番号
			String no=req.getParameter("no");
			//氏名
			String name = req.getParameter("name");
			//クラス番号
			String class_Num = req.getParameter("classNum");
			//ふりがな
			String hurigana = req.getParameter("hurigana");
//			性別
			String gender = req.getParameter("gender");
//			在学状況
			boolean isAttend = true;
			String isAttend_str = req.getParameter("isAttend");
			if (isAttend_str == null) {
				isAttend = false;
			}

			Student student = new Student();
			student.setEntYear(ent_Year);
			student.setNo(no);
			student.setName(name);
			student.setClassNum(class_Num);
			student.setHurigana(hurigana);
			student.setGender(gender);
			student.setAttend(isAttend);


			StudentDao dao = new StudentDao();
			int line = dao.insert(school,student);


			System.out.println(line);
			if (line>0) {
				out.println("登録に成功しました。");
			}
			req.setAttribute("teacher", session.getAttribute("teacher"));
			req.getRequestDispatcher("/student/student_join_completion.jsp").forward(req, res);
			Page.footer(out);

		} catch (Exception e) {
			e.printStackTrace(out);
			System.out.print("エラー");
		}
	}
}
