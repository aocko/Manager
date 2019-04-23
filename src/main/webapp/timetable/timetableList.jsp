<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="data_list">
    <div class="search_content">
        <label align=center style="font-size: 40px;color: #ff874b">${error}</label>
        <button class="btn btn-mini btn-primary" type="button" style="float: right;margin-bottom: 5px;"
                onclick="javascript:window.location='timetable_preSave?aClassId=${aClassId}'">添加课程表
        </button>
    </div>
    <div class="data_content">
        <table class="table table-bordered table-hover table-striped" style="background-color: #ffffff">
            <tr>
                <th>日期</th>
                <th>第一节课</th>
                <th>第二节课</th>
                <th>第三节课</th>
                <th>第四节课</th>
                <th>第五节课</th>
                <th>第六节课</th>
                <th>第七节课</th>

            </tr>
            <c:forEach var="timetable" items="${timetableList}" varStatus="status">
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
                <th>第八节课</th>
                <th>第九节课</th>
                <th>第十节课</th>
                <th>第十一节课</th>
                <th>第十二节课</th>
                <th>第十三节课</th>
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
      <div align="center">  <button class="btn btn-large btn-info" type="button"
                onclick="javascript:window.location='timetable_preSave?classId=${aClassId}'">修改
    </button>
          <input  type="button" class="btn btn-large" value="返回" onclick="javascript:history.back()"/></div>
    </div>

</div>
