package dao;

import model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDao {
    public List<Grade> gradeList(Connection con) throws Exception {
        List<Grade> gradeList = new ArrayList<>();
        String sql = "select * from t_grade";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Grade grade = new Grade();
            grade.setGradeId(rs.getInt("gradeId"));
            grade.setGradeName(rs.getString("gradeName"));
            grade.setGradeDesc(rs.getString("gradeDesc"));
            grade.setMajorName(rs.getString("majorName"));
            gradeList.add(grade);
        }
        return gradeList;

    }
    public List<Grade> gradeList(Connection con,int majorId) throws Exception {
        List<Grade> gradeList = new ArrayList<>();
        String sql = "select * from t_grade where majorId='"+majorId+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Grade grade = new Grade();
            grade.setGradeId(rs.getInt("gradeId"));
            grade.setGradeName(rs.getString("gradeName"));
            grade.setGradeDesc(rs.getString("gradeDesc"));
            grade.setMajorName(rs.getString("majorName"));
            gradeList.add(grade);
        }
        return gradeList;

    }



    public Grade getGradeById(Connection con, String gradeId) throws Exception {
        String sql = "select * from t_grade where gradeId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, gradeId);
        ResultSet rs = pstmt.executeQuery();
        Grade grade = new Grade();
        if (rs.next()) {
            grade.setGradeId(rs.getInt("gradeId"));
            grade.setGradeName(rs.getString("gradeName"));
            grade.setGradeDesc(rs.getString("gradeDesc"));
            grade.setMajorName(rs.getString("majorName"));

        }
        return grade;
    }

    public int gradeAdd(Connection con, Grade grade) throws SQLException {
        String sql = "insert into t_grade values(null,?,?,?,?) ";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, grade.getGradeName());
        preparedStatement.setString(2, grade.getGradeDesc());
        preparedStatement.setString(3, grade.getMajorName());
        preparedStatement.setInt(4, grade.getMajorId());
        int result = preparedStatement.executeUpdate();
        return result;
    }

    public int gradeUpdate(Connection con, Grade grade) throws Exception {
        String sql = "update t_grade set gradeName=?,gradeDesc=?,majorName=?,majorId=? where gradeId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, grade.getGradeName());
        pstmt.setString(2, grade.getGradeDesc());
        pstmt.setString(3, grade.getMajorName());
        pstmt.setInt(4, grade.getMajorId());
        pstmt.setInt(5, grade.getGradeId());
        return pstmt.executeUpdate();
    }

    public int gradeDelete(Connection con, int gradeId) throws Exception {
        String sql = "delete from t_grade where gradeId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, gradeId);
        return pstmt.executeUpdate();

    }
    public  List<Grade> gradeListBymajorId(Connection con,int majorId) throws Exception {
        List<Grade> gradeList = new ArrayList<>();
        String sql = "select * from t_grade where majorId='"+majorId+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Grade grade = new Grade();
            grade.setGradeId(rs.getInt("gradeId"));
            grade.setGradeName(rs.getString("gradeName"));
            grade.setGradeDesc(rs.getString("gradeDesc"));
            grade.setMajorName(rs.getString("majorName"));
            gradeList.add(grade);
        }
        return gradeList;

    }
}
