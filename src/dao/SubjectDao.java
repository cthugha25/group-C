package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends DAO {
	//	SUBJECTテーブルのデータを全て表示する
	public List<Subject> selectAllSubject(School school) throws Exception {
		// リスト初期化
		List<Subject> list=new ArrayList<>();
		// コネクション確率
		Connection con=getConnection();
		// プリペアードステートメントにSQL文セット
		PreparedStatement st=con.prepareStatement(
			"select * from SUBJECT "
			+ "where SCHOOL_CD=? "
			+ "order by CD");
		// プリペアードステートメントに学校コードをバインド
		st.setString(1, school.getCd());
		// プリペアードステートメント実行
		ResultSet rs=st.executeQuery();
		// リストに格納
		while (rs.next()) {
			Subject subject=new Subject();
			subject.setCd(rs.getString("CD"));
			subject.setName(rs.getString("NAME"));
			// 学校フィールドには学校コードで検索した学校インスタンスをセット
			subject.setSchool(rs.getString("SCHOOL_CD"));
			list.add(subject);
		}

		st.close();
		con.close();

		return list;
	}

	// 選択した科目情報表示
	public Subject get(String cd, School school) throws Exception {
		// 科目インスタンス初期化
		Subject subject = new Subject();
		// コネクションを確立
		Connection con=getConnection();

		// プリペアードステートメントにSQL文をセット
		PreparedStatement st=con.prepareStatement(
			"select CD, NAME from SUBJECT "
			+ "where CD=? and SCHOOL_CD=?");
		// 検索条件に科目コードと学校コードをバインド
		st.setString(1, cd);
		st.setString(2, school.getCd());
		// 実行
		ResultSet rs = st.executeQuery();

		// 科目インスタンスに検索結果セット
		rs.next();
		subject.setCd(rs.getString("CD"));
		subject.setName(rs.getString("NAME"));

		// プリペアードステートメントとコネクションを閉じる
		st.close();
		con.close();

		return subject;
	}

	// 選択した科目削除
	public boolean delete(String cd) throws Exception {
		// コネクションを確立
		Connection con=getConnection();
		// プリペアードステートメント
		PreparedStatement st = null;
		// 実行件数
		int count = 0;

		try  {
			// プリペアードステートメントにSQL文をセット
			st=con.prepareStatement(
				"delete from SUBJECT where CD=?");
			// 検索条件に科目コードをバインド
			st.setString(1, cd);
			// 実行
			count = st.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count == 1) {
			// 実行件数が1件ある場合
			return true;
		} else {
			return false;
		}
	}
}
