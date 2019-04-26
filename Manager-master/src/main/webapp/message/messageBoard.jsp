<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/4/10
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<script>
    var m_id;

    function openAnswerModal(id) {
        m_id = id;
        $('#answerModal').modal("show");
    }

    function answer() {
        var answer = document.getElementById("answer").value;
        $.post("answer", {answer: answer, messageId: m_id}, function (r) {
            var result = eval('(' + r + ')');
            if (result.error) {
                alert(result);
            } else {
                window.location.href = "${pageContext.request.contextPath}/messageList";
            }


        });
    }


</script>

<div class="modal fade" id="answerModal" tabindex="-1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                    class="sr-only">关闭</span></button>
            <h4 class="modal-title">回复</h4>
        </div>
        <div class="modal-body" id="dialogs1">
            <label for="answer">回复</label>
            <textarea name="answer" id="answer" style="resize:none;"></textarea></div>
        <div class="modal-footer">
            <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e73013; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;"
                    onclick="answer()">回复
            </button>
            <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e1dfe7; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;">
                否
            </button>
        </div>
    </div>


</div>
<table class="table table-hover table-bordered table-condensed table-striped" style="background-color: #ffffff;">
    <tr>
        <th>序号</th>
        <th>标题</th>
        <th>内容</th>
        <th>管理员回复</th>
        <th>操作</th>
    </tr>
    <c:forEach var="message" items="${messageList}" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${message.title}</td>
            <td>${message.content}</td>
            <td>${message.answer}</td>
            <td>
                <button class="btn btn-mini btn-info" type="button"
                        onclick="openAnswerModal(${message.id})">回复
                </button>
            </td>
        </tr>
    </c:forEach>
</table>