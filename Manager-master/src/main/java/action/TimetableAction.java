package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.TimetableDao;
import model.Timetable;
import util.DbUtil;
import util.StringUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TimetableAction extends ActionSupport {
    private List<Timetable> timetableList=new ArrayList<>();;
    private String classId;
    private Timetable timetable;
    private String mainPage;
    private DbUtil dbUtil = new DbUtil();
    private TimetableDao timetableDao = new TimetableDao();
    private String aClassId;
    private String error;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getaClassId() {
        return aClassId;
    }

    public void setaClassId(String aClassId) {
        this.aClassId = aClassId;
    }

    public List<Timetable> getTimetableList() {
        return timetableList;
    }

    public void setTimetableList(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public String list() throws Exception {
        Connection con = dbUtil.getCon();
        timetableList = timetableDao.timetableList(con, classId);
        aClassId = classId;
        if (timetableList.size() == 0) {
            error = "未找到该教室的课程表,请重新添加";
            if (userName != null) {
                error = "未找到该教室的课程表,请通知管理员重新添加";
            }
        }
        if (timetableList.size() == 0&&userName != null) {
            error = "未找到该教室的课程表,请通知管理员重新添加";
        }
        mainPage = "timetable/timetableList.jsp";
        if (userName != null) {
            mainPage = "timetable/timetableList2.jsp";
            dbUtil.closeCon(con);
            return "user";
        }
        dbUtil.closeCon(con);
        return SUCCESS;
    }
    public String preSave() throws Exception {
        Connection con = dbUtil.getCon();
        if (StringUtil.isNotEmpty(classId)) {
            timetableList = timetableDao.timetableList(con, classId);
            aClassId = classId;
        }
        dbUtil.closeCon(con);
        mainPage = "timetable/timetableSave.jsp";
        return SUCCESS;
    }

    public String Save() throws Exception {
        Connection con = dbUtil.getCon();

        if ( (timetableDao.timetableList(con, classId) ).size()== 0) {
            timetableDao.saveTimetableList(con, classId, timetableList);
            mainPage = "timetable/timetableSave.jsp";
            return "save";
        }
        if (StringUtil.isNotEmpty(classId)&&StringUtil.isEmpty(aClassId)) {
            timetableDao.timetableListUpdate(con, classId, timetableList);
        }
        mainPage = "timetable/timetableSave.jsp";
        dbUtil.closeCon(con);
        return "save";
    }


}
