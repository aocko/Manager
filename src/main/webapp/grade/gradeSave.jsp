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
    // function checkForm() {
    //     var gradeName = $("#gradeName").val();
    //     if (gradeName == null || gradeName == "") {
    //         $("#error").html("年级不能为空");
    //         return false;
    //     }
    //
    // }
</script>
<div class="modal fade" id="mymodal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">您好</h4>
            </div>
            <div class="modal-body" id="dialogs">
                <p id="error" name="error">${error}</p>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e75f49; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;">我知道了
                </button>
            </div>
        </div>
    </div>
</div>
<div class="data_list">
    <div class="data_content">
        <form  style="margin-top: 25px" action="grade_save" method="post">
            <table width="40%" align="center" >
            <tr>
                <td><label>年级名称</label></td>
                <td><input type="text" id="gradeName" name="grade.gradeName" value="${grade.gradeName}" required="required"></td>
            </tr>
            <tr>
                <td><label>年级描述</label></td>
                <td><input type="text" id="gradeDesc" name="grade.gradeDesc" value="${grade.gradeDesc}"></td>
            </tr>
            <tr>
                <td>专业名称</td>
                <td><select id="majorName" name="majorName">
                    <option value="未选择">请选择专业...</option>
                    <c:forEach var="major" items="${majorList}">
                        <option value="${major.majorName}">${major.majorName}</option>
                    </c:forEach></select></td>
            </tr>
            <tr style="position: relative; left: 50px">
                <td><input type="hidden" id="gradeId" name="gradeId" value="${grade.gradeId}">
                    <input   style="position: relative; left: 100px" type="submit" class="btn btn-primary" value="保存"></td>
                <td><input style="position: relative; left: 100px" type="button" class="btn btn-primary" value="返回" onclick="javascript:history.back()"/>&nbsp;&nbsp; </td>
            </tr></table>
        </form>
    </div>
</div>