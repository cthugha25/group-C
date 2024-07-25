package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.TestListStudent;
import bean.TestListSubject;

public class TestListSubjectDao extends DAO {

	// 入学年度全表示
	public List<TestListSubject> AllEntYear_test() throws Exception {
		List<TestListSubject> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct ENT_YEAR from STUDENT");
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			TestListSubject p=new TestListSubject();
			p.setEntYear(rs.getInt("ENT_YEAR"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

	// 入学年度全表示
	public List<TestListSubject> AllClassNum_test() throws Exception {
		List<TestListSubject> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct CLASS_NUM from TEST");
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			TestListSubject p=new TestListSubject();
			p.setClassNum(rs.getString("CLASS_NUM"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

	// 教科名全表示
	public List<TestListStudent> AllSubject_test() throws Exception {
		List<TestListStudent> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct NAME from SUBJECT");
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			TestListStudent p=new TestListStudent();
			p.setSubjectName(rs.getString("NAME"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

	// 回数全表示
	public List<TestListStudent> AllNo_test() throws Exception {
		List<TestListStudent> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct NO from TEST");
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			TestListStudent p=new TestListStudent();
			p.setNum(rs.getInt("NO"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

	public List<TestListSubject> test_subjectfilter(String f1, String f2, String f3) throws Exception{
		List<TestListSubject> list=new ArrayList<>();
		Connection con=getConnection();
		PreparedStatement st=con.prepareStatement("SELECT DISTINCT STUDENT.ENT_YEAR, TEST.CLASS_NUM, TEST.STUDENT_NO, STUDENT.NAME FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+f1+" and TEST.CLASS_NUM = '"+f2+"' and SUBJECT.NAME = '"+f3+"'");
		PreparedStatement max=con.prepareStatement("SELECT MAX(NO) AS NOM FROM TEST");

		ResultSet rs = st.executeQuery();
		ResultSet mx = max.executeQuery();

		int maxnum = 0;
		while(mx.next()){
			maxnum = mx.getInt("NOM");
		}
		int count;
		while(rs.next()){
			TestListSubject stu = new TestListSubject();
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			stu.setPoints(map);
			stu.setEntYear(rs.getInt("STUDENT.ENT_YEAR"));
			stu.setClassNum(rs.getString("TEST.CLASS_NUM"));
			stu.setStudentNo(rs.getString("TEST.STUDENT_NO"));
			stu.setStudentName(rs.getString("STUDENT.NAME"));
			PreparedStatement st2=con.prepareStatement("SELECT TEST.POINT, TEST.NO FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.NAME = '"+rs.getString("STUDENT.NAME")+"' ORDER BY TEST.NO");
			ResultSet rs2 = st2.executeQuery();
			count = 1;
			while(rs2.next()){
				if(count == rs2.getInt("TEST.NO")){
					stu.putPoint(rs2.getInt("TEST.NO"), rs2.getInt("TEST.POINT"));
					count++;
				}else if (count < rs2.getInt("TEST.NO")){
					while(count != rs2.getInt("TEST.NO")){
						stu.putPoint(count, 9999);
						count++;
					}
					stu.putPoint(rs2.getInt("TEST.NO"), rs2.getInt("TEST.POINT"));
					count++;
				}else{
					stu.putPoint(rs2.getInt("TEST.NO"), rs2.getInt("TEST.POINT"));
				}
			}
			while(count <= maxnum){
				stu.putPoint(count, 9999);
				count++;
			}
			list.add(stu);
		}
		st.close();
		con.close();

		return list;


	}

	public List<TestListStudent> test_num(String f1, String f2, String f3)throws Exception{
		List<TestListStudent> list=new ArrayList<>();
		Connection con=getConnection();
		PreparedStatement st=con.prepareStatement("SELECT DISTINCT TEST.NO FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+f1+" and TEST.CLASS_NUM = '"+f2+"' and SUBJECT.NAME = '"+f3+"'");
		ResultSet rs = st.executeQuery();

		while(rs.next()){
			TestListStudent stu = new TestListStudent();
			stu.setNum(rs.getInt("TEST.NO"));
			list.add(stu);
		}
		st.close();
		con.close();

		return list;

	}
}
