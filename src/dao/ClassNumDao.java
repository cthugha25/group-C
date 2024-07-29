package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Class_num;

public class ClassNumDao extends DAO {

    // 全てのクラス番号を取得するSQL
    private String selectAllSql = "SELECT class_num FROM stdent";

    // 全てのクラス番号を取得するメソッド
    public List<Class_num> getAllClassNums() throws Exception {
        List<Class_num> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(selectAllSql);
             ResultSet rs = ps.executeQuery()) {

            // 結果セットからリストに変換
            list = postFilter(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error accessing the database", e);
        }

        return list;
    }

    // 結果セットからクラス番号リストを作成するメソッド
    private List<Class_num> postFilter(ResultSet rSet) throws SQLException {
        List<Class_num> list = new ArrayList<>();

        try {
            while (rSet.next()) {
                Class_num classnum = new Class_num();
//                classnum.setSchool_cd(rSet.getString("school_cd"));
                classnum.setClass_num(rSet.getString("class_num"));
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
