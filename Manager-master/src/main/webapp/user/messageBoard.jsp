<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/4/10
  Time: 13:35
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
<table class="table table-hover table-bordered table-condensed table-striped" style="background-color: #ffffff;">
    <tr style=" font-size: 14px">
        <th>序号</th>
        <th>标题</th>
        <th>内容</th>
        <th>管理员回复</th>
    </tr>
    <c:forEach var="message" items="${messageList}" varStatus="status">
        <tr style=" font-size: 14px">
            <td>${status.index+1}</td>
            <td>${message.title}</td>
            <td>${message.content}</td>
            <td>${message.answer}</td>
        </tr>
    </c:forEach>
</table>
<form action="messageSubmit">
    <div class="row">
        <div class="col-md-3 col-md-offset-4">
            <div class="form-group">
                <label for="title">标题</label>
                <input type="text" class="form-control" id="title" name="message.title" placeholder="标题最多20字"
                       style="height:30px;width: 300px">
            </div>
            <div class="form-group">
                <label for="content">内容</label>
                <textarea type="text" rows="3" class="form-control" id="content" name="message.content" placeholder="内容最多100字"
                          style="height:90px;width: 300px;resize:none"></textarea>
            </div>
            <input type="hidden" name="userName" id="userName" value="<%=userName%>">
            <button type="submit" class="btn btn-default">提交</button>
        </div>
    </div>
</form>