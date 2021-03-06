package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.ClassDao;
import dao.GradeDao;
import dao.MajorDao;
import model.Class;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import util.DbUtil;
import util.ResponseUtil;
import util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ClassAction extends ActionSupport  implements ServletRequestAware {
    private HttpServletRequest request;
    private List<Class> classList=new ArrayList<>();
    private Class aClass;
    private String classId;
    private String mainPage;
    private String error;
    private ClassDao classDao = new ClassDao();
    private GradeDao gradeDao = new GradeDao();
    private MajorDao majorDao = new MajorDao();
    private DbUtil dbUtil = new DbUtil();
    private List<Integer> pageNoList = new ArrayList<>();
    private String pageNo;

    public List<Integer> getPageNoList() {
        return pageNoList;
    }

    public void setPageNoList(List<Integer> pageNoList) {
        this.pageNoList = pageNoList;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

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
        classList = classDao.getclassList(con,null);
        for (int i = 1; i <= ((classList.size())/15+1); i++) {
            pageNoList.add(i);
        }
        if (classList.size() == 15) {
            pageNoList = new ArrayList<>();
            pageNoList.add(1);
        }

        if (pageNo == null&&classList.size()>=15) {
            classList = classList.subList(0, 15);
        } else {
            classList = classDao.getclassList(con,pageNo);
        }
        if (pageNo == null) {
            setPageNo("1");
        }
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
    public  String delete() throws IOException {
        Connection con = null;
        JSONObject resultJson=new JSONObject();
        try {con = dbUtil.getCon();
            classDao.classDelete(con, classId);
            resultJson.put("success", true);
            ResponseUtil.write(resultJson, ServletActionContext.getResponse());
            dbUtil.closeCon(con);
        } catch (Exception e) {
            resultJson.put("error", "该教室无法删除！有申请待处理中");
            ResponseUtil.write(resultJson, ServletActionContext.getResponse());
        }


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

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;

    }
}
