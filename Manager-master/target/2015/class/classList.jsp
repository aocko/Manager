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

function dialog1(classId) {
    $("#deleteModal").modal();
    Id = classId;
};

function classDelete() {
    $.post("class_delete", {classId: Id}, function (result) {
        var result = eval('(' + result + ')');
        if (result.error) {
            alert(result.error);
        } else {
            alert("删除成功！");
            window.location.href = "${pageContext.request.contextPath}/class_list";
        }
    });


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
                    onclick="classDelete()">是
            </button>
            <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e1dfe7; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;">
                否
            </button>
        </div>
    </div>


</div>

<div class="data_list">
    <div class="search_content">
        <button class="btn btn-mini btn-primary" type="button" style="float: right;margin-bottom: 5px;"
                onclick="javascript:window.location='class_preSave'">添加教室
        </button>
    </div>
    <div class="data_content">
        <table class="table table-bordered table-hover table-striped" style="background-color: #ffffff">
            <tr>
                <th>序号</th>
                <th>教室名称</th>
                <th>教室类别</th>
                <th>设备状态</th>
                <th>教室备注</th>
                <th>教室大小</th>
                <th>教室状态</th>
                <th>操作</th>
            </tr>
            <c:forEach var="aClass" items="${classList}" varStatus="status">
                <tr>
                    <td>${status.index+1+(pageNo-1)*15}</td>
                    <td>${aClass.className}</td>
                    <td>${aClass.classType}</td>
                    <td>${aClass.eqCondition}</td>
                    <td>${aClass.classDesc}</td>
                    <td>${aClass.classSize}</td>
                    <td>${aClass.classStatus}</td>
                    <td>
                        <button class="btn btn-mini btn-info" type="button"
                                onclick="javascript:window.location='class_preSave?classId=${aClass.classId}'">修改
                        </button>&nbsp;&nbsp;
                        <button class="btn btn-mini btn-danger " type="button" onclick="dialog1(${aClass.classId})">删除
                        </button>
                        &nbsp;&nbsp;<button btn btn-mini btn-info type="button"
                                            onclick="javascript:window.location='class_timetable?classId=${aClass.classId}'">
                        查看课程表
                    </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>

<div class="pagination" style="text-align: center">
    <ul>
        <c:forEach var="pageNo" items="${pageNoList}">
            <li><a href="class_list?pageNo=${pageNo}" >${pageNo}</a></li>
        </c:forEach>
    </ul>
</div>
