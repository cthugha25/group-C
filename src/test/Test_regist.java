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
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TeacherDao;
import dao.TestDao;

// 成績管理の検索フォームの送信先
@WebServlet(urlPatterns={"/test/Test_regist"})
public class Test_regist extends HttpServlet {
	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// 成績登録したい生徒リスト:初期化
			List<Test> students = null;

			// Daoインスタンス化:初期化
			TestDao dao=new TestDao();
			StudentDao stu_dao=new StudentDao();
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

			// 入力された値を受け取り
			int entYear=Integer.parseInt(request.getParameter("f1"));
			String classNum=request.getParameter("f2");
			String subjectCd = request.getParameter("f3");
			int no =Integer.parseInt(request.getParameter("f4"));

			//セレクトボックス用データ取得
            List<Student> entYearSet = stu_dao.AllEntYear(school);
            List<Student> classNumSet = stu_dao.AllClassNum(school);
            List<Subject> subjectNameSet = sub_dao.selectAllSubject(school);
            System.out.println('c');


			request.setAttribute("ent_year_set", entYearSet);
			request.setAttribute("class_num_set", classNumSet);
            request.setAttribute("subject_name_set", subjectNameSet);

			//フォームの入力が不完全な時
			if (entYear ==0 || classNum.equals("0")|| subjectCd.equals("0") || no == 0) {
				request.setAttribute("errors", "入学年度とクラス、科目、回数を選択してください");
				request.setAttribute("all_info", students);
	            request.setAttribute("test_num", no);

				System.out.println("not parfect");

	            System.out.println("c2");

				request.getRequestDispatcher("test_regist.jsp")
				.forward(request, response);
			}

			// メソッド実行
			students = dao.filter(school, entYear,classNum,subjectCd,no);		// 成績登録したい生徒取得
            Subject subjectInfo = sub_dao.get(subjectCd, school);			// 科目情報取得
            List<Student> eaxm1 = stu_dao.filter(school, entYear, classNum, true); //学校コード,入学年度,クラス番号を取得
			System.out.println('c');

			// 値セット
            request.setAttribute("subject", subjectInfo);
            request.setAttribute("all_info", students);
            request.setAttribute("test_num", no);
            request.setAttribute("eaxm1", eaxm1); //eaxm1にeaxm１の中身を入れる

            // 成績管理にフォワード
			request.getRequestDispatcher("test_regist.jsp")
				.forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
