<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
      <div align="center">
          <input  type="button" class="btn btn-large" value="返回" onclick="javascript:history.back()"/></div>
    </div>

</div>
