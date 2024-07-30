package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends DAO {

    private String baseSql = "select "
    		+ "student.ent_year, test.class_num,subject.name, test.student_no, student.name, test.point,subject.cd,test.no "
    		+ "from test "
    		+ "join student on test.class_num = student.class_num "
    		+ "join subject on test.subject_cd = subject.cd "
    		+ "where test.no=?";

    public Test get(School school, Subject subject, Student student, int no) throws Exception {
        Test test = new Test();
        Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = con.prepareStatement(baseSql);
            statement.setInt(1, no);
            rSet = statement.executeQuery();

            if (rSet.next()) {
                student.setEntYear(rSet.getInt("ent_year"));
                test.setClassNum(rSet.getString("class_num"));
                subject.setName(rSet.getString("name"));
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                subject.setCd(rSet.getString("cd"));
            } else {
                test = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) {
                try {
                    rSet.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return test;
    }

    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        try {
            while (rSet.next()) {
            	//生徒インスタンス化
                Student student = new Student();
                //入学年度
                student.setEntYear(rSet.getInt("ent_year"));
                //クラス
                student.setClassNum(rSet.getString("class_num"));
                //生徒名
                student.setName(rSet.getString("name"));
                //学生番号
                student.setNo(rSet.getString("no"));
                //教科インスタンス化
                Subject subject = new Subject();
                //教科の科目名
                subject.setName(rSet.getString("name"));
                //テストインスタンス化
                Test test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                //点数
                test.setPoint(rSet.getInt("point"));
                //回数
                test.setNo(rSet.getInt("no"));
                test.setSchool(school);

                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    public List<Test> filter(School school, int entYear, String classNum, String subjectCd, int no) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(
            		"select "
            		+ "ent_year, student.class_num, student.no, student.name, test.point, test.subject_cd "
            		+ "from student "
            		+ "left join (select * from test where subject_cd=? and no=?) as test "
            		+ "on student.no=test.student_no "
            		+ "left join subject "
            		+ "on test.subject_cd=subject.cd "
            		+ "where ent_year=? "
            		+ "and student.class_num=? "
            		+ "and student.school_cd=?");


            statement.setString(1, subjectCd);
            statement.setInt(2, no);
            statement.setInt(3, entYear);
            statement.setString(4, classNum);
            statement.setString(5, school.getCd());

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) {
                try {
                    rSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return list;
    }

	public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();
        PreparedStatement insertStatement = null;
        PreparedStatement updateStatement = null;
        int insertCount = 0;
        int updateCount = 0;

        try {
            connection.setAutoCommit(false);

            insertStatement = connection.prepareStatement(
                    "insert into test(student_no, subject_cd, school_cd, no, point, class_num) "
                    + "values(?, ?, ?, ?, ?, ?)");
            updateStatement = connection.prepareStatement(
            		"update test set point=? "
            		+ "where student_no=? and subject_cd=? and school_cd=? and no=? and class_num=? ");

            for (Test test : list) {
            	boolean old = reget(test.getSchool(), test.getSubject(), test.getStudent(), test.getNo(),test.getClassNum());
                if (old == false) {
                    insertStatement.setString(1, test.getStudent().getNo());
                    insertStatement.setString(2, test.getSubject().getCd());
                    insertStatement.setString(3, test.getSchool().getCd());
                    insertStatement.setInt(4, test.getNo());
                    insertStatement.setInt(5, test.getPoint());
                    insertStatement.setString(6, test.getClassNum());
                    insertCount += insertStatement.executeUpdate();
                    System.out.println(insertCount);
                } else {
                	updateStatement.setInt(1, test.getPoint());
                    updateStatement.setString(2, test.getStudent().getNo());
                    updateStatement.setString(3, test.getSubject().getCd());
                    updateStatement.setString(4, test.getSchool().getCd());
                    updateStatement.setInt(5, test.getNo());
                    updateStatement.setString(6, test.getClassNum());
                    updateCount += updateStatement.executeUpdate();
                    System.out.println(updateCount);
                }
            }

            connection.commit(); // トランザクションをコミット
            connection.setAutoCommit(true);
            connection.close();
            return true;

        } catch (Exception e) {
            connection.rollback(); // エラー発生時にロールバック
            connection.setAutoCommit(true);
            connection.close();
            throw e;
        } finally {
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
    }


    public boolean delete(List<Test> list) throws Exception {
        Connection connection = getConnection();
        PreparedStatement deleteStatement = null;
        int deleteCount = 0;

        try {
            connection.setAutoCommit(false); // トランザクションを開始

            deleteStatement = connection.prepareStatement("DELETE FROM test WHERE no = ?");

            for (Test test : list) {
                deleteStatement.setInt(1, test.getNo());
                deleteCount += deleteStatement.executeUpdate();
            }

            connection.commit(); // トランザクションをコミット

        } catch (Exception e) {
            connection.rollback(); // エラー発生時にロールバック
            throw e;
        } finally {
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return deleteCount == list.size();
    }

    public List<Test> insert(Test test) throws Exception {
        // Testオブジェクトリストを初期化
        List<Test> list = new ArrayList<>();
        // Studentをインスタンス化
        Student student = test.getStudent();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection(); // データベース接続を取得
            String sql = "UPDATE test "
                       + "SET point = point"
                       + "from test as ts"
                       + "join student as stu"
                       + "on "
                       + " "
                       + "WHERE student_no = ? "
                       + "AND subject_cd = ? "
                       + "AND school_cd = ? "
                       + "AND no = ? "
                       + "AND class_num = ?";

            // SQL更新文
            statement = connection.prepareStatement(sql);
            // 点数
            statement.setInt(1, test.getPoint());
            // 学生番号
            statement.setString(2, student.getNo());
            statement.setInt(3, test.getStudent().getEntYear());
            // クラス
            statement.setString(6, test.getClassNum());

            int line = statement.executeUpdate(); // SQL文を実行し、影響を受けた行数を取得
            if (line > 0) {
                list.add(test); // 更新が成功した場合、リストに `Test` オブジェクトを追加
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // エラー発生時に例外を投げる
            throw new Exception("データベースへの登録中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
            if (statement != null) {
                try {
                    statement.close(); // PreparedStatementを閉じる
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close(); // Connectionを閉じる
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list; // リストを返す
    }

    public boolean nullget(HttpServletRequest request,String infoName){
    	String check = request.getParameter(infoName);
    return check != null && !check.trim().isEmpty();
    }

    public boolean reget(School school, Subject subject, Student student, int no,String class_num) throws Exception {
        Connection connection = getConnection();
        PreparedStatement regetstatement = null;
        ResultSet rSet = null;
        boolean exists = false; // デフォルトはデータが存在しないとする

        try {
            connection.setAutoCommit(false);

            String sql = "SELECT * FROM test " +
                         "WHERE student_no = ? " +
                         "AND subject_cd = ? " +
                         "AND school_cd = ? " +
                         "AND no = ? " +
                         "AND class_num = ?; ";

            regetstatement = connection.prepareStatement(sql);

            regetstatement.setString(1, student.getNo());
            regetstatement.setString(2, subject.getCd());
            regetstatement.setString(3, school.getCd());
            regetstatement.setInt(4, no);
            regetstatement.setString(5,class_num);

            rSet = regetstatement.executeQuery();

            if (rSet.next()) {
            	// データが存在する
                exists = true;
            }else{
            	return exists;
            }
            System.out.println(exists);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            if (rSet != null) {
                rSet.close();
            }
            if (regetstatement != null) {
                regetstatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return exists;
    }

}

