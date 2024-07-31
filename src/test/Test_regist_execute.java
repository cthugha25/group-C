package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import dao.TestDao;
import tool.Page;

@WebServlet(urlPatterns={"/test/Test_regist_execute"})
public class Test_regist_execute extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Page.header(out);
        try {
            List<Test> tests = new ArrayList<>();

         // 学生番号
            String[] student_no = request.getParameterValues("student_no");
         // 教科コード
            String[] subject_cd = request.getParameterValues("f3");
         // 学校コード
            String[] school_cd = request.getParameterValues("school_cd");
         // 回数
            String[] noArray = request.getParameterValues("f4");
         // 点数
            String[] self_points = request.getParameterValues("point");
         // クラス番号
            String[] class_num = request.getParameterValues("class_Num");

         // 配列を整数型に変換
         int length = student_no.length;
         int[] noArrayInt = new int[length];
         int[] self_pointsInt = new int[length];

         for (int i = 0; i < length; i++) {
             noArrayInt[i] = Integer.parseInt(noArray[i]);
             self_pointsInt[i] = Integer.parseInt(self_points[i]);
         }

         // ループ
         for (int i = 0; i < length; i++) {
             int point = self_pointsInt[i];
             int no = noArrayInt[i];

             // インスタンス化
             Student student = new Student();
             Subject subject = new Subject();
             School school = new School();
             Test test = new Test();

             // テストインスタンス化
             test.setStudent(student);       	// 学生
             test.setSubject(subject);      	// 教科
             test.setSchool(school);        	// 学校

             student.setNo(student_no[i]);  	// 学生番号
             subject.setCd(subject_cd[i]); 		// 教科コード
             school.setCd(school_cd[i]);   		// 学校コード
             test.setNo(no);               		// 回数
             test.setPoint(point);         		// 点数
             test.setClassNum(class_num[i]); 	// クラス番号

             // テストオブジェクトをリストに追加
             tests.add(test);

         }
            TestDao testDao = new TestDao();

            boolean Success = testDao.save(tests);
            System.out.println(Success);

            String forward = Success ?  "test_regist_done.jsp" : "test_regist_erroe.jsp";

            // 登録結果を表示するJSPページにフォワード
            request.getRequestDispatcher(forward).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("データベース登録中にエラーが発生しました。");
        }finally{
        	 Page.footer(out);
        }
    }
}
