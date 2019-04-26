<%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/4/9
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<table class="table table-hover table-bordered table-condensed table-striped" style="background-color: #ffffff;">

    <thead>
    <tr style="font-size: 14px">
        <th>序号</th>
        <th>教室名称</th>
        <th>教室类别</th>
        <th>申请时间</th>
        <th>开始时间</th>
        <th>到期时间</th>
        <th>申请理由</th>
        <th>教室状态</th>
        <th>审核结果</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="rent" items="${rentList}" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${rent.className}</td>
            <td>${rent.classType}</td>
            <td>${rent.rentTime}</td>
            <td>${rent.startTime}</td>
            <td>${rent.endTime}</td>
            <td>${rent.reason}</td>
            <td>${rent.classStatus}</td>
            <td>${rent.rentStatus}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>