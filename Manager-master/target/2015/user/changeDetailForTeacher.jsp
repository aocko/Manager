<%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/4/1
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form style="margin-top: 25px" action="saveChange" method="post" class="form-horizontal">

<div class="row">
    <div class="col-md-4 col-md-offset-4">
            <label>教师姓名</label>
           <input type="text" class="form-control" id="studentName" name="student.stuName" value="${student.stuName}" required="required"></td>


            <label>教工号</label>
           <input type="text"  class="form-control" id="stuNo" name="student.stuNo" value="${student.stuNo}" required="required">



            <label>性别</label>
            <select id="stuSex" name="student.stuSex" required="required" class="form-control">
                <option value="${student.stuSex}">请选择性别...</option>
                <option value="男" }>男</option>
                <option value="女" }>女</option>
            </select>

           <label>家庭地址</label>
           <input type="text" id="stuNation" class="form-control" name="student.stuNation" value="${student.stuNation}" required="required">



            <label>出生日期</label>

                <input type="date" id="stuBirthday" name="stuBirthday"
                       value="${student.stuBirthday}"  class="form-control" required="required">

           <input type="hidden" id="studentId" name="studentId" value="${student.studentId}" >
                <input style="position: relative; left: 140px;margin-top: 20px;" type="submit" class="btn btn-default" value="保存">

            <input style="position: relative; left: 150px;margin-top: 20px" type="button" class="btn btn-default " value="返回"
                       onclick="javascript:history.back()"/>&nbsp;&nbsp;
    </div>
</div>

</form>
