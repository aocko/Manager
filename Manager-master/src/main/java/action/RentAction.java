package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.*;
import model.Class;
import model.Rent;
import model.Student;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import util.DbUtil;
import util.ResponseUtil;
import util.StatusUtil;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Timestamp;
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

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    private List<String> statusList=new ArrayList<>();

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

    public String list() throws Exception {
        Timer timer = new Timer();
        Connection con = dbUtil.getCon();
        Rent rent = new Rent();
        rent.setStartTime(startTime);
        rent.setEndTime(endTime);
        rent.setClassStatus(status);
        startTime = startTime;
        endTime = endTime;

        if (userName != null) {
            rent.setUserName(userName);
            rentList = rentDao.rentListByuser(con, rent);
            mainPage = "user/rentList.jsp";
            dbUtil.closeCon(con);
            return SUCCESS;
        } else {
            rentList = rentDao.rentList(con, rent);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Connection con2 = null;
                    try {
                        con2 = dbUtil.getCon();
                        List<Rent> list = rentDao.rentList(con2, rent);
                        for (Rent rent1 : list) {
                            try {
                                rentDao.Duplicatedetection(format, rent1, con2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            dbUtil.closeCon(con2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 0, 5000);
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
        Date date = format.parse(r.getEndTime());
        Date now = new Date();
        rentList = rentDao.rentList(con, null);
        JSONObject result = new JSONObject();
        if (now.compareTo(date) > 0) {
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
            rentDao.updateAgreeStatus(con, rentId, classId);
        }
        rentList = rentDao.rentList(con, null);
        ResponseUtil.write(result, ServletActionContext.getResponse());
        dbUtil.closeCon(con);

        return SUCCESS;
    }

    public String rentList() throws Exception {
        Connection con = dbUtil.getCon();
        Timer timer = new Timer();
        classList = classDao.getclassList(con,null);
        if (classList.size() >= 15) {
            classList.subList(0, 15);
        }
        statusList.add("所有");
        statusList.add("空闲");
        statusList.add("使用中");
        if (pageNo == null) {
            pageNo = "1";
        }
        for (int i = 1; i <= ((classList.size())/15+1); i++) {
            pageNoList.add(i);
        }
        try {
            classList.forEach(c -> {
                try {
                  rentDao.timeTableDetection(null, c.getClassId(), con, new Date(),c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Connection con2 = null;
                try {
                    con2 = dbUtil.getCon();
                   List<Class> classList2 = classDao.getclassList(con2,null);
                    for (Class c : classList2) {
                        try {
                            rentDao.timeTableDetection(null, c.getClassId(), con2, new Date(),c);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        dbUtil.closeCon(con2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 5000);
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
        classList = classDao.getclassList(connection, className, classStatus,null);
        for (int i = 1; i <= ((classList.size())/15+1); i++) {
            pageNoList.add(i);
        }
        if (pageNo == null) {
            pageNo = "1";
        }
            classList = classDao.getclassList(connection, className, classStatus,pageNo);

        mainPage= "user/rentClass.jsp";
        dbUtil.closeCon(connection);
        return SUCCESS;
    }

    public String rentSubmit() throws Exception {
        Connection con = dbUtil.getCon();
        StudentDao studentDao = new StudentDao();
        UserDao userDao = new UserDao();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date s_Time = sdf.parse(startTime);
        Date e_Time = sdf.parse(endTime);
        Class aClass = classDao.getclassByid(con, String.valueOf(classId));
        int studentId = userDao.getStudentIdByuserName(userName, con);
        Student student = studentDao.getStudentById(con, String.valueOf(studentId));
        Rent rent = new Rent();
        rent.setClassStatus(aClass.getClassStatus());
        rent.setRentStatus("未处理");
        rent.setClassId(classId);
        if (s_Time.getTime() < ((new Date()).getTime() + 21600000)) {
            JSONObject result = new JSONObject();
            result.put("error", "为保证教室使用不冲突,请开始时间设置为6小时之后");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if (s_Time.getTime() > e_Time.getTime()) {
            JSONObject result = new JSONObject();
            result.put("error", "错误!开始时间必须小于结束时间");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if ((e_Time.getTime()-s_Time.getTime()) >21600000 ) {
            JSONObject result = new JSONObject();
            result.put("error", "最长租用时间为六个小时!");
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

        return null;

    }

}
