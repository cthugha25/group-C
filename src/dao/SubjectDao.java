package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			"select * from SUBJECT where SCHOOL_CD=?");
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
//			subject.setSchool(school);
			subject.setSchool(rs.getString("SCHOOL_CD"));
			list.add(subject);
		}

		st.close();
		con.close();

		return list;
	}
}
