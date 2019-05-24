<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/3/28
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<%
    User user = (User) session.getAttribute("currentUser");
    String userName = user.getUserName();
%>

<script type="text/javascript">
    window.onload = function () {
        var table = document.getElementById("table");
        var rows = table.rows;
        for (var i = 1; i < rows.length; i++) {
            if ((rows[i].cells[5].innerText.trim()) == "禁止使用") {
                rows[i].cells[7].children[0].setAttribute("disabled", "disabled");
            }
        }
    }
    $(function () {
        $(".xq").click(function () {
            var code = $(this).attr("code");
            $.ajax({
                url: "classDetail",
                data: {classId: code},
                type: "post",
                success: function (result) {
                    var result = eval('(' + result + ')');
                    var str = "<div>教室大小：" + result.success.classSize + "</div><div>设备状态：" + result.success.eqCondition + "</div><div>教室描述：" + result.success.classDesc + "</div>";
                    $("#neirong").html(str);
                    $('#myModal').modal('show');
                }
            });
        })
    });
    var Id = 0;

    function OpenrentModal(classId) {
        $('#rent').modal('show');
        Id = classId;
    }

    function rent(userName) {
        var startTime = document.getElementById("startTime").value;
        var endTime = document.getElementById("endTime").value;
        var reason = document.getElementById("reason").value;

        $.post("rent_submit", {
            classId: Id,
            userName: userName,
            startTime: startTime,
            endTime: endTime,
            reason: reason
        }, function (r) {
            var result = eval('(' + r + ')')
            if (result.error) {
                alert(result.error);
            } else {
                window.location.href = "${pageContext.request.contextPath}/rent_list"
            }
        })
    }

    function submitQuery(pageNo) {
        var form = document.getElementById('form1');
        form.action = "query_class?pageNo=" + pageNo + "";
        form.submit();
    }
</script>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">教室详情</h4>
            </div>
            <div class="modal-body" id="neirong"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="rent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="rentModal">申请</h4>
            </div>
            <div class="modal-body">
                <label>选择开始时间：</label>
                <input type="datetime-local" class="form-control" id="startTime" name="startTime" required="required">
                <label>选择到期时间：</label>
                <input type="datetime-local" class="form-control" id="endTime" name="endTime" required="required">
                <label for="reason">申请理由(请详细填写,以便管理员审核)</label>
                <textarea class="form-control" rows="3" id="reason" name="reason" style="resize:none"></textarea>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default  btn-info" type="button"
                        onclick="rent('<%=userName%>')">
                    申请
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<form class="form-inline" action="query_class" method="post" id="form1">
    <div class="form-group">
        <label for="className">教室名称</label>
        <input type="text" class="form-control" id="className" name="className" value="${className}">
    </div>
    <div class="form-group">
        <label for="classStatus">教室状态</label>
        <select id="classStatus" name="classStatus" required="required" class="form-control">
            <c:forEach var="status" items="${statusList}">
                <option value="${status}">${status}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="btn btn-default btn-info">查询</button>
</form>
<table class="table table-hover table-bordered table-condensed table-striped" style="background-color: #ffffff;" id="table">
    <thead>
    <tr style="font-size: 14px;text-align: center">
        <th style="text-align: center">序号</th>
        <th style="text-align: center">教室名称</th>
        <th style="text-align: center">教室类型</th>
        <th style="text-align: center">教室状态</th>
        <th style="text-align: center" class="hidden-xs">教室大小</th>
        <th style="text-align: center" class="hidden-xs">设备状态</th>
        <th style="text-align: center" class="hidden-xs">教室备注</th>
        <th style="text-align: center" colspan="2" class="hidden-xs">操作</th>
        <th style="text-align: center" colspan="3" class="hidden-lg">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="aClass" items="${classList}" varStatus="status">
        <tr style="font-size: 14px">
            <td align="center">${status.index+1+15*(pageNo-1)}</td>
            <td align="center">${aClass.className}</td>
            <td align="center">${aClass.classType}</td>
            <td align="center">${aClass.classStatus}</td>
            <td align="center" class="hidden-xs">${aClass.classSize}</td>
            <td align="center" class="hidden-xs">${aClass.eqCondition}</td>
            <td align="center" class="hidden-xs">${aClass.classDesc}</td>
            <td align="center">
                <button class="btn btn-primary btn-xs btn-info" type="button"
                        onclick="OpenrentModal(${aClass.classId})">
                    申请
                </button>
            </td>
            <td align="center">
                <button class="btn btn-primary btn-xs btn-info" type="button"
                        onclick="javascript:window.location='class_timetable2?classId=${aClass.classId}&userName=<%=userName%>'">
                    课程
                </button>
            </td>
            <td align="center" class="visible-xs">
                <button class="btn btn-primary btn-xs btn-info xq" id="xq" type="button" code="${aClass.classId}">查看
                </button>
            </td>

        </tr>
    </c:forEach>
    </tbody>

</table>
<nav aria-label="Page navigation" style="text-align: center">
    <ul class="pagination">

        <c:forEach var="pageNo" items="${pageNoList}">
            <li><a href="#" onclick="submitQuery(${pageNo})">${pageNo}</a></li>
        </c:forEach>

    </ul>
</nav>