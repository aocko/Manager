package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorDaoUtil {

    public int selectMajorIdByName(Connection con,String majorName) throws SQLException {
        String sql = "select majorId from t_major where majorName=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, majorName);
        ResultSet rs=pstmt.executeQuery();
        int majorId = 0;
        if (rs.next()) {
            majorId=  rs.getInt("majorId");
        }
        return majorId;
    }
    public int selectGradeIdByName(Connection con,String gradeName) throws SQLException {
        String sql = "select gradeId from t_grade where gradeName=? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, gradeName);
        ResultSet rs=pstmt.executeQuery();
        int gradeId = 0;
        if (rs.next()) {
            gradeId=  rs.getInt("gradeId");
        }
        return gradeId;
    }
    public int selectGradeIdByNameandId(Connection con,String gradeName,int majorId) throws SQLException {
        String sql = "select gradeId from t_grade where gradeName=? and majorId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, gradeName);
        pstmt.setInt(2, majorId);
        ResultSet rs=pstmt.executeQuery();
        int gradeId = 0;
        if (rs.next()) {
            gradeId=  rs.getInt("gradeId");
        }
        return gradeId;
    }
}
