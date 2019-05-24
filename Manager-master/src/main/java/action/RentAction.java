package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.*;
import model.Class;
import model.Rent;
import model.Student;
import model.Timetable;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import util.DateUtil;
import util.DbUtil;
import util.ResponseUtil;
import util.StatusUtil;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentAction extends ActionSupport {
    private List<Rent> rentList;
    private String mainPage;
    private String error;
    private Rent rent;
    private String rentTime;
    private String endTime;
    private String status;
    private int rentId;
    private int classId;
    private List<Class> classList;
    private String startTime;
    private String userName;
    private String reason;
    private String rejectReason;
    private String className;
    private String classStatus;
    private List<Integer> pageNoList = new ArrayList<>();
    private String pageNo;
    private int week;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    private List<String> statusList = new ArrayList<>();

    public List getPageNoList() {
        return pageNoList;
    }

    public void setPageNoList(List pageNoList) {
        this.pageNoList = pageNoList;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }


    private ClassDao classDao = new ClassDao();
    private Timetable2Dao timetable2Dao = new Timetable2Dao();

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Rent> getRentList() {
        return rentList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    DbUtil dbUtil = new DbUtil();
    RentDao rentDao = new RentDao();
    TimetableDao timetableDao = new TimetableDao();

    public String list() throws Exception, SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Rent rent = new Rent();
        Connection con = dbUtil.getCon();
        rent.setStartTime(startTime);
        rent.setEndTime(endTime);
        rent.setClassStatus(status);
        startTime = startTime;
        endTime = endTime;
        rentList = rentDao.rentList(con, rent);
        rentList.removeIf(rent1 -> rentDao.deleteRent(con, rent1, format));
        try {
            rentList.forEach(r -> {
                try {
                    rentDao.Duplicatedetection(format, r, con);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (userName != null) {
            rent.setUserName(userName);
            rentList = rentDao.rentListByuser(con, rent);
            mainPage = "user/rentList.jsp";
            dbUtil.closeCon(con);
            return SUCCESS;
        }
        mainPage = "class/classRent.jsp";

        dbUtil.closeCon(con);
        return SUCCESS;
    }

    public String updateStatus() throws Exception {
        Connection con = dbUtil.getCon();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Rent r = rentDao.getRentById(con, rentId);
        mainPage = "class/classRent.jsp";
        Date startTime = format.parse(r.getStartTime());
        Date endTime = format.parse(r.getEndTime());
        Date now = new Date();
        rentList = rentDao.rentList(con, null);
        JSONObject result = new JSONObject();
        if (now.compareTo(endTime) > 0) {
            result.put("error", "申请已超时");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return ERROR;
        }
        String rentStatus;
        if (rejectReason == null) {
            rentStatus = "同意";
        } else {
            rentStatus = "拒绝";
        }
        rentDao.updaterentStatus(con, rentId, rentStatus, rejectReason);
        if (rentStatus.equals("同意")) {
            List<Timetable> timetableList = timetable2Dao.timetable2List(con, String.valueOf(r.getClassId()));
            if (timetableList.size() == 0) {
                List<Timetable> timetableList2 = new ArrayList<>();
                String[] weekdays = {"星期一", "星期二", "星期三", "星期四", "星期五"};
                for (int i=0;i<5;i++){
                    Timetable timetable = new Timetable();
                    timetable.setWeek(weekdays[i]);
                    timetableList2.add(timetable);
                }
                timetable2Dao.saveTimetable2List(con, String.valueOf(r.getClassId()), timetableList2);
                timetableDao.saveTimetableList(con, String.valueOf(r.getClassId()), timetableList2);
            }
            List<Timetable> timetableList2 = timetable2Dao.timetable2List(con, String.valueOf(r.getClassId()));
            List<Timetable>  timetableList1=   rentDao.timetableDetectionForAccept(rentDao.timetableDetectionForAccept(timetableList2, con, startTime), con, endTime);
            try {
                timetable2Dao.timetable2ListUpdate(con, String.valueOf(r.getClassId()), timetableList1);
            } catch (Exception e) {
            }
        }
        rentList = rentDao.rentList(con, null);
        ResponseUtil.write(result, ServletActionContext.getResponse());
        dbUtil.closeCon(con);

        return SUCCESS;
    }

    public String rentList() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Rent rent = new Rent();
        Connection con = dbUtil.getCon();
        rentList = rentDao.rentList(con, rent);
        rentList.removeIf(rent1 -> rentDao.deleteRent(con, rent1, format));
        Timer timer = new Timer();
        classList = classDao.getclassList(con, null);
        if (classList != null) {
            for (int i = 1; i <= ((classList.size()) / 15 + 1); i++) {
                pageNoList.add(i);
            }
            if (classList.size() == 15) {
                pageNoList = new ArrayList<>();
                pageNoList.add(1);
            }
            if (classList.size() >= 15) {
                classList = classList.subList(0, 15);
            }
        }
        statusList.add("所有");
        statusList.add("空闲");
        statusList.add("使用中");
        if (pageNo == null) {
            pageNo = "1";
        }
        try {
            classList.forEach(c -> {
                try {
                    rentDao.timeTableDetection(null, c.getClassId(), con, new Date(), c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPage = "user/rentClass.jsp";
        dbUtil.closeCon(con);
        return SUCCESS;
    }

    public String QueryClass() throws Exception {
        Connection connection = dbUtil.getCon();
        statusList.add("所有");
        statusList.add("使用中");
        statusList.add("空闲");
        if (classStatus != null) {
            StatusUtil.status(statusList, classStatus);
        }
        int classListsize = classDao.countclassList(connection, className, classStatus, null);
        for (int i = 1; i <= ((classListsize) / 15 + 1); i++) {
            pageNoList.add(i);
        }
        if (classListsize == 15) {
            pageNoList = new ArrayList<>();
            pageNoList.add(1);
        }

        if (pageNo == null) {
            pageNo = "1";
        }
        classList = classDao.getclassList(connection, className, classStatus, pageNo);

        mainPage = "user/rentClass.jsp";
        dbUtil.closeCon(connection);
        return SUCCESS;
    }

    public String rentSubmit() throws Exception {
        Connection con = dbUtil.getCon();
        StudentDao studentDao = new StudentDao();
        UserDao userDao = new UserDao();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject result = new JSONObject();
        if (week != 0) {
            Date now = new Date();
            int weekday = DateUtil.getweek(now);
            if (weekday > week) {
                result.put("error", "时间错误！只允许预约本星期！");
                ResponseUtil.write(result, ServletActionContext.getResponse());
                return null;
            }
            now= DateUtil.getNow();
            int[] time = rentDao.time;
            Date date =new Date(now.getTime()+time[2*(Integer.parseInt(startTime)-1)]*1000+(week-weekday)*24*60*60*1000);
            Date date2 =new Date(now.getTime()+time[2*(Integer.parseInt(endTime)-1)+1]*1000+(week-weekday)*24*60*60*1000);
            startTime = sdf.format(date);
            endTime = sdf.format(date2);
        }

        Date s_Time = new Date();
        Date e_Time = new Date();
        try {
            s_Time = sdf.parse(startTime);
            e_Time = sdf.parse(endTime);
        } catch (Exception e) {
            result.put("error", "时间格式错误！");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        Class aClass = classDao.getclassByid(con, String.valueOf(classId));
        int studentId = userDao.getStudentIdByuserName(userName, con);
        Student student = studentDao.getStudentById(con, String.valueOf(studentId));
        Rent rent = new Rent();
        rent.setClassStatus(aClass.getClassStatus());
        rent.setRentStatus("未处理");
        rent.setClassId(classId);
        if (s_Time.getTime() < ((new Date()).getTime() + 21600000)) {

            result.put("error", "为保证教室使用不冲突,请开始时间设置为6小时之后！");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if (s_Time.getTime() > e_Time.getTime()) {
            result.put("error", "错误！开始时间必须小于结束时间！");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if ((e_Time.getTime() - s_Time.getTime()) > 21600000) {
            result.put("error", "最长租用时间为六个小时！");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if (!rentDao.timetableDetectionForSubmit(classId, con, s_Time) || !rentDao.timetableDetectionForSubmit(classId, con, e_Time)) {
            result.put("error", "申请时间有课程，请勿与课程冲突！");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        rent.setClassName(aClass.getClassName());
        rent.setClassType(aClass.getClassType());
        rent.setRentTime(sdf2.format(new Date()));
        rent.setStartTime(sdf2.format(s_Time));
        rent.setEndTime(sdf2.format(e_Time));
        rent.setStudentId(studentId);
        rent.setStuName(student.getStuName());
        rent.setStuNo(student.getStuNo());
        rent.setReason(reason);
        rent.setUserName(userName);
        rentDao.addRent(con, rent);
        ResponseUtil.write(result, ServletActionContext.getResponse());

        return null;

    }
    public String queryBadge() throws Exception {
        Connection connection = dbUtil.getCon();
        int badge = 0;
        for (Rent rent1 : rentDao.rentList(connection, rent)) {
            if (rent1.getRentStatus().equals("未处理")) {
                badge += 1;
            }
        }
        JSONObject result = new JSONObject();
        result.put("badge", badge);
        ResponseUtil.write(result, ServletActionContext.getResponse());
        dbUtil.closeCon(connection);
        return null;
    }

}
