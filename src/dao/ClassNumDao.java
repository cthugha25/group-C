package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Class_num;
import bean.School;

public class ClassNumDao extends DAO {

    // 全てのクラス番号を取得するSQL
    private String selectAllSql = "select distinct CLASS_NUM from STUDENT where SCHOOL_CD=? order by CLASS_NUM";

    // 全てのクラス番号を取得するメソッド
	public List<Class_num> getAllClassNums(School school) throws Exception {
		List<Class_num> list=new ArrayList<>();
		Connection con=getConnection();
		PreparedStatement st = null;
		try {
			st=con.prepareStatement(selectAllSql);
			st.setString(1, school.getCd());
			ResultSet rs=st.executeQuery();
			list=postFilter(rs);
		}catch(Exception e){
			throw e;
		}finally {
			// プリペアードステートメントを閉じる
			if(st != null){
				try{
					st.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if(con != null){
				try {
					con.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
		}
		return list;
	}

	// 結果セットからクラス番号リストを作成するメソッド
	private List<Class_num> postFilter(ResultSet rSet) throws SQLException {
	    List<Class_num> list = new ArrayList<>();
	    try {
	        while (rSet.next()) {
	            Class_num classnum = new Class_num();
	            classnum.setClassNum(rSet.getString("class_num"));
	            list.add(classnum);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("エラー");
	        throw e;
	    }

	    return list;
	}
}
