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
import bean.TestListStudent;
import bean.TestListSubject;
import dao.StudentDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;

@WebServlet(urlPatterns={"/test/test_filter"})
public class Test_filter extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			if (request.getParameter("stu") != null){
				TestListSubjectDao dao=new TestListSubjectDao();
				String f1 = request.getParameter("f1");
				String f2 = request.getParameter("f2");
				String f3 = request.getParameter("f3");
				System.out.println(f1.equals("null"));
				if(f1.equals("null") != true && f2.equals("null") != true && f3.equals("null") != true){
					List<TestListSubject> list = dao.test_subjectfilter(f1, f2, f3);
					List<TestListStudent> numlist = dao.test_num(f1, f2, f3);
					List<TestListSubject> ent_year_set=dao.AllEntYear_test();
					List<TestListSubject> class_num_set=dao.AllClassNum_test();
					List<TestListStudent> subject_set=dao.AllSubject_test();
					List<TestListStudent> no_set=dao.AllNo_test();

					request.setAttribute("title", "(科目)");
					request.setAttribute("num", 1);
					request.setAttribute("subject", f3);
					request.setAttribute("list", list);
					request.setAttribute("numlist", numlist);
					request.setAttribute("ent_year_set", ent_year_set);
					request.setAttribute("class_num_set", class_num_set);
					request.setAttribute("subject_set", subject_set);
					request.setAttribute("no_set", no_set);

					request.getRequestDispatcher("test_list.jsp")
						.forward(request, response);
				}else{
					List<TestListSubject> ent_year_set=dao.AllEntYear_test();
					List<TestListSubject> class_num_set=dao.AllClassNum_test();
					List<TestListStudent> subject_set=dao.AllSubject_test();
					List<TestListStudent> no_set=dao.AllNo_test();

					request.setAttribute("title", "");
					request.setAttribute("num", 11);
					request.setAttribute("ent_year_set", ent_year_set);
					request.setAttribute("class_num_set", class_num_set);
					request.setAttribute("subject_set", subject_set);
					request.setAttribute("no_set", no_set);

					request.getRequestDispatcher("test_list.jsp")
						.forward(request, response);

				}
			}else{
				// 成績リスト
				List<TestListStudent> tests = null;
				// Daoインスタンス化
				TestListStudentDao dao=new TestListStudentDao();
				StudentDao stu_dao=new StudentDao();
				TestListSubjectDao sub_dao=new TestListSubjectDao();

				// ログインした教師の情報受け取り　後で変える！
				School school=new School();
				school.setCd("oom");
				school.setName("学校名");

				// 入力された学生番号受け取り
				String studentNo=request.getParameter("f4");

				// メソッド実行
				Student student = stu_dao.get(studentNo, school);	// 学生情報取得
				tests = dao.filter(student);		// 学生別成績参照
				// セレクトボックス用データ取得
				List<TestListSubject> ent_year_set=sub_dao.AllEntYear_test();
				List<TestListSubject> class_num_set=sub_dao.AllClassNum_test();
				List<TestListStudent> subject_set=sub_dao.AllSubject_test();

				// 値セット
				request.setAttribute("title", "(学生)");
				request.setAttribute("num", 2);
				request.setAttribute("tests", tests);
				request.setAttribute("student", student);
				request.setAttribute("ent_year_set", ent_year_set);
				request.setAttribute("class_num_set", class_num_set);
				request.setAttribute("subject_set", subject_set);


				// 学生一覧にフォワード
				request.getRequestDispatcher("test_list.jsp")
					.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}