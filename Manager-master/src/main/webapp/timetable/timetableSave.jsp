<%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/3/15
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    th {
        width: 65px;
    }

    tr {
        width: 65px;
    }

    input {
        width: 65px;
    }
</style>
<div class="data_list">
    <div class="data_content">
        <form style="margin-top: 25px" action="timetable_save" method="post">
            <table align="center" style="table-layout: fixed">
                <tr>
                    <th>日期</th>
                    <th>第一节课</th>
                    <th>第二节课</th>
                    <th>第三节课</th>
                    <th>第四节课</th>
                    <th>第五节课</th>
                    <th>第六节课</th>
                    <th>第七节课</th>
                    <th>第八节课</th>
                    <th>第九节课</th>
                    <th>第十节课</th>
                    <th>第十一节课</th>
                    <th>第十二节课</th>
                    <th>第十三节课</th>
                </tr>
                <tr>
                    <td><input type="text" id="week" name="timetableList[0].week" value="星期一"
                                 ></td>
                    <td><input type="text" id="first" name="timetableList[0].first" value="${timetableList[0].first}"
                               ></td>
                    <td><input type="text" id="second" name="timetableList[0].second" value="${timetableList[0].second}"
                               ></td>
                    <td><input type="text" id="third" name="timetableList[0].third" value="${timetableList[0].third}"
                               ></td>
                    <td><input type="text" id="fourth" name="timetableList[0].fourth" value="${timetableList[0].fourth}"
                               ></td>
                    <td><input type="text" id="fifth" name="timetableList[0].fifth" value="${timetableList[0].fifth}"
                               ></td>
                    <td><input type="text" id="sixth" name="timetableList[0].sixth" value="${timetableList[0].sixth}"
                               ></td>
                    <td><input type="text" id="seventh" name="timetableList[0].seventh"
                               value="${timetableList[0].seventh}" ></td>
                    <td><input type="text" id="eighth" name="timetableList[0].eighth" value="${timetableList[0].eighth}"
                               ></td>
                    <td><input type="text" id="ninth" name="timetableList[0].ninth" value="${timetableList[0].ninth}"
                               ></td>
                    <td><input type="text" id="tenth" name="timetableList[0].tenth" value="${timetableList[0].tenth}"
                               ></td>
                    <td><input type="text" id="eleventh" name="timetableList[0].eleventh"
                               value="${timetableList[0].eleventh}" ></td>
                    <td><input type="text" id="twelfth" name="timetableList[0].twelfth"
                               value="${timetableList[0].twelfth}" ></td>
                    <td><input type="text" id="thirteenth" name="timetableList[0].thirteenth"
                               value="${timetableList[0].thirteenth}" ></td>
                </tr>
                <tr>
                    <td><input type="text" id="week1" name="timetableList[1].week" value="星期二"
                                ></td>
                    <td><input type="text" id="first1" name="timetableList[1].first" value="${timetableList[1].first}"
                               ></td>
                    <td><input type="text" id="second1" name="timetableList[1].second"
                               value="${timetableList[1].second}" ></td>
                    <td><input type="text" id="third1" name="timetableList[1].third" value="${timetableList[1].third}"
                               ></td>
                    <td><input type="text" id="fourth1" name="timetableList[1].fourth"
                               value="${timetableList[1].fourth}" ></td>
                    <td><input type="text" id="fifth1" name="timetableList[1].fifth" value="${timetableList[1].fifth}"
                               ></td>
                    <td><input type="text" id="sixth1" name="timetableList[1].sixth" value="${timetableList[1].sixth}"
                               ></td>
                    <td><input type="text" id="seventh1" name="timetableList[1].seventh"
                               value="${timetableList[1].seventh}" ></td>
                    <td><input type="text" id="eighth1" name="timetableList[1].eighth"
                               value="${timetableList[1].eighth}" ></td>
                    <td><input type="text" id="ninth1" name="timetableList[1].ninth" value="${timetableList[1].ninth}"
                               ></td>
                    <td><input type="text" id="tenth1" name="timetableList[1].tenth" value="${timetableList[1].tenth}"
                               ></td>
                    <td><input type="text" id="eleventh1" name="timetableList[1].eleventh"
                               value="${timetableList[1].eleventh}" ></td>
                    <td><input type="text" id="twelfth1" name="timetableList[1].twelfth"
                               value="${timetableList[1].twelfth}" ></td>
                    <td><input type="text" id="thirteenth1" name="timetableList[1].thirteenth"
                               value="${timetableList[1].thirteenth}" ></td>
                </tr>
                <tr>
                    <td><input type="text" id="week2" name="timetableList[2].week" value="星期三"
                               ></td>
                    <td><input type="text" id="first2" name="timetableList[2].first" value="${timetableList[2].first}"
                               ></td>
                    <td><input type="text" id="second2" name="timetableList[2].second"
                               value="${timetableList[2].second}" ></td>
                    <td><input type="text" id="third2" name="timetableList[2].third" value="${timetableList[2].third}"
                               ></td>
                    <td><input type="text" id="fourth2" name="timetableList[2].fourth"
                               value="${timetableList[2].fourth}" ></td>
                    <td><input type="text" id="fifth2" name="timetableList[2].fifth" value="${timetableList[2].fifth}"
                               ></td>
                    <td><input type="text" id="sixth2" name="timetableList[2].sixth" value="${timetableList[2].sixth}"
                               ></td>
                    <td><input type="text" id="seventh2" name="timetableList[2].seventh"
                               value="${timetableList[2].seventh}" ></td>
                    <td><input type="text" id="eighth2" name="timetableList[2].eighth"
                               value="${timetableList[2].eighth}" ></td>
                    <td><input type="text" id="ninth2" name="timetableList[2].ninth" value="${timetableList[2].ninth}"
                               ></td>
                    <td><input type="text" id="tenth2" name="timetableList[2].tenth" value="${timetableList[2].tenth}"
                               ></td>
                    <td><input type="text" id="eleventh2" name="timetableList[2].eleventh"
                               value="${timetableList[2].eleventh}" ></td>
                    <td><input type="text" id="twelfth2" name="timetableList[2].twelfth"
                               value="${timetableList[2].twelfth}" ></td>
                    <td><input type="text" id="thirteenth2" name="timetableList[2].thirteenth"
                               value="${timetableList[2].thirteenth}" ></td>
                </tr>
                <tr>
                    <td><input type="text" id="week3" name="timetableList[3].week" value="星期四"
                                 ></td>
                    <td><input type="text" id="first3" name="timetableList[3].first" value="${timetableList[3].first}"
                               ></td>
                    <td><input type="text" id="second3" name="timetableList[3].second"
                               value="${timetableList[3].second}" ></td>
                    <td><input type="text" id="third3" name="timetableList[3].third" value="${timetableList[3].third}"
                               ></td>
                    <td><input type="text" id="fourth3" name="timetableList[3].fourth"
                               value="${timetableList[3].fourth}" ></td>
                    <td><input type="text" id="fifth3" name="timetableList[3].fifth" value="${timetableList[3].fifth}"
                               ></td>
                    <td><input type="text" id="sixth3" name="timetableList[3].sixth" value="${timetableList[3].sixth}"
                               ></td>
                    <td><input type="text" id="seventh3" name="timetableList[3].seventh"
                               value="${timetableList[3].seventh}" ></td>
                    <td><input type="text" id="eighth3" name="timetableList[3].eighth"
                               value="${timetableList[3].eighth}" ></td>
                    <td><input type="text" id="ninth3" name="timetableList[3].ninth" value="${timetableList[3].ninth}"
                               ></td>
                    <td><input type="text" id="tenth3" name="timetableList[3].tenth" value="${timetableList[3].tenth}"
                               ></td>
                    <td><input type="text" id="eleventh3" name="timetableList[3].eleventh"
                               value="${timetableList[3].eleventh}" ></td>
                    <td><input type="text" id="twelfth3" name="timetableList[3].twelfth"
                               value="${timetableList[3].twelfth}" ></td>
                    <td><input type="text" id="thirteenth3" name="timetableList[3].thirteenth"
                               value="${timetableList[3].thirteenth}" ></td>
                </tr>
                <tr>
                    <td><input type="text" id="week4" name="timetableList[4].week" value="星期五"
                                 ></td>
                    <td><input type="text" id="first4" name="timetableList[4].first" value="${timetableList[4].first}"
                               ></td>
                    <td><input type="text" id="second4" name="timetableList[4].second"
                               value="${timetableList[4].second}" ></td>
                    <td><input type="text" id="third4" name="timetableList[4].third" value="${timetableList[4].third}"
                               ></td>
                    <td><input type="text" id="fourth4" name="timetableList[4].fourth"
                               value="${timetableList[4].fourth}" ></td>
                    <td><input type="text" id="fifth4" name="timetableList[4].fifth" value="${timetableList[4].fifth}"
                               ></td>
                    <td><input type="text" id="sixth4" name="timetableList[4].sixth" value="${timetableList[4].sixth}"
                               ></td>
                    <td><input type="text" id="seventh4" name="timetableList[4].seventh"
                               value="${timetableList[4].seventh}" ></td>
                    <td><input type="text" id="eighth4" name="timetableList[4].eighth"
                               value="${timetableList[4].eighth}" ></td>
                    <td><input type="text" id="ninth4" name="timetableList[4].ninth" value="${timetableList[4].ninth}"
                               ></td>
                    <td><input type="text" id="tenth4" name="timetableList[4].tenth" value="${timetableList[4].tenth}"
                               ></td>
                    <td><input type="text" id="eleventh4" name="timetableList[4].eleventh"
                               value="${timetableList[4].eleventh}" ></td>
                    <td><input type="text" id="twelfth4" name="timetableList[4].twelfth"
                               value="${timetableList[4].twelfth}" ></td>
                    <td><input type="text" id="thirteenth4" name="timetableList[4].thirteenth"
                               value="${timetableList[4].thirteenth}" ></td>
                </tr>
                <tr>
                    <td><input type="hidden" id="classId" name="classId" value="${aClassId}">
                        <input style="position: relative; left: 100px" type="submit" class="btn btn-primary" value="保存">
                    </td>
                    <td><input style="position: relative; left: 100px" type="button" class="btn btn-primary" value="返回"
                               onclick="javascript:history.back()"/>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>