package student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.StudentDao;

//学生一覧の検索フォームの送信先
@WebServlet(urlPatterns={"/student/Student_filter"})
public class Student_filter extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// 在学フラグのデフォルトをtrue(在学中)にする
			boolean isAttend = true;
			// 学生リスト
			List<Student> list = null;
			// エラーメッセージ
			Map<String,String> errors = new HashMap<>();
			// Daoインスタンス化
			StudentDao dao=new StudentDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");

			// 入学年度、クラス、在学フラグの値受け取り
			int entYear=Integer.parseInt(request.getParameter("f1"));
			String classNum=request.getParameter("f2");
			String[] isAttendStr=request.getParameterValues("f3");

			// 在学チェックボックスがチェックされていなかった場合在学フラグをfalseにする
			if (isAttendStr == null) {
				isAttend = false;
			}

			// セレクトボックスに入学年度とクラス番号を表示するメソッド実行
			List<Student> ent_year_set=dao.AllEntYear(school);
			List<Student> class_num_set=dao.AllClassNum(school);

			// 実行する絞り込みメソッド分岐
			if (entYear != 0 && !classNum.equals("0")) {
				// 入学年度とクラス番号指定
				list = dao.filter(school, entYear, classNum, isAttend);
			} else if (entYear != 0 && classNum.equals("0")) {
				// 入学年度のみ指定
				list = dao.filter(school, entYear, isAttend);
			} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
				// 指定なしの場合
				// 全学年情報を取得
				list = dao.filter(school, isAttend);
			} else {
				// エラー
				errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
				request.setAttribute("errors", errors);
				// 全学年情報を取得
				list = dao.filter(school, isAttend);
			}

			// セレクトボックスに選択した値セット
			request.setAttribute("f1", entYear);
			request.setAttribute("f2", classNum);
			// セレクトボックスに選択肢セット
			request.setAttribute("ent_year_set", ent_year_set);
			request.setAttribute("class_num_set", class_num_set);

			// 在学フラグが送信されていた場合
			if (isAttendStr != null) {
				// 在学フラグを立てる
				isAttend = true;
				// リクエストに在学フラグをセット
				request.setAttribute("f3", isAttendStr);
			}

			// リクエストに学生リストをセット
			request.setAttribute("students", list);

			// 学生一覧にフォワード
			request.getRequestDispatcher("student_list.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
