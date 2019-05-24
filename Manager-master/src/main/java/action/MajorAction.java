package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.GradeDao;
import dao.MajorDao;
import dao.StudentDao;
import model.Grade;
import model.Major;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import util.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MajorAction extends ActionSupport {
    private DbUtil dbUtil = new DbUtil();
    private String mainPage;
    private Major major;
    private String majorId;
    private String error;
    private List<Major> majorList = new ArrayList<>();
    private MajorDao majorDao = new MajorDao();
    private GradeDao gradeDao = new GradeDao();
    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

    public String preSave() {
        Connection con = null;
        try {
            con = dbUtil.getCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(majorId)) {
            try {
                major=majorDao.getMajorById(con, majorId);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            }

        try {
            majorList = majorDao.majorList(con);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mainPage = "major/majorSave.jsp";
        return SUCCESS;
    }

    public String list() {
        Connection con = null;
        try {
            con = dbUtil.getCon();
            majorList = majorDao.majorList(con);
            mainPage = "major/majorList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return SUCCESS;
    }
    public String save(){
        Connection con=null;

        try {
            con=dbUtil.getCon();
            mainPage = "major/majorSave.jsp";


            if (StringUtil.isNotEmpty(majorId)) {
                major.setMajorId(Integer.parseInt(majorId));
                majorDao.majorUpdate(con, major);
            }else {
                majorDao.majorAdd(con, major);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "save";
    }
    public String delete() throws Exception {
        StudentDao studentDao = new StudentDao();
        Connection con=null;

            con=dbUtil.getCon();
          JSONObject resultJson=new JSONObject();
            if ((gradeDao.gradeListBymajorId(con, Integer.parseInt(majorId))).size()!=0) {
             resultJson.put("error", "该专业下有年级数据，无法删除！");
                ResponseUtil.write(resultJson, ServletActionContext.getResponse());
                return "delete";
            }else  if ((studentDao.getstudentListBymajorIdorGradeId(con, Integer.parseInt(majorId), null)).size() > 0) {
                resultJson.put(error, "该专业下有学生数据,无法删除!");
                ResponseUtil.write(resultJson, ServletActionContext.getResponse());
                return "delete";
            }


            else {
            majorDao.majorDelete(con, Integer.parseInt(majorId));
            resultJson.put("success", true);}




            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return "save";
    }
}
