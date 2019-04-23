package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.ClassDao;
import dao.GradeDao;
import dao.MajorDao;
import model.Class;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import util.DbUtil;
import util.ResponseUtil;
import util.StringUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ClassAction extends ActionSupport {
    private List<Class> classList=new ArrayList<>();
    private Class aClass;
    private String classId;
    private String mainPage;
    private String error;
    private ClassDao classDao = new ClassDao();
    private GradeDao gradeDao = new GradeDao();
    private MajorDao majorDao = new MajorDao();
    private DbUtil dbUtil = new DbUtil();
    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public String list() throws Exception {
        Connection con=null;
        con = dbUtil.getCon();
        classList = classDao.getclassList(con);
        mainPage = "class/classList.jsp";
        dbUtil.closeCon(con);
        return SUCCESS;
    }
    public String preSave() throws Exception {
        Connection con=null;
        con = dbUtil.getCon();
        if (StringUtil.isNotEmpty(classId)) {
            aClass=classDao.getclassByid(con, classId);
        }
        dbUtil.closeCon(con);
        mainPage = "class/classSave.jsp";
        return SUCCESS;

    }

    public String save() throws Exception {
        Connection con = null;
        con = dbUtil.getCon();
        if (StringUtil.isNotEmpty(classId)) {
            aClass.setClassId(Integer.parseInt(classId));
            classDao.classUpdate(con, aClass);
        } else {
            classDao.classAdd(con, aClass);
        }
        mainPage = "class/classSave.jsp";
        dbUtil.closeCon(con);
        return "save";
    }
    public  String delete() throws Exception {
        Connection con = null;
        con = dbUtil.getCon();
        classDao.classDelete(con, classId);
        JSONObject resultJson=new JSONObject();
            resultJson.put("success", true);
        ResponseUtil.write(resultJson, ServletActionContext.getResponse());
        dbUtil.closeCon(con);

        return SUCCESS;
    }

    public String classDetail() throws Exception {
        Connection connection = dbUtil.getCon();
       aClass= classDao.getclassByid(connection, classId);
        JSONObject result = new JSONObject();
        result.put("success", aClass);
        ResponseUtil.write(result, ServletActionContext.getResponse());
        return null;
    }
}
