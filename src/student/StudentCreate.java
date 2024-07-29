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
import dao.ClassNumDao;
import tool.Page;

@WebServlet(urlPatterns={"/student/StudentCreate"})
public class StudentCreate extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession();
        Page.header(out);

        try {
//        	学生番号
            String no = req.getParameter("no");

//          名前
            String name = req.getParameter("name");

//			ふりがな
            String hurigana = req.getParameter("hurigana");

//          性別
            String gender = req.getParameter("gender");

//          入学年度のリスト作成
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            List<Integer> entYears = new ArrayList<>();
            for (int year = currentYear-10; year <= currentYear+10; year++) {
                entYears.add(year);
            }

//          クラス番号
//          String classNum = req.getParameter("classNum");
            ClassNumDao classNumDao = new ClassNumDao();
            List<Class_num> classnumms = classNumDao.getAllClassNums();

            // エンティティ年の取得と変換
            int entYear = Integer.parseInt(req.getParameter("entYear"));
            Boolean isAttend = entYear > 3;

            if (isAttend) {
                out.println("○");
            } else {
                out.println("×");
            }

//			jspに渡すための属性を設定
            req.setAttribute("teacher", session.getAttribute("teacher"));
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("no", no);
            req.setAttribute("entYears", entYears);
            req.setAttribute("classnumms", classnumms);
            req.setAttribute("hurigana", hurigana);
            req.setAttribute("gender", gender);
            req.setAttribute("isAttend", isAttend ? "○" : "×");

        } catch (Exception e) {
            e.printStackTrace(out);
            System.out.println("エラー");
        }

        req.getRequestDispatcher("/student/student_join.jsp").forward(req, res);
        Page.footer(out);
    }
}
