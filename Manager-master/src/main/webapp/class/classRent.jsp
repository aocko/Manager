<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<script type="text/javascript">var Id = 0;

var rId = 0;
var cId = 0;

function RejectModal(rentId, classId) {
    $("#rejectModal").modal("show");
    rId = rentId;
    cId = classId;

}

function reject() {
    var rejectReason1 = document.getElementById("rejectReason").value;
    $.post("rent_status", {rentId: rId, classId: cId, rejectReason: rejectReason1}, function (r) {
        var result = eval('(' + r + ')');
        if (result.error) {
            alert(result.error);
        } else {
            window.location.href = "${pageContext.request.contextPath}/class_rent"
        }

    });


}

function acceptRent(rentId, classId) {
    rId = rentId;
    cId = classId;
    $.post("rent_status", {rentId: rId, classId: cId}, function (r) {
        var result = eval('(' + r + ')');
        if (result.error) {
            alert(result.error);
        } else {
            alert("处理成功");
            window.location.href = "${pageContext.request.contextPath}/class_rent"

        }

    });

}
window.onload = function () {
    var table = document.getElementById("table");
    var rows = table.rows;
    for (var i = 1; i < rows.length; i++) {
        if ((rows[i].cells[11].innerText.trim()) == "拒绝") {
            rows[i].cells[13].children[0].setAttribute("disabled", "disabled");
        }
        if ((rows[i].cells[11].innerText.trim()) == "同意") {
            rows[i].cells[12].children[0].setAttribute("disabled", "disabled");
        }
    }
}
</script>


<div class="modal fade" id="rejectModal" tabindex="-1" role="dialog" aria-labelledby="rejectModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel1">拒绝</h4>
            </div>
            <div class="modal-body"><label for="rejectReason">拒绝理由(请详细填写,以便管理员审核)</label>
                <textarea class="form-control" rows="3" id="rejectReason" name="rejectReason" style="width:100%;resize:none;"></textarea></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-danger"  onclick="reject()">拒绝</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
    <div class="data_list">
        <div class="search_content">
            <form action="class_rent" method="post" style="margin-top: 30px">
                <table align="center">

                    <tr>
                        <td>
                            <lable>教室类别：</lable>
                        </td>
                        <td>
                            <select id="status" name="status">
                                <option value="所有">所有</option>
                                <option value="空闲" }>空闲</option>
                                <option value="使用中" }>使用中</option>
                            </select>
                        </td>
                        <td>
                            <lable>开始时间：</lable>
                        </td>
                        <td><input type="datetime-local" id="startTime" name="startTime" value="${startTime}">

                        </td>
                        <td>
                            <lable>到期时间：</lable>
                        </td>
                        <td>
                            <input type="datetime-local" id="endTime" name="endTime" value="${endTime}">
                        </td>
                        <td colspan="2">
                        <button class="btn btn-primary"  style="margin-bottom: 12px" type="submit">查询</button>
                    </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="data_content">
            <table id="table" class="table table-bordered table-hover table-striped" style="background-color: #ffffff">
                <tr>
                    <th>序号</th>
                    <th>教室名称</th>
                    <th>教室类别</th>
                    <th>申请时间</th>
                    <th>开始时间</th>
                    <th>到期时间</th>
                    <th>学生名称</th>
                    <th>学生学号</th>
                    <th>申请理由</th>
                    <th>教室状态</th>
                    <th>用户昵称</th>
                    <th>状态</th>
                    <th colspan="2">操作</th>
                </tr>
                <c:forEach var="rent" items="${rentList}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${rent.className}</td>
                        <td>${rent.classType}</td>
                        <td>${rent.rentTime}</td>
                        <td>${rent.startTime}</td>
                        <td>${rent.endTime}</td>
                        <td>${rent.stuName}</td>
                        <td>${rent.stuNo}</td>
                        <td>${rent.reason}</td>
                        <td>${rent.classStatus}</td>
                        <td>${rent.userName}</td>
                        <td>${rent.rentStatus}</td>
                        <td>
                            <button class="btn btn-mini btn-info" type="button"
                                    onclick="acceptRent(${rent.rentId},${rent.classId})">
                                同意
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-mini btn-danger " type="button"
                                    onclick="RejectModal(${rent.rentId},${rent.classId})"
                            >
                                拒绝
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </div>