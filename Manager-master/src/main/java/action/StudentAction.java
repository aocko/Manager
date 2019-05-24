package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.*;
import model.Grade;
import model.Major;
import model.Student;
import model.User;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import util.DbUtil;
import util.ResponseUtil;
import util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;

public class StudentAction extends ActionSupport implements ServletRequestAware {
    private HttpServletRequest request;
    private String stuName;
    private String stuNo;
    private String stuSex;
    private int gradeId;
    private List<Student> studentList = new ArrayList<>();
    private String studentId;
    private String error;
    private Student student;
    private String mainPage;
    private StudentDao studentDao = new StudentDao();
    private DbUtil dbUtil = new DbUtil();
    private String majorName;
    private List<Major> majorList;
    private MajorDao majorDao = new MajorDao();
    private List<Grade> gradeList = new ArrayList<>();
    private String stuBirthday;
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

    public String getStuBirthday() {
        return stuBirthday;
    }

    public void setStuBirthday(String stuBirthday) {
        this.stuBirthday = stuBirthday;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    private GradeDao gradeDao = new GradeDao();
    private MajorDaoUtil majorDaoUtil = new MajorDaoUtil();

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String list() {
        Connection con = null;
        try {
            student = new Student();
            student.setMajorName(majorName);
            student.setStuName(stuName);
            if (StringUtil.isNotEmpty(stuNo)) {
                student.setStuNo(Integer.parseInt(stuNo));
            }
            student.setStuSex(stuSex);
            con = dbUtil.getCon();
            majorList = majorDao.majorList(con);
            studentList = studentDao.studentList(con, student,null);
            for (int i = 1; i <= ((studentList.size())/15+1); i++) {
                pageNoList.add(i);
            }
            if (studentList.size() == 15) {
                pageNoList = new ArrayList<>();
                pageNoList.add(1);
            }
            if (pageNo ==null) {
                pageNo ="1";
            }
            if (pageNo == null&&studentList.size()>=15) {
                studentList = studentList.subList(0, 15);
            } else {
                studentList = studentDao.studentList(con,student,pageNo);
            }
            mainPage = "student/studentList.jsp";
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

    public String preSave() {
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (StringUtil.isNotEmpty(studentId)) {
                student = studentDao.getStudentById(con, studentId);
            }
            majorList = majorDao.majorList(con);
            //  gradeList = gradeDao.gradeList(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mainPage = "student/studentSave.jsp";
        return SUCCESS;
    }

    public String save() {
        Connection con = null;

        try {
            con = dbUtil.getCon();
            mainPage = "student/studentSave.jsp";
            student.setStuBirthday(java.sql.Date.valueOf(stuBirthday));
            int majorId = majorDaoUtil.selectMajorIdByName(con, student.getMajorName());
            int gradeId = majorDaoUtil.selectGradeIdByNameandId(con, student.getGradeName(), majorId);
            majorList = majorDao.majorList(con);
            gradeList = gradeDao.gradeList(con, majorList.get(0).getMajorId());
            if (majorId == 0) {
                error = "没有该专业的信息,请选择正确的专业名称";
                return ERROR;
            }
            if (gradeId == 0) {
                error = "没有该年级的信息,请选择正确的年级名称";
                return ERROR;
            }
            student.setMajorId(majorId);
            student.setGradeId(gradeId);
            if (StringUtil.isNotEmpty(studentId)) {
                student.setStudentId(Integer.parseInt(studentId));
                studentDao.studentUpdate(con, student);
            } else {
                studentDao.studentAdd(con, student);
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

    public String saveChange() {
        Connection con = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        try {
            con = dbUtil.getCon();
            if (user.getUserType().equals("学生")) {
                mainPage = "user/changeDetail.jsp";int majorId = majorDaoUtil.selectMajorIdByName(con, student.getMajorName());
                int gradeId = majorDaoUtil.selectGradeIdByNameandId(con, student.getGradeName(), majorId);

                majorList = majorDao.majorList(con);
                gradeList = gradeDao.gradeList(con, majorList.get(0).getMajorId());
                if (majorId == 0) {
                    error = "没有该专业的信息,请选择正确的专业名称";
                    return ERROR;
                }
                if (gradeId == 0) {
                    error = "没有该年级的信息,请选择正确的年级名称";
                    return ERROR;
                }
                student.setMajorId(majorId);
                student.setGradeId(gradeId);
            }

            student.setStuBirthday(java.sql.Date.valueOf(stuBirthday));
            student.setStudentId(Integer.parseInt(studentId));
            try {
                studentDao.studentUpdate(con, student);
            } catch (Exception e) {
                studentDao.teacherUpdate(con, student);

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
        mainPage = "user/index.jsp";
        return SUCCESS;
    }

    public String delete() throws Exception {
        JSONObject result = new JSONObject();
        Connection con = null;
        con = dbUtil.getCon();
            if ((new UserDao().selectByStudentid(con, Integer.parseInt(studentId))) != null) {
                result.put("error", "该用户已注册，无法删除！");
                ResponseUtil.write(result, ServletActionContext.getResponse());
            }else {
                try {
                    studentDao.studentDelete(con, Integer.parseInt(studentId));
                    ResponseUtil.write(result, ServletActionContext.getResponse());
                } catch (Exception e) {
                    result.put("error", "该用户已注册，无法删除！");
                    ResponseUtil.write(result, ServletActionContext.getResponse());
                } finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        return null;
    }

    public String changeDetail() throws Exception {
        Connection con = dbUtil.getCon();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        student = studentDao.getStudentById(con, String.valueOf(user.getStudentId()));
       if (user.getUserType().equals("学生")){ majorList = majorDao.majorList(con);
        Iterator<Major> iterator = majorList.iterator();
        int flag = 0;
        while (iterator.hasNext()) {
            Major major = iterator.next();
            if (major.getMajorName().equals(student.getMajorName())) {
                flag = major.getMajorId();
                iterator.remove();
            }
        }
        gradeList = gradeDao.gradeList(con, flag);
        Iterator<Grade> it = gradeList.iterator();
        while (iterator.hasNext()) {
            Grade grade = it.next();
            if (grade.getGradeName().equals(student.getGradeName())) {
                it.remove();
            }
        }
           mainPage = "user/changeDetail.jsp";
       }else{
        mainPage = "user/changeDetailForTeacher.jsp";}
        dbUtil.closeCon(con);
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }
}
