package dao;

import model.Student;
import model.User;
import util.StringUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    public List<Student> studentList(Connection con, Student student,String pageNo) throws Exception {
        List<Student> studentList = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from t_student s,t_grade g where s.gradeId=g.gradeId");
        if (student.getStuNo() != 0) {
            sql.append(" and s.stuNo like '%" + student.getStuNo() + "%'");
        }
        if (StringUtil.isNotEmpty(student.getStuSex()) && !(student.getStuSex()).equals("未选择")) {
            sql.append(" and s.stuSex='" + student.getStuSex() + "'");
        }
        if (StringUtil.isNotEmpty(student.getStuName())) {
            sql.append(" and s.stuName like '%" + student.getStuName() + "%'");
        }
        if (StringUtil.isNotEmpty(student.getMajorName()) && !(student.getMajorName()).equals("未选择")) {
            sql.append(" and s.majorName='" + student.getMajorName() + "'");
        }
        if (StringUtil.isNotEmpty(pageNo) ) {
            sql.append(" limit "+(Integer.valueOf(pageNo)-1)*15+",15");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Student student1 = new Student();
            student1.setStudentId(rs.getInt("studentId"));
            student1.setStuNo(rs.getInt("stuNo"));
            student1.setStuName(rs.getString("stuName"));
            student1.setStuSex(rs.getString("stuSex"));
            student1.setStuNation(rs.getString("stuNation"));
            student1.setStuBirthday(rs.getDate("stuBirthday"));
            student1.setStuZzmm(rs.getString("stuZzmm"));
            student1.setStuDesc(rs.getString("stuDesc"));
            student1.setMajorId(rs.getInt("majorId"));
            student1.setMajorName(rs.getString("majorName"));
            student1.setGradeId(rs.getInt("gradeId"));
            student1.setGradeName(rs.getString("gradeName"));
            student1.setType(rs.getString("Type"));
            studentList.add(student1);
        }
        studentList.removeIf(student1 -> "老师".equals(student1.getType()));
        return studentList;
    }

    public List<Student> getstudentListBymajorIdorGradeId(Connection con, Integer majorId, Integer gradeId) throws Exception {
        List<Student> studentList = new ArrayList<>();
        StringBuffer sql =new StringBuffer("select * from t_student ");
        if (majorId != null&&gradeId != null) {
            sql.append(" and majorId='" + majorId + "'");
            sql.append(" or gradeId='" + gradeId + "'");
        }
        if (gradeId != null) {
            sql.append(" and gradeId='" + gradeId + "'");
        }
        if (majorId != null) {
            sql.append(" and majorId='" + majorId + "'");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Student student1 = new Student();
            studentList.add(student1);
        }
        return studentList;
    }

    public Student getStudentById(Connection con, String studentId) throws Exception {
        String sql = "select * from t_student where studentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, studentId);
        ResultSet rs = pstmt.executeQuery();
        Student student = new Student();
        if (rs.next()) {
            student.setStudentId(rs.getInt("studentId"));
            student.setStuNo(rs.getInt("stuNo"));
            student.setStuName(rs.getString("stuName"));
            student.setStuSex(rs.getString("stuSex"));
            student.setStuNation(rs.getString("stuNation"));
            student.setStuBirthday(rs.getDate("stuBirthday"));
            student.setStuZzmm(rs.getString("stuZzmm"));
            student.setStuDesc(rs.getString("stuDesc"));
            student.setMajorId(rs.getInt("majorId"));
            student.setMajorName(rs.getString("majorName"));
            student.setGradeId(rs.getInt("gradeId"));
            student.setGradeName(rs.getString("gradeName"));

        }
        return student;
    }
    public Student getStudentBystuNo(Connection con, Integer stuNo,String stuName) throws Exception {
        String sql = "select * from t_student where stuNo=? and stuName=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, stuNo);
        pstmt.setString(2, stuName);
        ResultSet rs = pstmt.executeQuery();
        Student student = new Student();
        if (rs.next()) {
            student.setStudentId(rs.getInt("studentId"));

        }
        return student;
    }



    public int studentAdd(Connection con, Student student) throws SQLException {
        String sql = "insert into t_student values(null,?,?,?,?,?,?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, student.getStuNo());
        preparedStatement.setString(2, student.getStuName());
        preparedStatement.setString(3, student.getStuSex());
        preparedStatement.setString(4, student.getStuNation());
        preparedStatement.setDate(5, student.getStuBirthday());
        preparedStatement.setString(6, student.getStuZzmm());
        preparedStatement.setString(7, student.getStuDesc());
        preparedStatement.setInt(8, student.getMajorId());
        preparedStatement.setString(9, student.getMajorName());
        preparedStatement.setInt(10, student.getGradeId());
        preparedStatement.setString(11, student.getGradeName());
        preparedStatement.setString(12, "学生");
       preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int result=0;
        if (rs.next()) {
       result = rs.getInt(1);
        }
        return result;
    }

    public int studentAdd(Connection con, Student student, String userType) throws SQLException {
        String sql = "insert into t_student values(null,?,?,?,?,?,?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, student.getStuNo());
        preparedStatement.setString(2, student.getStuName());
        preparedStatement.setString(3, student.getStuSex());
        preparedStatement.setString(4, student.getStuNation());
        preparedStatement.setDate(5, student.getStuBirthday());
        preparedStatement.setString(6, student.getStuZzmm());
        preparedStatement.setString(7, student.getStuDesc());
        preparedStatement.setInt(8, student.getMajorId());
        preparedStatement.setString(9, student.getMajorName());
        preparedStatement.setInt(10, student.getGradeId());
        preparedStatement.setString(11, student.getGradeName());
        preparedStatement.setString(12, userType);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int result=0;
        if (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public int studentUpdate(Connection con, Student student) throws Exception {
        String sql = "update t_student set stuNo=?,stuName=?,stuSex=?,stuNation=? ,stuBirthday=?,stuZzmm=?,stuDesc=?,majorId=?,majorName=?,gradeId=?,gradeName=? where studentId=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, student.getStuNo());
        preparedStatement.setString(2, student.getStuName());
        preparedStatement.setString(3, student.getStuSex());
        preparedStatement.setString(4, student.getStuNation());
        preparedStatement.setDate(5, student.getStuBirthday());
        preparedStatement.setString(6, student.getStuZzmm());
        preparedStatement.setString(7, student.getStuDesc());
        preparedStatement.setInt(8, student.getMajorId());
        preparedStatement.setString(9, student.getMajorName());
        preparedStatement.setInt(10, student.getGradeId());
        preparedStatement.setString(11, student.getGradeName());
        preparedStatement.setInt(12, student.getStudentId());

        return preparedStatement.executeUpdate();
    }
    public int teacherUpdate(Connection con, Student student) throws Exception {
        String sql = "update t_student set stuNo=?,stuName=?,stuSex=?,stuNation=? ,stuBirthday=? where studentId=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, student.getStuNo());
        preparedStatement.setString(2, student.getStuName());
        preparedStatement.setString(3, student.getStuSex());
        preparedStatement.setString(4, student.getStuNation());
        preparedStatement.setDate(5, student.getStuBirthday());
        preparedStatement.setInt(6, student.getStudentId());

        return preparedStatement.executeUpdate();
    }

    public int studentDelete(Connection con, int studentId) throws Exception {
        String sql = "delete from t_student where studentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, studentId);
        return pstmt.executeUpdate();
    }
}
