<%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/3/7
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    function queryGrade() {
        var select=document.getElementById('majorName');
        var m_Name = select.options[select.selectedIndex].getAttribute("id");
        $.ajax({
            url: "gradeList",
            data : {"majorId":m_Name},
            type: "post",
            success: function (result) {
                var result = eval('(' + result + ')');
                var gradeName = document.getElementById("gradeName");
                gradeName.innerHTML = "";
                for (var i = 0; i < result.gradeList.length; i++) {
                    gradeName.innerHTML += "<option  value='" + result.gradeList[i].gradeName + "'>" + result.gradeList[i].gradeName + "</option>";
                }
            }
        });

    }


</script>

<div class="modal fade" id="mymodal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">您好</h4>
            </div>
            <div class="modal-body" id="dialogs">
                <p id="error" name="error">${error}</p>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e75f49; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;">
                    我知道了
                </button>
            </div>
        </div>
    </div>
</div>
<div class="data_list">
    <div class="data_content">
        <form style="margin-top: 25px" action="saveStudent" method="post">
            <table  align="center">
                <tr>
                    <td ><label>学生姓名</label></td>
                    <td><input type="text" id="studentName" name="student.stuName" value="${student.stuName}" required="required"></td>


                    <td><label>学号</label></td>
                    <td><input type="number" id="stuNo" name="student.stuNo" value="${student.stuNo}" required="required">
                    </td>
                </tr>
                <tr>
                    <td><label>性别</label></td>
                    <td><select id="stuSex" name="student.stuSex" required="required">
                        <option value="${student.stuSex}">请选择性别...</option>
                        <option value="男" }>男</option>
                        <option value="女" }>女</option>
                    </select></td>

                    <td><label>家庭地址</label></td>
                    <td><input type="text" id="stuNation" name="student.stuNation" value="${student.stuNation}" required="required">
                    </td>
                </tr>
                <tr>
                    <td><label>出生日期</label></td>
                    <td>
                        <input type="date" id="stuBirthday" name="stuBirthday"
                               value="${student.stuBirthday}" required="required"></td>
                    <td><label>政治面貌</label></td>
                    <td><input type="text" id="stuZzmm" name="student.stuZzmm" value="${student.stuZzmm}" required="required">
                    </td>
                </tr>
                <tr>
                    <td><label>学生描述</label></td>
                    <td><input type="text" id="stuDesc" name="student.stuDesc" value="${student.stuDesc}" required="required">
                    </td>
                    <td><label>所在专业:</label></td>
                    <td><select id="majorName" name="student.majorName" required="required"  onchange="queryGrade()">
                        <option value="${student.majorName}">${student.majorName}
                        </option>
                        <c:forEach var="major" items="${majorList}">
                            <option value="${major.majorName}" id="${major.majorId}">${major.majorName}</option>
                        </c:forEach></select>
                    </td>
                </tr>
                <tr>
                    <td><label>所在年级:</label></td>
                    <td><select id="gradeName" name="student.gradeName" required="required">
                        <option value="${student.gradeName}">${student.gradeName}</option>

                    </select>
                    </td>
                </tr>
                <tr style="position: relative; left: 50px">
                    <td><input type="hidden" id="studentId" name="studentId" value="${student.studentId}" >
                        <input style="position: relative; left: 240px" type="submit" class="btn btn-primary" value="保存">
                    </td>
                    <td><input style="position: relative; left: 250px" type="button" class="btn btn-primary" value="返回"
                               onclick="javascript:history.back()"/>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>