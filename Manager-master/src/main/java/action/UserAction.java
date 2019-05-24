package action;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;
import dao.*;
import model.*;
import model.Class;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import util.DbUtil;
import util.ResponseUtil;
import util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserAction extends ActionSupport implements ServletRequestAware {
    private HttpServletRequest request;

    private String error;

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

    private List<Major> majorList;
    private String imageCode;
    private User user;
    private String majorName;
    private UserDao userDao = new UserDao();
    private DbUtil dbUtil = new DbUtil();
    private GradeDao gradeDao = new GradeDao();
    private String r_userName;
    private String r_password;
    private String userType;
    private String r_password2;
    private List<Grade> gradeList;
    private String gradeName;
    private String stuName;
    private Integer stuNo;

    private String oldPassword;
    private String majorId;
    private ClassDao classDao = new ClassDao();
    private  RentDao rentDao = new RentDao();

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private String newPassword;
    private String status;

    private MajorDaoUtil majorDaoUtil = new MajorDaoUtil();
    private StudentDao studentDao = new StudentDao();
    MajorDao majorDao = new MajorDao();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuNo() {
        return stuNo;
    }

    public void setStuNo(Integer stuNo) {
        this.stuNo = stuNo;
    }

    public String getR_password2() {
        return r_password2;
    }

    public void setR_password2(String r_password2) {
        this.r_password2 = r_password2;
    }

    public String getR_password() {
        return r_password;
    }

    public void setR_password(String r_password) {
        this.r_password = r_password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getR_userName() {
        return r_userName;
    }

    public void setR_userName(String r_userName) {
        this.r_userName = r_userName;
    }

    public String login() throws Exception {
     Timer timer = new Timer();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Rent rent = new Rent();
        final int[] flag = {0};

        HttpSession session = request.getSession();
        if (status!=null&&status.equals("exit")) {
            session.removeAttribute("currentUser");
            return ERROR;
        }
        if (user == null) {
            error = "请登录!";
            return ERROR;
        }
        if (StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
            error = "用户名或密码为空!";
            return ERROR;
        }
        if (StringUtil.isEmpty(imageCode)) {
            error = "验证码为空";
            return ERROR;
        }
        if (!imageCode.equals(session.getAttribute("sRand"))) {
            error = "验证码错误";
            return ERROR;
        }
        Connection con = null;
        con = dbUtil.getCon();
        User currentUser = userDao.login(con, user);
        dbUtil.closeCon(con);
        if (currentUser == null) {
            error = "用户名或密码错误!";
            return ERROR;
        } else {
            session.setAttribute("currentUser", currentUser);
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
                        flag[0] += 1;
                        if (flag[0] >= 6) {
                            timer.cancel();
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
            }, 0, 600000);
            if (currentUser.getUserType().equals("学生") || currentUser.getUserType().equals("老师")) {
                return "student";
            }
            return SUCCESS;
        }


    }

    public String checkName() throws Exception {
        Connection con = dbUtil.getCon();
        r_userName = request.getParameter("r_userName");
        boolean flag = userDao.checkName(r_userName, con);
        JSONObject result = new JSONObject();
        if (flag) {
            result.put("success", true);
        } else {
            result.put("error", true);
        }
        ResponseUtil.write(result, ServletActionContext.getResponse());
        dbUtil.closeCon(con);
        return SUCCESS;
    }

    public String gradeList() throws Exception {
        Connection con = dbUtil.getCon();
        majorList = majorDao.majorList(con);
        if (majorId != null) {
            gradeList = gradeDao.gradeList(con, Integer.parseInt(majorId));
        }else {
            gradeList = gradeDao.gradeList(con,majorList.get(0).getMajorId());

        }

        JSONObject result = new JSONObject();
        result.put("gradeList", gradeList);
        result.put("majorList", majorList);
        ResponseUtil.write(result, ServletActionContext.getResponse());
        dbUtil.closeCon(con);
        return SUCCESS;
    }

    public String registUser() throws Exception {
        Connection con = dbUtil.getCon();
        User user = new User();
        user.setUserName(r_userName);
        user.setPassword(r_password);
        user.setUserType(userType);
        Student student = new Student();

        int majorId = majorDaoUtil.selectMajorIdByName(con, majorName);
        int gradeId = majorDaoUtil.selectGradeIdByNameandId(con, gradeName, majorId);
        if (gradeId == 0) {
            error = "请选择正确的年级";
            return ERROR;
        }student.setStuName(stuName);
        student.setStuNo(stuNo);
      if (userType.equals("学生")){ student.setGradeId(gradeId);
        student.setGradeName(gradeName);
        student.setMajorId(majorId);
        student.setMajorName(majorName);}
        boolean flag = userDao.checkName(r_userName, con);
        if (r_userName.length() > 8) {
            error = "用户名不得超过8个字符";
            return ERROR;
        }
        if (!r_password2.equals(r_password)) {
            error = "两次输入密码不一致，请重新输入";
            return ERROR;
        }
        if (r_password.length() > 10) {
            error = "用户名不得超过10个字符";
            return ERROR;
        }
        if (r_userName.length() < 6) {
            error = "用户名不得少于6个字符";
            return ERROR;
        }
        if (flag) {
        } else {
            error = r_userName + "已被注册";
            return ERROR;
        }
        Integer studentId=0;
        Student student1 = studentDao.getStudentBystuNo(con, stuNo, stuName);
        if (student1.getStudentId() == 0) {
            studentId = studentDao.studentAdd(con, student,userType);
        } else if (userDao.selectByStudentid(con, student1.getStudentId()) != null) {
            error = "该学生或教师已注册，请勿重复注册！";
            return ERROR;
        } else {
            studentId = student1.getStudentId();
        }
        user.setStudentId(studentId);
        userDao.registUser(user, con);
        dbUtil.closeCon(con);
        error = "注册成功";
        return SUCCESS;
    }

    public String changePassword() throws Exception {
        HttpSession session = request.getSession();
        Connection con = dbUtil.getCon();
        User user = (User) session.getAttribute("currentUser");
        JSONObject result = new JSONObject();
        if (!user.getPassword().equals(oldPassword)) {
            result.put("error", "当前密码错误,请重新输入!");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if (newPassword.length() < 6) {
            result.put("error", "密码长度必须大于等于6个字符!");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }
        if (newPassword.length() > 10) {
            result.put("error", "密码长度必须小于10个字符!");
            ResponseUtil.write(result, ServletActionContext.getResponse());
            return null;
        }

        userDao.changePassword(user.getUserId(), newPassword, con);
        ResponseUtil.write(result, ServletActionContext.getResponse());
        return null;

    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
