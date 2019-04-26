package dao;

import model.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDao {

	public boolean existStudent(Connection con,String gradeId)throws Exception {
		String sql = "select * from t_student where gradeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public List<Major> majorList(Connection con)throws Exception {
		List<Major> majorList = new ArrayList<>();
		String sql = "select * from t_major";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Major major = new Major();
			major.setMajorId(rs.getInt("majorId"));
			major.setMajorName(rs.getString("majorName"));
			major.setMajorDesc(rs.getString("majorDesc"));
			majorList.add(major);
		}

		return majorList;
	}
	public int majorAdd(Connection con, Major major) throws SQLException {
		String sql = "insert into t_major values(null,?,?) ";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, major.getMajorName());
		preparedStatement.setString(2, major.getMajorDesc());
		int result = preparedStatement.executeUpdate();
		return result;
	}

	public Major getMajorById(Connection con, String majorId) throws Exception {
		String sql = "select * from t_major where majorId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, majorId);
		ResultSet rs = pstmt.executeQuery();
		Major major = new Major();
		if (rs.next()) {
			major.setMajorId(rs.getInt("majorId"));
			major.setMajorName(rs.getString("majorName"));
			major.setMajorDesc(rs.getString("majorDesc"));

		}
		return major;
	}

	public int majorUpdate(Connection con, Major major) throws Exception {
		String sql = "update t_major set majorName=?,majorDesc=? where majorId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, major.getMajorName());
		pstmt.setString(2, major.getMajorDesc());
		pstmt.setInt(3, major.getMajorId());
		return pstmt.executeUpdate();
	}

	public int majorDelete(Connection con, int majorId) throws Exception {
		String sql = "delete from t_major where majorId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, majorId);
		return pstmt.executeUpdate();

	}
}
