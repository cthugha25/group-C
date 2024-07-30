package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;

// 成績管理の検索フォームの送信先
@WebServlet(urlPatterns={"/test/Test_regist"})
public class Test_regist extends HttpServlet {
	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			// 成績登録したい生徒リスト
			List<Test> students = null;
			// エラーメッセージ
			//Map<String,String> errors = new HashMap<>();
			// Daoインスタンス化
			TestDao dao=new TestDao();
			StudentDao stu_dao=new StudentDao();
			SubjectDao sub_dao = new SubjectDao();

			// ログインした教師の情報受け取り　後で変える！
			School school=new School();
			school.setCd("oom");

			// 入力された値を受け取り
			int entYear=Integer.parseInt(request.getParameter("f1"));
			String classNum=request.getParameter("f2");
			String subjectCd = request.getParameter("f3");
			int no =Integer.parseInt(request.getParameter("f4"));

			// メソッド実行
			students = dao.filter(school, entYear,classNum,subjectCd,no);		// 成績登録したい生徒取得
            Subject subjectInfo = sub_dao.get(subjectCd, school);			// 科目情報取得
            List<Student> eaxm1 = stu_dao.filter(school, entYear, classNum, true); //学校コード,入学年度,クラス番号を取得


			// セレクトボックス用データ取得
            List<Student> entYearSet = stu_dao.AllEntYear(school);
            List<Student> classNumSet = stu_dao.AllClassNum(school);
            List<Subject> subjectNameSet = sub_dao.selectAllSubject(school);

			// 値セット
			request.setAttribute("ent_year_set", entYearSet);
			request.setAttribute("class_num_set", classNumSet);
            request.setAttribute("subject_name_set", subjectNameSet);
            request.setAttribute("subject", subjectInfo);
            request.setAttribute("all_info", students);
            request.setAttribute("test_num", no);
            request.setAttribute("eaxm1", eaxm1); //eaxm1にeaxm１の中身を入れる

            // 成績管理にフォワード
			request.getRequestDispatcher("test_regist.jsp")
				.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
