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
        <form  style="margin-top: 25px" action="major_save" method="post" >
            <table width="40%" align="center">
            <tr>
                <td><label>专业名称</label></td>
                <td><input type="text" id="majorName" name="major.majorName" value="${major.majorName}" required="required"></td>
            </tr>
            <tr>
                <td><label>专业描述</label></td>
                <td><input type="text" id="majorDesc" name="major.majorDesc" value="${major.majorDesc}"></td>
            </tr>
            <tr style="position: relative; left: 50px">
                <td><input type="hidden" id="majorId" name="majorId" value="${major.majorId}">
                    <input   style="position: relative; left: 100px" type="submit" class="btn btn-primary" value="保存"></td>
                <td><input style="position: relative; left: 100px" type="button" class="btn btn-primary" value="返回" onclick="javascript:history.back()"/>&nbsp;&nbsp; </td>
            </tr></table>
        </form>
    </div>
</div>