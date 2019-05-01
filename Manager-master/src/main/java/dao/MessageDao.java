package dao;

import com.sun.org.apache.xpath.internal.operations.And;
import model.Message;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    public List<Message> getList(Connection con, String userName) throws SQLException {
        StringBuffer sql =new StringBuffer("select * from t_message") ;
        if (userName != null) {
            sql.append(" and userName='" + userName + "'");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        List<Message> messageList = new ArrayList<>();
        while (rs.next()) {
            Message message = new Message();
            message.setId(rs.getInt("messageId"));
            message.setTitle(rs.getString("title"));
            message.setContent(rs.getString("content"));
            message.setAnswer(rs.getString("answer"));
            message.setUserName(rs.getString("userName"));
            messageList.add(message);
        }
        return messageList;
    }

    public void sendMessage(Connection con, Message message,String userName) throws SQLException {
        String sql="insert into t_message values (null,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, message.getTitle());
        pstmt.setString(2, message.getContent());
        pstmt.setString(3, message.getAnswer());
        pstmt.setString(4, userName);
        pstmt.executeUpdate();
    }
    public void answer(Connection con, Message message) throws SQLException {
        String sql="update t_message set answer=? where messageId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, message.getAnswer());
        pstmt.setInt(2, message.getId());
        pstmt.executeUpdate();
    }
}
