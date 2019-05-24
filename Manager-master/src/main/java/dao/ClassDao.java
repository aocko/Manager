package dao;

import model.Class;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDao {
    public List<Class> getclassList(Connection connection,String pageNo) throws SQLException {
        List<Class> classList = new ArrayList<>();
        String sql = "select * from t_class";
        if (StringUtil.isNotEmpty(pageNo)) {
          sql = "select * from t_class limit "+(Integer.valueOf(pageNo)-1)*15+",15";
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Class aClass=new Class();
            aClass.setClassId(rs.getInt("classId"));
            aClass.setClassName(rs.getString("className"));
            aClass.setClassDesc(rs.getString("classDesc"));
            aClass.setClassSize(rs.getInt("classsize"));
            aClass.setClassType(rs.getString("classType"));
            aClass.setClassStatus(rs.getString("classStatus"));
            aClass.setEqCondition(rs.getString("eqCondition"));
            classList.add(aClass);
        }
        return classList;
    }



    public List<Class> getclassList(Connection connection, String className, String classStatus,String pageNo) throws SQLException {
        List<Class> classList = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from t_class");
        if (StringUtil.isNotEmpty(className)) {
            sql.append(" and className like '%"+className+"%'");
        }
        if (StringUtil.isNotEmpty(classStatus)&&!classStatus.equals("所有")) {
            sql.append(" and classStatus ='"+classStatus+"'");
        }
        if (StringUtil.isNotEmpty(pageNo)) {
            sql.append(" limit "+(Integer.valueOf(pageNo)-1)*15+" , 15");
        }
        PreparedStatement pstmt = connection.prepareStatement(sql.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Class aClass = new Class();
            aClass.setClassId(rs.getInt("classId"));
            aClass.setClassName(rs.getString("className"));
            aClass.setClassDesc(rs.getString("classDesc"));
            aClass.setClassSize(rs.getInt("classsize"));
            aClass.setClassType(rs.getString("classType"));
            aClass.setClassStatus(rs.getString("classStatus"));
            aClass.setEqCondition(rs.getString("eqCondition"));
            classList.add(aClass);
        }
        return classList;
    }
    public int countclassList(Connection connection, String className, String classStatus,String pageNo) throws SQLException {


        StringBuffer sql = new StringBuffer("select count(0) from t_class");
        if (StringUtil.isNotEmpty(className)) {
            sql.append(" and className like '%"+className+"%'");
        }
        if (StringUtil.isNotEmpty(classStatus)&&!classStatus.equals("所有")) {
            sql.append(" and classStatus ='"+classStatus+"'");
        }
        if (StringUtil.isNotEmpty(pageNo)) {
            sql.append(" limit "+(Integer.valueOf(pageNo)-1)*15+" , 15");
        }
        PreparedStatement pstmt = connection.prepareStatement(sql.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);

        }
        return count;
    }
    public Class getclassByid(Connection connection,String classId) throws SQLException {
        String sql = "select * from t_class where classId=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, classId);
        ResultSet rs = pstmt.executeQuery();
        Class aClass = new Class();
        if (rs.next()) {

            aClass.setClassId(rs.getInt("classId"));
            aClass.setClassName(rs.getString("className"));
            aClass.setClassDesc(rs.getString("classDesc"));
            aClass.setClassSize(rs.getInt("classsize"));
            aClass.setClassType(rs.getString("classType"));
            aClass.setClassStatus(rs.getString("classStatus"));
            aClass.setEqCondition(rs.getString("eqCondition"));
        }
        return aClass;
    }
    public int classAdd(Connection con, Class aclass) throws SQLException {
        String sql = "insert into t_class values(null,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, aclass.getClassName());
        preparedStatement.setString(2, aclass.getClassDesc());
        preparedStatement.setInt(3, aclass.getClassSize());
        preparedStatement.setString(4, aclass.getClassType());
        preparedStatement.setString(5, aclass.getClassStatus());
        preparedStatement.setString(6, aclass.getEqCondition());
        int result = preparedStatement.executeUpdate();
        return result;
    }
    public int classUpdate(Connection con, Class aclass) throws SQLException {
        String sql = "update t_class set className=?,classDesc=?,classType=? ,classSize=?,classStatus=?,eqCondition=? where classId=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, aclass.getClassName());
        preparedStatement.setString(2, aclass.getClassDesc());
        preparedStatement.setString(3, aclass.getClassType());
        preparedStatement.setString(5, aclass.getClassStatus());
        preparedStatement.setString(6, aclass.getEqCondition());
        preparedStatement.setInt(7, aclass.getClassId());
        preparedStatement.setInt(4, aclass.getClassSize());
        int result = preparedStatement.executeUpdate();
        return result;
    }

    public int classDelete(Connection con, String classId) throws Exception {
        String sql = "delete from t_class where classId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, classId);
        return pstmt.executeUpdate();

    }
    public void updateStatus(Connection con, Integer classId) throws SQLException {
        String sql = "update t_rent set classStatus ='空闲' where classId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, classId);
        pstmt.executeUpdate();
    }
    public void updateStatusForClass(Connection con, Integer classId) throws SQLException {
        String sql = "update t_rent set classStatus ='使用中' where classId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, classId);
        pstmt.executeUpdate();
    }
}
