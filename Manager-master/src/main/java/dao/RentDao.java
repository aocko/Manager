package dao;

import com.mysql.cj.protocol.Resultset;
import model.Class;
import model.Rent;
import model.Timetable;
import util.DateUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentDao {
    TimetableDao timetableDao = new TimetableDao();
    ClassDao classDao = new ClassDao();
    int[] time = {30000, 32700, 33000, 35700, 36900, 39600, 39900, 42600, 42900, 44700, 45000, 46800, 47400, 50100, 50400, 53100, 54300, 57000, 57300, 60000, 64800, 67500, 67800, 69500, 69800, 72500};

    public List<Rent> rentList(Connection con, Rent rent) throws SQLException, ParseException {
        StringBuffer sql = new StringBuffer("select * from t_rent");
        if (rent != null) {
            if (rent.getClassStatus() != null || rent.getRentTime() != null || rent.getEndTime() != null) {
                if (rent.getClassStatus().equals("空闲") || rent.getClassStatus().equals("使用中")) {
                    sql.append(" and status ='" + rent.getClassStatus() + "' ");
                }
                if (rent.getStartTime() != null && !rent.getStartTime().equals("")) {
                    sql.append(" and startTime >='" + rent.getStartTime() + "' ");
                }
                if (rent.getEndTime() != null && !rent.getEndTime().equals("")) {
                    sql.append(" and endTime <='" + rent.getEndTime() + "' ");
                }
            }
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        List<Rent> rentList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        while (rs.next()) {
            Rent rentPojo = new Rent();
            rentPojo.setRentId(rs.getInt("rentId"));
            rentPojo.setClassId(rs.getInt("classId"));
            rentPojo.setClassName(rs.getString("className"));
            rentPojo.setClassType(rs.getString("classType"));
            rentPojo.setRentTime(format.format(format.parse(String.valueOf(rs.getTimestamp("rentTime")))));
            rentPojo.setStartTime(format.format(format.parse(String.valueOf(rs.getTimestamp("startTime")))));
            rentPojo.setEndTime(format.format(format.parse(String.valueOf(rs.getTimestamp("endTime")))));
            rentPojo.setClassStatus(rs.getString("classStatus"));
            rentPojo.setStudentId(rs.getInt("studentId"));
            rentPojo.setStuName(rs.getString("stuName"));
            rentPojo.setStuNo(rs.getInt("stuNo"));
            rentPojo.setReason(rs.getString("reason"));
            rentPojo.setUserName(rs.getString("userName"));
            rentPojo.setRentStatus(rs.getString("rentStatus"));
            rentList.add(rentPojo);
        }
        return rentList;
    }


    public List<Rent> rentListByuser(Connection con, Rent rent) throws SQLException, ParseException {
        String sql = "select * from t_rent where userName='"+rent.getUserName()+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Rent> rentList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        while (rs.next()) {
            Rent rentPojo = new Rent();
            rentPojo.setRentId(rs.getInt("rentId"));
            rentPojo.setClassId(rs.getInt("classId"));
            rentPojo.setClassName(rs.getString("className"));
            rentPojo.setClassType(rs.getString("classType"));
            rentPojo.setRentTime(format.format(format.parse(String.valueOf(rs.getTimestamp("rentTime")))));
            rentPojo.setStartTime(format.format(format.parse(String.valueOf(rs.getTimestamp("startTime")))));
            rentPojo.setEndTime(format.format(format.parse(String.valueOf(rs.getTimestamp("endTime")))));
            rentPojo.setClassStatus(rs.getString("classStatus"));
            rentPojo.setStudentId(rs.getInt("studentId"));
            rentPojo.setStuName(rs.getString("stuName"));
            rentPojo.setStuNo(rs.getInt("stuNo"));
            rentPojo.setReason(rs.getString("reason"));
            rentPojo.setUserName(rs.getString("userName"));
            rentPojo.setRentStatus(rs.getString("rentStatus"));
            rentList.add(rentPojo);
        }
        return rentList;
    }

    public Rent getRentById(Connection con, Integer rentId) throws SQLException, ParseException {
        String sql = "select * from t_rent where rentId='" + rentId + "'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Rent rentPojo = new Rent();
        while (rs.next()) {
            rentPojo.setRentId(rs.getInt("rentId"));
            rentPojo.setClassId(rs.getInt("classId"));
            rentPojo.setClassName(rs.getString("className"));
            rentPojo.setClassType(rs.getString("classType"));
            rentPojo.setRentTime(format.format(format.parse(String.valueOf(rs.getTimestamp("rentTime")))));
            rentPojo.setStartTime(format.format(format.parse(String.valueOf(rs.getTimestamp("startTime")))));
            rentPojo.setEndTime(format.format(format.parse(String.valueOf(rs.getTimestamp("endTime")))));
            rentPojo.setClassStatus(rs.getString("classStatus"));
            rentPojo.setStudentId(rs.getInt("studentId"));
            rentPojo.setStuName(rs.getString("stuName"));
            rentPojo.setStuNo(rs.getInt("stuNo"));
            rentPojo.setReason(rs.getString("reason"));
            rentPojo.setUserName(rs.getString("userName"));
            rentPojo.setRentStatus(rs.getString("rentStatus"));
        }
        return rentPojo;

    }

    public void updateClassStatus(Connection con, Integer rentId) throws SQLException {
        String sql = "update t_rent set classStatus ='空闲',rentStatus='申请已过期' where rentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, rentId);
        pstmt.executeUpdate();
    }

    public void updateClassStatusForClass(Connection con, Integer rentId) throws SQLException {
        String sql = "update t_rent set classStatus ='使用中', where rentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, rentId);
        pstmt.executeUpdate();
    }

    public void updaterentStatus(Connection con, Integer rentId, String rentStatus, String rejectReason) throws SQLException {
        String sql = "update t_rent set rentStatus='" + rentStatus + "' where rentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, rentId);
        pstmt.executeUpdate();
        if (rejectReason != null) {
            String sql1 = "update t_rent set rejectReason='" + rejectReason + "' where rentId=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql1);
            preparedStatement.setInt(1, rentId);
            preparedStatement.executeUpdate();
        }
    }

    public void updateAgreeStatus(Connection con, Integer rentId, Integer classId) throws SQLException {
        String sql = "update t_rent r,t_class c set r.classStatus ='使用中',c.classStatus='使用中' where r.rentId=? and c.classId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, rentId);
        pstmt.setInt(2, classId);
        pstmt.executeUpdate();
    }

    public void Duplicatedetection(SimpleDateFormat format, Rent rent, Connection con) throws SQLException, ParseException {
        Date date = null;
        date = format.parse(rent.getEndTime());
        Date now = new Date();
        if (now.compareTo(date) == 1) {
            updateClassStatus(con, rent.getRentId());
            classDao.updateStatus(con, rent.getClassId());
            rent.setClassStatus("空闲");
            rent.setRentStatus("申请已过期");
        }
        timeTableDetection( rent,rent.getClassId(), con,now,null);
    }
    public void timeTableDetection(Rent rent, int classId, Connection con, Date now, Class aClass) throws SQLException {
        String weekday = DateUtil.getWeekDays(now);
        List<Timetable> timetableList = timetableDao.timetableList(con, String.valueOf(classId));
        timetableList.forEach(timetable -> {
            if (timetable.getWeek().equals(weekday)) {
                int timeOfnow = DateUtil.getSecond(now);
                java.lang.Class clazz = timetable.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 3; i < fields.length; i++) {
                    Field f = fields[i];
                    f.setAccessible(true);
                    try {
                        if (!f.get(timetable).equals("无课程")) {
                            if (timeOfnow < time[(i - 3) * 2 + 1] && timeOfnow > time[(i - 3) * 2]) {
                                if (rent != null) {
                                    updateClassStatus(con, rent.getRentId());
                                    rent.setClassStatus("使用中");
                                }
                                if (aClass != null) {
                                    aClass.setClassStatus("使用中");
                                }
                                classDao.updateStatus(con, classId);
                                break;
                            }
                        }
                    } catch (IllegalAccessException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean timetableDetectionForSubmit(int classId, Connection con, Date now) throws SQLException {
        String weekday = DateUtil.getWeekDays(now);
        List<Timetable> timetableList = timetableDao.timetableList(con, String.valueOf(classId));
        boolean flag=true;
        for (Timetable timetable : timetableList) {
            if (timetable.getWeek().equals(weekday)) {
                int timeOfnow = DateUtil.getSecond(now);
                java.lang.Class clazz = timetable.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 3; i < fields.length; i++) {
                    Field f = fields[i];
                    f.setAccessible(true);
                    try {
                        if (!f.get(timetable).equals("无课程")) {
                            if (timeOfnow < time[(i - 3) * 2 + 1] && timeOfnow > time[(i - 3) * 2]) {
                                flag = false;
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }
    public void addRent(Connection con, Rent rent) throws SQLException, ParseException {
        String sql = "insert into t_rent values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, rent.getClassId());
        preparedStatement.setString(2, rent.getClassName());
        preparedStatement.setString(3, rent.getClassType());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(rent.getRentTime()));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(rent.getStartTime()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(rent.getEndTime()));
        preparedStatement.setString(7, rent.getClassStatus());
        preparedStatement.setInt(8, rent.getStudentId());
        preparedStatement.setString(9, rent.getStuName());
        preparedStatement.setInt(10, rent.getStuNo());
        preparedStatement.setString(11, rent.getReason());
        preparedStatement.setString(12, rent.getUserName());
        preparedStatement.setString(13, rent.getRentStatus());
        preparedStatement.setString(14, null);
        preparedStatement.executeUpdate();
    }

    public boolean deleteRent(Connection connection, Rent rent,SimpleDateFormat format)  {

        Date date = null;
        try {
            date = format.parse(rent.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        if (now.getTime() - date.getTime() > 432000000) {
            String sql = "delete from t_rent where rentId="+rent.getRentId()+"";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
