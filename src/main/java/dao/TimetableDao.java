package dao;

import model.Timetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimetableDao {
    public List<Timetable> timetableList(Connection con, String classId) throws SQLException {
        String sql = "select * from t_timetable where classId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, classId);
        ResultSet rs = pstmt.executeQuery();
        List<Timetable> timetableList = new ArrayList<>();
        while (rs.next()) {
            Timetable timetable = new Timetable();
            timetable.setWeek(rs.getString("week"));
            timetable.setFirst(rs.getString("first"));
            timetable.setSecond(rs.getString("second"));
            timetable.setThird(rs.getString("third"));
            timetable.setFourth(rs.getString("fourth"));
            timetable.setFifth(rs.getString("fifth"));
            timetable.setSixth(rs.getString("sixth"));
            timetable.setSeventh(rs.getString("seventh"));
            timetable.setEighth(rs.getString("eighth"));
            timetable.setNinth(rs.getString("ninth"));
            timetable.setTenth(rs.getString("tenth"));
            timetable.setEleventh(rs.getString("eleventh"));
            timetable.setTwelfth(rs.getString("twelfth"));
            timetable.setThirteenth(rs.getString("thirteenth"));
            timetableList.add(timetable);
        }
        return timetableList;
    }

    public int timetableListUpdate(Connection con,String classId, List<Timetable> timetableList) {
       timetableList.forEach(timetable -> {
           try {
               timetbaleUpdate(con, classId, timetable);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       });
       return 1;
    }

    public int timetbaleUpdate(Connection con, String classId, Timetable timetable) throws SQLException {
        String sql = "update t_timetable set first=?,second=?,third=?,fourth=?,fifth=?,sixth=?,seventh=?,eighth=?,ninth=?,tenth=?,eleventh=?,twelfth=?,thirteenth=? where classId=? and week=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, timetable.getFirst());
        pstmt.setString(2, timetable.getSecond());
        pstmt.setString(3, timetable.getThird());
        pstmt.setString(4, timetable.getFourth());
        pstmt.setString(5, timetable.getFifth());
        pstmt.setString(6, timetable.getSixth());
        pstmt.setString(7, timetable.getSeventh());
        pstmt.setString(8, timetable.getEighth());
        pstmt.setString(9, timetable.getNinth());
        pstmt.setString(10, timetable.getTenth());
        pstmt.setString(11, timetable.getEleventh());
        pstmt.setString(12, timetable.getTwelfth());
        pstmt.setString(13, timetable.getThirteenth());
        pstmt.setString(14, classId);
        pstmt.setString(15, timetable.getWeek());
      return   pstmt.executeUpdate();
    }
    public int saveTimetableList(Connection con,String classId, List<Timetable> timetableList) {
        timetableList.forEach(timetable -> {
            try {
                saveTimetable(con, classId, timetable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return 1;
    }
  public int saveTimetable(Connection con,String classId,Timetable timetable) throws SQLException {
      String sql = "insert into t_timetable values (null ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, classId);
      pstmt.setString(2, timetable.getWeek());
      pstmt.setString(3, timetable.getFirst());
      pstmt.setString(4, timetable.getSecond());
      pstmt.setString(5, timetable.getThird());
      pstmt.setString(6, timetable.getFourth());
      pstmt.setString(7, timetable.getFifth());
      pstmt.setString(8, timetable.getSixth());
      pstmt.setString(9, timetable.getSeventh());
      pstmt.setString(10, timetable.getEighth());
      pstmt.setString(11, timetable.getNinth());
      pstmt.setString(12, timetable.getTenth());
      pstmt.setString(13, timetable.getEleventh());
      pstmt.setString(14, timetable.getTwelfth());
      pstmt.setString(15, timetable.getThirteenth());

      return   pstmt.executeUpdate();
  }
}
