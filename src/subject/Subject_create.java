package subject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;

@WebServlet("/subject/Subject_create")
public class Subject_create extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Subject_create.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("subject_join.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");

        Subject subject = new Subject();
        subject.setCode(subjectCode);
        subject.setName(subjectName);

        SubjectDao subjectDao = new SubjectDao();

        try {
            subjectDao.save(subject);
            request.setAttribute("message", "科目情報が登録されました。");
            request.getRequestDispatcher("subject_join.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while saving subject information", e);
            request.setAttribute("error", "科目情報の登録中にエラーが発生しました。詳細: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
