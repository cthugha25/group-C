package student;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

//学生一覧の検索フォームの送信先
@WebServlet(urlPatterns={"/student/Student_filter"})
public class Student_filter extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
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

			// 在学フラグのデフォルトをtrue(在学中)にする
			boolean isAttend = true;
			// 学生リスト
			List<Student> list = null;
			// エラーメッセージ
			Map<String,String> errors = new HashMap<>();

			// 入学年度、クラス、在学フラグの値受け取り
			int entYear=Integer.parseInt(request.getParameter("f1"));
			String classNum=request.getParameter("f2");
			String isAttendStr=request.getParameter("f3");

			// 在学チェックボックスがチェックされていなかった場合在学フラグをfalseにする
			if (isAttendStr == null) {
				isAttend = false;
			}

			// 入学年度とクラス番号を表示するメソッド実行
			StudentDao dao=new StudentDao();
			List<Student> ent_year_set=dao.AllEntYear(school);
			List<Student> class_num_set=dao.AllClassNum(school);

			// リクエストに値をセット
			request.setAttribute("teacher", session.getAttribute("teacher"));
			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);

			// 実行するfilterメソッド分岐
			if (entYear != 0 && !classNum.equals("0")) {
				// 入学年度とクラス番号指定
				list = dao.filter(school, entYear, classNum, isAttend);
			} else if (entYear != 0 && classNum.equals("0")) {
				// 入学年度のみ指定
				list = dao.filter(school, entYear, isAttend);
			} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
				// 指定なしの場合
				// 全学年情報を取得
				list=dao.filter(school);
				request.setAttribute("f3", "");
				request.setAttribute("students", list);
				if (isAttend==true) {
					list = dao.filter(school, isAttend);
				} else if (isAttend==false) {
					list = dao.filter(school, isAttend);
				}
			} else {
				// エラー
				errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
				request.setAttribute("errors", errors);
				// デフォルトの学生情報を取得
				list=dao.filter(school);
				request.setAttribute("f3", "");
				request.setAttribute("students", list);
			}

			// リクエストに値をセット
			request.setAttribute("teacher", session.getAttribute("teacher"));
			request.setAttribute("f1", entYear);
			request.setAttribute("f2", classNum);
			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);
			if (isAttendStr != null) {	// 在学フラグが送信されていた場合
				// 在学フラグを立てる
				isAttend = true;
				// リクエストに在学フラグをセット
				request.setAttribute("f3", isAttendStr);
			}
			request.setAttribute("students", list);

			// 学生一覧にフォワード
			request.getRequestDispatcher("student_list.jsp")
				.forward(request, response);

		} catch (Exception e) {
			// エラーページにフォワード
			request.getRequestDispatcher("../common/error.jsp")
				.forward(request, response);
		}
	}
}
