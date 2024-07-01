package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListStudent;

public class TestListStudentDao extends DAO {
	// 学生番号を取得してその学生の成績一覧を表示
	public List<TestListStudent> filter(School school, String studentNo) throws Exception {
		// リスト初期化
		List<TestListStudent> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from TEST "
					+ "join SUBJECT on TEST.SUBJECT_CD=SUBJECT.CD "
					+ "join STUDENT on TEST.STUDENT_NO=STUDENT.NO "
					+ "where TEST.SCHOOL_CD=? and TEST.STUDENT_NO=?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに学生番号をバインド
			statement.setString(2, studentNo);
			// プリペアードステートメント実行
			rSet = statement.executeQuery();

			while (rSet.next()) {
				TestListStudent testList=new TestListStudent();
				testList.setSubjectName(rSet.getString("NAME"));
				testList.setSubjectCd(rSet.getString("SUBJECT_CD"));
				testList.setNum(rSet.getInt("NO"));
				testList.setPoint(rSet.getInt("POINT"));
				list.add(testList);
			}
		}catch(Exception e){
			throw e;
		}finally {
			// プリペアードステートメントを閉じる
			if(statement != null){
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if(connection != null){
				try {
					connection.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
		}
		return list;
	}
}
