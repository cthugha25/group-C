package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class TeacherDao extends DAO {
	// 教師情報取得
	public Teacher get(String id) throws Exception {
		// 教師インスタンス初期化
		Teacher teacher = new Teacher();
		// コネクションを確立
		Connection con=getConnection();

		// プリペアードステートメントにSQL文をセット
		PreparedStatement st=con.prepareStatement(
			"select * from TEACHER where ID=?");
		// 検索条件に学校コードをバインド
		st.setString(1, id);
		// プリペアードステートメント実行
		ResultSet rs = st.executeQuery();

		// 教師インスタンスに検索結果セット
		rs.next();
		teacher.setId(rs.getString("ID"));
		teacher.setName(rs.getString("NAME"));

		// プリペアードステートメントとコネクションを閉じる
		st.close();
		con.close();

		return teacher;
	}


	// ログインメソッド
	public Teacher login(String id, String password) throws Exception {
		// 教師インスタンスの初期化
		Teacher teacher=null;
		// コネクション確率
		Connection con=getConnection();

		// プリペアードステートメントにSQL文をセット
		PreparedStatement st=con.prepareStatement(
				"select * from TEACHER where ID=? and PASSWORD=?");
		// プリペアードステートメントにIDをバインド
		st.setString(1, id);
		// プリペアードステートメントにパスワードをバインド
		st.setString(2, password);
		// プリペアードステートメント実行
		ResultSet rs=st.executeQuery();

		// 学校Daoを初期化
		SchoolDao schoolDao = new SchoolDao();

		while (rs.next()) {
			teacher=new Teacher();
			teacher.setId(rs.getString("ID"));
			teacher.setPassword(rs.getString("PASSWORD"));
			teacher.setName(rs.getString("NAME"));
			// 学校フィールドにはログインした教師が所属する学校コードをセット
			teacher.setSchool(schoolDao.get(rs.getString("SCHOOL_CD")));
		}

		// プリペアードステートメントとコネクションを閉じる
		st.close();
		con.close();

		return teacher;
	}
}
