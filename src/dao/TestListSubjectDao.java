package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.TestListStudent;
import bean.TestListSubject;

public class TestListSubjectDao extends DAO {

	// 入学年度全表示
	public List<TestListSubject> AllEntYear_test(School school) throws Exception {
		List<TestListSubject> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct ENT_YEAR from STUDENT WHERE SCHOOL_CD = '"+school.getCd()+"' ORDER BY ENT_YEAR");
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

	// クラス全表示
	public List<TestListSubject> AllClassNum_test(School school) throws Exception {
		List<TestListSubject> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct CLASS_NUM from STUDENT WHERE SCHOOL_CD = '"+school.getCd()+"' ORDER BY CLASS_NUM");
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
	public List<TestListStudent> AllSubject_test(School school) throws Exception {
		List<TestListStudent> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select distinct NAME, CD from SUBJECT WHERE SCHOOL_CD = '"+school.getCd()+"' ORDER BY CD");
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


	public List<TestListSubject> test_subjectfilter(String f1, String f2, String f3, School school) throws Exception{
		List<TestListSubject> list=new ArrayList<>();
		Connection con=getConnection();
		PreparedStatement st=con.prepareStatement("SELECT DISTINCT STUDENT.ENT_YEAR, TEST.CLASS_NUM, TEST.STUDENT_NO, STUDENT.NAME FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.ENT_YEAR = "+f1+" and TEST.CLASS_NUM = '"+f2+"' and SUBJECT.NAME = '"+f3+"' and STUDENT.SCHOOL_CD = '"+school.getCd()+"'");

		ResultSet rs = st.executeQuery();

		while(rs.next()){
			TestListSubject stu = new TestListSubject();
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			stu.setPoints(map);
			stu.setEntYear(rs.getInt("STUDENT.ENT_YEAR"));
			stu.setClassNum(rs.getString("TEST.CLASS_NUM"));
			stu.setStudentNo(rs.getString("TEST.STUDENT_NO"));
			stu.setStudentName(rs.getString("STUDENT.NAME"));
			PreparedStatement st2=con.prepareStatement("SELECT TEST.POINT, TEST.NO FROM TEST JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO JOIN SUBJECT ON SUBJECT.CD = TEST.SUBJECT_CD WHERE STUDENT.NAME = '"+rs.getString("STUDENT.NAME")+"' AND STUDENT.SCHOOL_CD = '"+school.getCd()+"' ORDER BY TEST.NO");
			ResultSet rs2 = st2.executeQuery();
			int count = 1;
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
			while(count <= 2){
				stu.putPoint(count, 9999);
				count++;
			}
			list.add(stu);
		}
		st.close();
		con.close();

		return list;


	}

}
