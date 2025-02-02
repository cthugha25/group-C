package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;

public class SubjectDao extends DAO {
	//	SUBJECTテーブルのデータを全て表示する

	public void save(Subject subject) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            st = con.prepareStatement(
                "INSERT INTO SUBJECT (CD, NAME) VALUES (?, ?)");
            st.setString(1, subject.getCode());
            st.setString(2, subject.getName());
            st.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
    }


	public int insertSubject(School school, String subjectCode, String subjectName, Teacher teacher) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = null;
        int count = 0;

        try {
            st = con.prepareStatement(
                "insert into SUBJECT (SCHOOL_CD, CD, NAME, TEACHER_ID) values (?, ?, ?, ?)");
            st.setString(1, school.getCd());
            st.setString(2, subjectCode);
            st.setString(3, subjectName);
            st.setString(4, teacher.getId()); // TeacherクラスにgetIdメソッドが存在すると仮定
            count = st.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return count;
    }

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
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
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
			if (rs.next()) {
				subject.setCd(rs.getString("CD"));
				subject.setName(rs.getString("NAME"));
			}else {
				subject = null;
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

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

	// 科目のデータを更新する
    public int update(Subject subject) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement("update SUBJECT set NAME=? where CD=?");
        st.setString(1, subject.getName());
        st.setString(2, subject.getCd());
        int linesUpdated = st.executeUpdate();
        st.close();
        con.close();
        return linesUpdated;
    }
}
