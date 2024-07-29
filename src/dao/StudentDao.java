package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;


public class StudentDao extends DAO {
	private String baseSql = "select * from STUDENT where SCHOOL_CD=?";

	// 学生番号を指定して学生インスタンスを1件取得する
	public Student get(String no, School school) throws Exception {
		// 学生インスタンス初期化
		Student student = new Student();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from STUDENT where NO=? and SCHOOL_CD=?");
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, no);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(2, school.getCd());
			// プリペアードステートメント実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("NO"));
				student.setName(rSet.getString("NAME"));
				student.setEntYear(rSet.getInt("ENT_YEAR"));
				student.setClassNum(rSet.getString("CLASS_NUM"));
				student.setAttend(rSet.getBoolean("IS_ATTEND"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(rSet.getString("SCHOOL_CD")));
			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				student = null;
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

		return student;
	}

	// フィルター後のリストへの格納処理を行う
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		// リスト初期化
		List<Student> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (rSet.next()){
				// 学生インスタンス初期化
				Student student = new Student();
				// 学生インスタンスに検索結果セット
				student.setNo(rSet.getString("NO"));
				student.setName(rSet.getString("NAME"));
				student.setEntYear(rSet.getInt("ENT_YEAR"));
				student.setClassNum(rSet.getString("CLASS_NUM"));
				student.setAttend(rSet.getBoolean("IS_ATTEND"));
				student.setSchool(school);
				student.setGender(rSet.getString("GENDER"));
				student.setHurigana(rSet.getString("HURIGANA"));
				// リストに追加
				list.add(student);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return list;
	}

	// 学校、入学年度、クラス番号、在学フラグを指定して学生一覧を取得する
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		// リスト初期化
		List<Student> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year=? and class_num=?";
		// SQL文のソート
		String order = " order by no asc";

		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if(isAttend) {
			conditionIsAttend = "and is_attend=true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			// プリペアードステートメント実行
			rSet = statement.executeQuery();
			// リストへの格納処理実行
			list = postFilter(rSet, school);
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

	// 学校、入学年度、在学フラグを指定して学生一覧を取得する
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		// リスト初期化
		List<Student> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year=? ";
		// SQL文のソート
		String order = " order by no asc";

		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if(isAttend) {
			conditionIsAttend = "and is_attend=true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメント実行
			rSet = statement.executeQuery();
			// リストへの格納処理実行
			list = postFilter(rSet, school);
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

	// 学校、在学フラグを指定して学生一覧を取得する
	// 初期表示はこれ実行
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		// リスト初期化
		List<Student> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の条件
		String order = " order by no asc";

		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if(isAttend) {
			conditionIsAttend = "and is_attend=true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメント実行
			rSet = statement.executeQuery();
			// リストへの格納処理実行
			list = postFilter(rSet, school);
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

	// 入学年度全表示
	public List<Student> AllEntYear(School school) throws Exception {
		// リスト初期化
		List<Student> list=new ArrayList<>();
		// コネクション確率
		Connection con=getConnection();
		// プリペアードステートメントにSQL文セット
		PreparedStatement st=con.prepareStatement(
			"select distinct ENT_YEAR from STUDENT "
			+ "where SCHOOL_CD=? "
			+ "order by ENT_YEAR");
		// プリペアードステートメントに学校コードをバインド
		st.setString(1, school.getCd());
		// プリペアードステートメント実行
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Student student=new Student();
			student.setEntYear(rs.getInt("ENT_YEAR"));
			list.add(student);
		}

		st.close();
		con.close();

		return list;
	}

	// クラス番号全表示
	public List<Student> AllClassNum(School school) throws Exception {
		// リスト初期化
		List<Student> list=new ArrayList<>();
		// コネクション確率
		Connection con=getConnection();
		// プリペアードステートメントにSQL文セット
		PreparedStatement st=con.prepareStatement(
			"select distinct CLASS_NUM from STUDENT "
			+ "where SCHOOL_CD=? "
			+ "order by CLASS_NUM");
		// プリペアードステートメントに学校コードをバインド
		st.setString(1, school.getCd());
		// プリペアードステートメント実行
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Student student=new Student();
			student.setClassNum(rs.getString("CLASS_NUM"));
			list.add(student);
		}

		st.close();
		con.close();

		return list;
	}

	// 特定の学生のデータを取得する
    public Student getStudent(String studentCd) throws Exception {
        Student student = new Student();
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
        		"select NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND from STUDENT where NO=?");
        st.setString(1, studentCd);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            student.setNo(rs.getString("NO"));
            student.setName(rs.getString("NAME"));
            student.setEntYear(rs.getInt("ENT_YEAR"));
            student.setClassNum(rs.getString("CLASS_NUM"));
            student.setAttend(rs.getBoolean("IS_ATTEND"));
        }
        st.close();
        con.close();
        return student;
    }

	 // 学生のデータを更新する
	public int update(Student stu) throws Exception {
		Connection con= getConnection();

		PreparedStatement st=con.prepareStatement(
				"update STUDENT set NAME=?, ENT_YEAR=?, CLASS_NUM=?, IS_ATTEND=? where NO=?");
		st.setString(1, stu.getName());
		st.setInt(2, stu.getEntYear());
		st.setString(3, stu.getClassNum());
		st.setBoolean(4, stu.isAttend());
		st.setString(5, stu.getNo());

		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}

	public int insert(School school,Student student) throws Exception {
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
				"insert into STUDENT "
				+ "(NO,NAME,ENT_YEAR,CLASS_NUM,HURIGANA,IS_ATTEND,GENDER,SCHOOL_CD)"
				+ "values(?,?,?,?,?,?,?,?)");
		st.setString(1, student.getNo());
		st.setString(2, student.getName());
		st.setInt(3, student.getEntYear());
		st.setString(4, student.getClassNum());
		st.setString(5, student.getHurigana());
		st.setBoolean(6, student.isAttend());
		st.setString(7, student.getGender());
		st.setString(8, school.getCd());

		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}
}
