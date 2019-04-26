package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.GradeDao;
import dao.MajorDao;
import dao.MajorDaoUtil;
import dao.StudentDao;
import model.Grade;
import model.Major;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import util.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GradeAction extends ActionSupport {
    private DbUtil dbUtil = new DbUtil();
    private GradeDao gradeDao = new GradeDao();
    private List<Grade> gradeList = new ArrayList<>();
    private MajorDaoUtil majorDaoUtil = new MajorDaoUtil();
    private String mainPage;
    private Grade grade;
    private String gradeId;
    private String error;
    private List<Major> majorList = new ArrayList<>();
    private MajorDao majorDao = new MajorDao();

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }


    public String preSave() {
        Connection con = null;
        try {
            con = dbUtil.getCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(gradeId)) {


            try {
                grade = gradeDao.getGradeById(con, gradeId);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            majorList = majorDao.majorList(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mainPage = "grade/gradeSave.jsp";
        return SUCCESS;
    }

    public String list() {
        Connection con = null;
        try {
            con = dbUtil.getCon();
            gradeList = gradeDao.gradeList(con);
            mainPage = "grade/gradeList.jsp";
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

    public String save() {
        Connection con = null;

        try {
            con = dbUtil.getCon();
            mainPage = "grade/gradeSave.jsp";
            majorList = majorDao.majorList(con);
            int majorId = majorDaoUtil.selectMajorIdByName(con, grade.getMajorName());
            if (majorId == 0) {
                error = "没有该专业的信息,请填写正确的专业名称";
                return ERROR;
            }
            grade.setMajorId((majorId));

            if (StringUtil.isNotEmpty(gradeId)) {
                grade.setGradeId(Integer.parseInt(gradeId));
                gradeDao.gradeUpdate(con, grade);
            } else {
                gradeDao.gradeAdd(con, grade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "save";
    }

    public String delete() {

        Connection con = null;
        System.out.println("成功");
        try {
            StudentDao studentDao = new StudentDao();
            con = dbUtil.getCon();
            JSONObject resultJson = new JSONObject();
            if ((studentDao.getstudentListBymajorIdorGradeId(con, null, Integer.parseInt(gradeId))).size() > 0) {
                resultJson.put("error", "年级下有学生数据,无法删除!");
                ResponseUtil.write(resultJson, ServletActionContext.getResponse());
                return "delete";
            }else {   gradeDao.gradeDelete(con, Integer.parseInt(gradeId));
                resultJson.put("success", true);}



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "save";
    }
}
