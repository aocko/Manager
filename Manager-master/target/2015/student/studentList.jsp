<%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/3/6
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">var Id = 0;

function dialog(studentId) {
    $("#deleteModal").modal();
    Id = studentId;
};

function studentDelete() {
    $.post("student_delete", {studentId: Id}, function (r) {
    });
    window.location.reload();

}

function submitStudent(pageNo) {
    var form = document.getElementById('form2');
    form.action = "student_list?pageNo=" + pageNo + "";
    form.submit();
}
</script>
<div class="modal fade" id="deleteModal" tabindex="-1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                    class="sr-only">关闭</span></button>
            <h4 class="modal-title">您好</h4>
        </div>
        <div class="modal-body" id="dialogs1"><p>是否删除该数据?</p></div>
        <div class="modal-footer">
            <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e73013; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;"
                    onclick="studentDelete(${student.studentId})">是
            </button>
            <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e1dfe7; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;">
                否
            </button>
        </div>
    </div>


</div>
<div class="data_list">
    <div class="search_content table-responsive">
        <form action="student_list" method="post" style="margin-top: 30px" id="form2">
            <table align="center">
                <tr>
                    <td><label>学号</label></td>
                    <td><input type="text" id="stuNo" name="stuNo" value="${stuNo}"/></td>
                    <td width="30px">&nbsp;</td>
                    <td><label>学生姓名</label></td>
                    <td><input type="text" id="stuName" name="stuName" value="${stuName}"/></td>
                </tr>
                <tr>
                    <td><label>所在专业:</label></td>
                    <td><select id="majorName" name="majorName" >
                        <option value="未选择">请选择专业...</option>
                        <c:forEach var="major" items="${majorList}">
                            <option value="${major.majorName}">${major.majorName}</option>
                        </c:forEach></select>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                        <lable>性别：</lable>
                    </td>
                    <td>
                        <select id="stuSex" name="stuSex">
                            <option value="未选择">请选择性别...</option>
                            <option value="男" }>男</option>
                            <option value="女" }>女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                    </td>
                    <td colspan="2">
                        <button class="btn btn-primary" type="submit">查询</button>
                    </td>
                </tr>
            </table>
        </form>
        <button class="btn btn-mini btn-primary" type="button" style="float: right;margin-bottom: 5px;"
                onclick="javascript:window.location='student_preSave'">添加学生信息
        </button>
    </div>
    <div class="data_content">
        <table class="table table-bordered table-hover" style="background-color: #ffffff">
            <tr>
                <th>序号</th>
                <th>学号</th>
                <th>学生姓名</th>
                <th>性别</th>
                <th class="hidden-phone">家庭地址</th>
                <th class="hidden-phone">出生日期</th>
                <th class="hidden-phone">政治面貌</th>
                <th class="hidden-phone">学生描述</th>
                <th>年级</th>
                <th>专业</th>

            </tr>
            <c:forEach var="student" items="${studentList}" varStatus="status">
                <tr>
                    <td>${status.index+1+(pageNo-1)*15}</td>

                    <td>${student.stuNo}</td>
                    <td>${student.stuName}</td>
                    <td>${student.stuSex}</td>
                    <td class="hidden-phone">${student.stuNation}</td>
                    <td class="hidden-phone">${student.stuBirthday}</td>
                    <td class="hidden-phone">${student.stuZzmm}</td>
                    <td class="hidden-phone">${student.stuDesc}</td>
                    <td>${student.gradeName}</td>
                    <td>${student.majorName}</td>

                    <td>
                        <button class="btn btn-mini btn-info" type="button"
                                onclick="javascript:window.location='student_preSave?studentId=${student.studentId}'">修改
                        </button>&nbsp;&nbsp;<button class="btn btn-mini btn-danger " type="button"
                                                     onclick="dialog(${student.studentId})">删除
                    </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div></div>
    </div>


</div>
<div class="pagination" style="text-align: center">
    <ul>
        <c:forEach var="pageNo" items="${pageNoList}">
            <li><a href="#" onclick="submitStudent(${pageNo})" >${pageNo}</a></li>
        </c:forEach>
    </ul>
</div>
