package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListAverage;

public class TestListAverageDao extends DAO {
	public List<TestListAverage> test_averagefilter(String year, String classnum, String num, String search, School school) throws Exception{
		List<TestListAverage> list=new ArrayList<>();
		Connection con=getConnection();
		String sql = "";
		if (search.equals("合計点") != true){
			sql += " AND SUBJECT.NAME = '"+search+"'";
		}
		System.out.println(year);
		PreparedStatement st=con.prepareStatement("SELECT STUDENT.ENT_YEAR, TEST.STUDENT_NO, STUDENT.NAME, SUM(TEST.POINT) AS POS FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+year+" and TEST.CLASS_NUM = '"+classnum+"' and TEST.NO = "+num+"" + sql + " AND STUDENT.SCHOOL_CD = '"+school.getCd()+"' GROUP BY TEST.STUDENT_NO");

		ResultSet rs = st.executeQuery();

		while(rs.next()){
			TestListAverage stu = new TestListAverage();
			stu.setEntYear(rs.getInt("STUDENT.ENT_YEAR"));
			stu.setStudentNo(rs.getString("TEST.STUDENT_NO"));
			stu.setStudentName(rs.getString("STUDENT.NAME"));
			stu.setPoints(rs.getInt("POS"));
			list.add(stu);
		}
		st.close();
		con.close();

		return list;


	}

	public int average(String year, String classnum, String num, String search, School school)throws Exception{
		Connection con=getConnection();
		String sql = "";
		if (search.equals("合計点") != true){
			sql += " AND SUBJECT.NAME = '"+search+"'";
		}
		PreparedStatement avg=con.prepareStatement("SELECT AVG(POS) as TSA FROM (SELECT SUM(TEST.POINT) AS POS FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+year+" and TEST.CLASS_NUM = '"+classnum+"' and TEST.NO = "+num+"" + sql + " AND STUDENT.SCHOOL_CD = '"+school.getCd()+"' GROUP BY TEST.STUDENT_NO)");
		ResultSet rs = avg.executeQuery();
		rs.next();
		int av = rs.getInt("TSA");
		avg.close();
		con.close();

		return av;

	}

	public int gradeaverage(String year, String num, String search, School school)throws Exception{
		Connection con=getConnection();
		String sql = "";
		if (search.equals("合計点") != true){
			sql += " AND SUBJECT.NAME = '"+search+"'";
		}
		PreparedStatement gavg=con.prepareStatement("SELECT AVG(POS) as POA FROM (SELECT SUM(TEST.POINT) AS POS FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+year+" and TEST.NO = "+num+"" + sql + " AND STUDENT.SCHOOL_CD = '"+school.getCd()+"' GROUP BY TEST.STUDENT_NO)");
		ResultSet rs = gavg.executeQuery();
		rs.next();
		int gav = rs.getInt("POA");
		gavg.close();
		con.close();

		return gav;

	}

	public List<TestListAverage> allsubject(String year, String num, School school) throws Exception{
		List<TestListAverage> subject=new ArrayList<>();
		Connection con=getConnection();
		PreparedStatement st=con.prepareStatement("SELECT DISTINCT SUBJECT.NAME FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+year+" and TEST.NO = "+num+" AND STUDENT.SCHOOL_CD = '"+school.getCd()+"'");

		ResultSet rs = st.executeQuery();

		while(rs.next()){
			TestListAverage stu = new TestListAverage();
			stu.setSubject(rs.getString("SUBJECT.NAME"));
			subject.add(stu);
		}
		st.close();
		con.close();

		return subject;


	}

}

