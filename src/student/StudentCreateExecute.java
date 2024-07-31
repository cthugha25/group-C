package student;

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
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import dao.TeacherDao;
import tool.Page;

@WebServlet(urlPatterns={"/student/StudentCreateExecute"})
public class StudentCreateExecute extends HttpServlet {

	public void doGet (
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

			// エラーメッセージ
			Map<String,String> errors = new HashMap<>();

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

			// エラー時のデータを保持
			req.setAttribute("teacher", session.getAttribute("teacher"));
			req.setAttribute("ent_year", ent_Year);
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("hurigana", hurigana);
			req.setAttribute("classNum", class_Num);
			if (isAttend_str != null) {	// 在学フラグが送信されていた場合
				// 在学フラグを立てる
				isAttend = true;
				// リクエストに在学フラグをセット
				req.setAttribute("isAttend", isAttend_str);
			}
			req.setAttribute("gender", gender);

			// 入学年度が選択されていない場合はエラーメッセージ表示
			if (ent_Year==0) {
				errors.put("ent_year_error", "入学年度を選択してください");
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("StudentCreate")
					.forward(req, res);
			}else{

				// 学生番号が重複している場合はエラーメッセージ表示
				StudentDao dao = new StudentDao();
				if (dao.get(no, school)!=null) {
					errors.put("no_error", "学生番号が重複しています");
					req.setAttribute("errors", errors);
					req.getRequestDispatcher("StudentCreate")
						.forward(req, res);
				}

				Student student = new Student();
				student.setEntYear(ent_Year);
				student.setNo(no);
				student.setName(name);
				student.setClassNum(class_Num);
				student.setHurigana(hurigana);
				student.setGender(gender);
				student.setAttend(isAttend);

				int line = dao.insert(school,student);

				System.out.println(line);
				if (line>0) {
					out.println("登録に成功しました。");
				}
				req.setAttribute("teacher", session.getAttribute("teacher"));
				req.getRequestDispatcher("/student/student_join_completion.jsp").forward(req, res);
				Page.footer(out);
			}

		} catch (Exception e) {
			e.printStackTrace(out);
			System.out.print("エラー");
		}
	}
}
