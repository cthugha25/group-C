package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends DAO {
	public School get(String no) throws Exception {
		// 学校インスタンス初期化
		School school = new School();
		// コネクション確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from SCHOOL where CD=?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, no);
			// プリペアードステートメント実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学校インスタンスに検索結果をセット
				school.setCd(rSet.getString("CD"));
				school.setName(rSet.getString("NAME"));
			} else {
				// リザルトセットが存在しない場合
				// 学校インスタンスにnullをセット
				school = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return school;
	}
}
