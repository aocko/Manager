<%@ page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
    function dialogB() {
        $('#Modal1').modal();

    }
    function rentForweek(userName) {
        var startTime = document.getElementById("startTime").value;
        var week = document.getElementById("week").value;
        var endTime = document.getElementById("endTime").value;
        var reason = document.getElementById("reason").value;
        var Id = document.getElementById("classId").value;

        $.post("rent_submit", {
            classId: Id,
            week: week,
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

</script>
<%
    User user = (User) session.getAttribute("currentUser");
    String userName = user.getUserName();
%>

<div class="modal fade" id="Modal1" tabindex="-1" role="dialog" aria-labelledby="Modal1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="Modal1Label1">申请</h4>
            </div>
            <div class="modal-body" >
                <div class="form-group">
                    <label for="week">星期</label>
                    <select id="week" name="week" class="form-control">
                        <option value="1">星期一</option>
                        <option value="2" }>星期二</option>
                        <option value="3" }>星期三</option>
                        <option value="4" }>星期四</option>
                        <option value="5" }>星期五</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <select id="startTime" name="startTime" class="form-control">
                        <option value="1">第一小节</option>
                        <option value="2" }>第二小节</option>
                        <option value="3" }>第三小节</option>
                        <option value="4">第四小节</option>
                        <option value="5" }>第五小节</option>
                        <option value="6" }>第六小节</option>
                        <option value="7">第七小节</option>
                        <option value="8" }>第八小节</option>
                        <option value="9" }>第九小节</option>
                        <option value="10">第十小节</option>
                        <option value="11" }>第十一小节</option>
                        <option value="12" }>第十二小节</option>
                        <option value="13" }>第十三小节</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <select id="endTime" name="endTime" class="form-control">
                        <option value="1">第一小节</option>
                        <option value="2" }>第二小节</option>
                        <option value="3" }>第三小节</option>
                        <option value="4">第四小节</option>
                        <option value="5" }>第五小节</option>
                        <option value="6" }>第六小节</option>
                        <option value="7">第七小节</option>
                        <option value="8" }>第八小节</option>
                        <option value="9" }>第九小节</option>
                        <option value="10">第十小节</option>
                        <option value="11" }>第十一小节</option>
                        <option value="12" }>第十二小节</option>
                        <option value="13" }>第十三小节</option>
                    </select>
                </div>
                <label for="reason">申请理由(请详细填写,以便管理员审核)</label>
                <textarea class="form-control" rows="3" id="reason" name="reason" style="resize:none"></textarea>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"  onclick="rentForweek('<%=userName%>')">申请</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<div class="data_list">
    <div class="data_content">
      <div align=center> <label  style="font-size: 40px;color: #ff874b">${error}</label></div>
    <table class="table table-bordered table-hover table-striped" style="background-color: #ffffff">    <c:forEach var="timetable" items="${timetableList}" varStatus="status">
            <tr>
                <th>日期</th>
                <th>一</th>
                <th>二</th>
                <th>三</th>
                <th>四</th>
                <th>五</th>
                <th>六</th>
                <th>七</th>
            </tr>

                <tr >
                    <td>${timetable.week}</td>
                    <td>${timetable.first}</td>
                    <td>${timetable.second}</td>
                    <td>${timetable.third}</td>
                    <td>${timetable.fourth}</td>
                    <td>${timetable.fifth}</td>
                    <td>${timetable.sixth}</td>
                    <td>${timetable.seventh}</td>
                </tr><th></th>
                <th>八</th>
                <th>九</th>
                <th>十</th>
                <th>十一</th>
                <th>十二</th>
                <th>十三</th>
                <tr>
                    <td></td>
                    <td>${timetable.eighth}</td>
                    <td>${timetable.ninth}</td>
                    <td>${timetable.tenth}</td>
                    <td>${timetable.eleventh}</td>
                    <td>${timetable.twelfth}</td>
                    <td>${timetable.thirteenth}</td>

                </tr>
            </c:forEach>
        </table>
        <input id="classId" name="classId"  hidden="hidden" value="${classId}"/>
      <div align="center">
          <input  type="button" class="btn btn-large" value="申请" onclick="dialogB()"/>
          <input  type="button" class="btn btn-large" value="返回" onclick="javascript:history.back()"/></div>
    </div>

</div>
