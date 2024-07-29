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
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.StudentDao;
import dao.TeacherDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;

@WebServlet(urlPatterns={"/test/test_filter"})
public class Test_filter extends HttpServlet {

	public void doGet (
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			HttpSession session=request.getSession();
			if (session.getAttribute("teacher")==null) {
				String error="ログアウト状態ではシステムを使用できません。";
				request.setAttribute("error",error);
				request.getRequestDispatcher("../login/login.jsp")
					.forward(request, response);
			}
			if (request.getParameter("sj") != null){
				TeacherDao teacher_dao=new TeacherDao();
				Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
				School school=new School();
				school.setCd(teacher.getSchool().getCd());
				TestListSubjectDao dao=new TestListSubjectDao();
				String f1 = request.getParameter("f1");
				String f2 = request.getParameter("f2");
				String f3 = request.getParameter("f3");
				System.out.println(f1.equals("null"));
				if(f1.equals("null") != true && f2.equals("null") != true && f3.equals("null") != true){
					List<TestListSubject> list = dao.test_subjectfilter(f1, f2, f3, school);
					List<TestListStudent> numlist = dao.test_num(f1, f2, f3, school);
					List<TestListSubject> ent_year_set=dao.AllEntYear_test(school);
					List<TestListSubject> class_num_set=dao.AllClassNum_test(school);
					List<TestListStudent> subject_set=dao.AllSubject_test(school);
					List<TestListStudent> no_set=dao.AllNo_test(school);

					request.setAttribute("title", "(科目)");
					request.setAttribute("num", 1);
					request.setAttribute("year", f1);
					request.setAttribute("classnum", f2);
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
					List<TestListSubject> ent_year_set=dao.AllEntYear_test(school);
					List<TestListSubject> class_num_set=dao.AllClassNum_test(school);
					List<TestListStudent> subject_set=dao.AllSubject_test(school);
					List<TestListStudent> no_set=dao.AllNo_test(school);

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
				TeacherDao teacher_dao=new TeacherDao();
				Teacher teacher=teacher_dao.get(session.getAttribute("id").toString());
				School school=new School();
				school.setCd(teacher.getSchool().getCd());
				// 成績リスト
				List<TestListStudent> tests = null;
				// Daoインスタンス化
				TestListStudentDao dao=new TestListStudentDao();
				StudentDao stu_dao=new StudentDao();
				TestListSubjectDao sub_dao=new TestListSubjectDao();

				// 入力された学生番号受け取り
				String studentNo=request.getParameter("f4");

				// メソッド実行
				// セレクトボックス用データ取得
				List<TestListSubject> ent_year_set=sub_dao.AllEntYear_test(school);
				List<TestListSubject> class_num_set=sub_dao.AllClassNum_test(school);
				List<TestListStudent> subject_set=sub_dao.AllSubject_test(school);
				Student student = stu_dao.get(studentNo, school);	// 学生情報取得

				// 学生が存在しない場合
				if (student == null) {
					// 値セット
					request.setAttribute("title", "(学生)");
					request.setAttribute("num", 2);
					request.setAttribute("student", student);
					request.setAttribute("ent_year_set", ent_year_set);
					request.setAttribute("class_num_set", class_num_set);
					request.setAttribute("subject_set", subject_set);

					// 学生一覧にフォワード
					request.getRequestDispatcher("test_list.jsp")
						.forward(request, response);
				}

				tests = dao.filter(student);		// 学生別成績参照

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