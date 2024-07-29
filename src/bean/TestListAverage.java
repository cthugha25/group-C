package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestListAverage implements Serializable {
	private int entYear;
	private String studentNo;
	private String studentName;
	private int points;
	private Map<String, Integer> point = new HashMap<>();
	private int classaverage;
	private int gradeaverage;
	private String subject;

//	ゲッターとセッター
	public int getEntYear(){
		return entYear;
	}
	public void setEntYear(int entYear){
		this.entYear = entYear;
	}
	public String getStudentNo(){
		return studentNo;
	}
	public void setStudentNo(String studentNo){
		this.studentNo = studentNo;
	}
	public String getStudentName(){
		return studentName;
	}
	public void setStudentName(String studentName){
		this.studentName = studentName;
	}
	public int getPoints(){
		return points;
	}
	public void setPoints(int points){
		this.points = points;
	}
	public String getSubject(){
		return subject;
	}
	public void setSubject(String subject){
		this.subject = subject;
	}
}
