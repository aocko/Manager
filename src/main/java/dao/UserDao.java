package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public User login(Connection con, User user) throws Exception{
        User resultUser=null;
        String sql="select * from t_user where userName=? and password=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            resultUser=new User();
            resultUser.setUserName(rs.getString("userName"));
            resultUser.setPassword(rs.getString("password"));
            resultUser.setUserType(rs.getString("userType"));
            resultUser.setStudentId(rs.getInt("studentId"));
            resultUser.setUserId(rs.getInt("userId"));

        }
        return resultUser;
    }

    public boolean checkName(String userName,Connection con) throws SQLException {
        String sql = "select * from t_user where userName=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,userName);
        ResultSet rs=pstmt.executeQuery();
        if (rs.next()) {
            return false;
        }
        return true;
    }
    public int registUser(User user,Connection con) throws SQLException {
        String sql = "insert into t_user values(null,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getUserType());
        pstmt.setInt(4, user.getStudentId());
        return pstmt.executeUpdate();
    }

    public int changePassword(Integer userId,String newPassword, Connection con) throws SQLException {
        String sql = "update t_user set password='" + newPassword + "' where userId='" + userId + "'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();

    }

    public int getStudentIdByuserName(String userName, Connection connection) throws SQLException {
        String sql = "select studentId from t_user where userName='" + userName + "'";
        ResultSet rs = connection.prepareStatement(sql).executeQuery();
        if (rs.next()) {
        return     rs.getInt("studentId");
        }
        return 0;
    }
}
