<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>高校教室管理系统</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>

    <script type="text/javascript">
        $.ajax({
            url: "gradeList",
            type: "post",
            success: function (result) {
                var result = eval('(' + result + ')');
                var gradeName = document.getElementById("gradeName");
                var majorName = document.getElementById("majorName");
                for (var i = 0; i < result.gradeList.length; i++) {
                    gradeName.innerHTML += "<option  value='" + result.gradeList[i].gradeName + "'>" + result.gradeList[i].gradeName + "</option>";
                }
                ;
                for (var j = 0; j < result.majorList.length; j++) {
                    majorName.innerHTML += "<option id='" + result.majorList[j].majorId + "' value='" + result.majorList[j].majorName + "'>" + result.majorList[j].majorName + "</option>";
                }
                ;
            }
        });

        function loadimage() {
            document.getElementById("randImage").src = "image.jsp?" + Math.random();
        }

        function dialog() {
            $("#mymodal").modal();
        }

        function dialog2() {
            $("#register").modal();
        }

        function checkName() {
            var userName = $("#r_userName").val();
            var tishi = document.getElementById("tishi");
            $.ajax({
                url: "checkName",
                data: {"r_userName": userName},
                type: "get",
                success: function (result) {
                    var result = eval('(' + result + ')');
                    tishi.innerHTML = "";
                    if (userName != null && userName.trim() != '') {

                        if (result.error) {
                            tishi.innerHTML = "<font color='red'>抱歉，用户名已被注册，请更换！</font>";
                        } else if (result.success) {
                            tishi.innerHTML = "<font color='green'>恭喜，用户名可以注册！</font>"
                        }
                    }
                }
            });
        }

        function checkPassword() {
            var password = $("#r_password").val();
            var password2 = $("#r_password2").val();
            var tishi2 = document.getElementById("tishi2");
            tishi2.innerHTML = "";
            if (password.trim() != '' || password2.trim() != '') {
                if (password != password2) {
                    tishi2.innerHTML = "<font color='red'>两次密码不一致，请重新输入</font>";
                } else if (password == password2) {
                    tishi2.innerHTML = "<font color='green'>密码相同，可以注册</font>"
                }
            }
        }

        function usertype() {
            var select = document.getElementById('userType');
            var userType = select.options[select.selectedIndex].value;
            if (userType == "老师") {
                document.getElementById('stuNamelabel').innerText = "教师姓名";
                document.getElementById('stuNolabel').innerText = "教工号";
                document.getElementById('gradeNameDom').setAttribute('style', 'display:none');
                document.getElementById('majorNameDom').setAttribute('style', 'display:none');
            }
            if (userType == "学生") {
                document.getElementById('gradeNameDom').setAttribute('style', '');
                document.getElementById('majorNameDom').setAttribute('style', '');
            }
        }

        function keyLogin(){
            if (event.keyCode==13){
                document.getElementById("adminlogin").submit();
            }
        }
        function queryGrade() {
            var select=document.getElementById('majorName');
            var m_Name = select.options[select.selectedIndex].getAttribute("id");
            $.ajax({
                url: "gradeList",
                data : {"majorId":m_Name},
                type: "post",
                success: function (result) {
                    var result = eval('(' + result + ')');
                    var gradeName = document.getElementById("gradeName");
                    gradeName.innerHTML = "";
                    for (var i = 0; i < result.gradeList.length; i++) {
                        gradeName.innerHTML += "<option  value='" + result.gradeList[i].gradeName + "'>" + result.gradeList[i].gradeName + "</option>";
                    }
                }
            });

        }
    </script>
</head>
<body style="background: url('bg.jpg') no-repeat top center fixed;background-size: cover;" onkeydown="keyLogin();">

<div class="modal fade" id="mymodal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">您好</h4>
            </div>
            <div class="modal-body" id="dialogs">
                <p>${error}</p>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" style="color: #FFFFFF;background-color:#e75f49; text-align:center;
            	  padding:10px;border: 1px solid #dedede;-moz-border-radius: 5px;-webkit-border-radius: 5px;  border-radius:5px;vertical-align:middle;">
                    我知道了
                </button>
            </div>
        </div>
    </div>
</div>

<form class="form-horizontal" id="Userregister" name="Userregister" action="register" method="post" aria-hidden="true">
    <div class="modal hide fade" id="register" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title">请输入注册信息</h4>
                </div>
                <div class="modal-body" id="dialogs1">

                    <div class="control-group">
                        <label class="control-label" for="r_userName">用户名</label>
                        <div class="controls">
                            <input style="margin-top: 6px;height: 30px" type="text" id="r_userName" placeholder="用户名"
                                   name="r_userName"
                                   required="required" oninput="checkName()">
                            <div id="tishi"></div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="r_password">密码</label>
                        <div class="controls">
                            <input style="margin-top: 6px;height: 30px" type="password" id="r_password"
                                   name="r_password"
                                   placeholder="密码" required="required" oninput="checkPassword()">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="r_password2">确认密码</label>
                        <div class="controls">
                            <input style="margin-top: 6px;height: 30px" type="password" id="r_password2"
                                   name="r_password2"
                                   placeholder="确认密码" required="required" oninput="checkPassword()">
                            <div id="tishi2"></div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="userType">选择用户类别</label>
                        <select id="userType" name="userType" required="required"
                                style="height: 30px;width: 206px;margin-left: 20px" onchange="usertype()">
                            <option value="学生">学生</option>
                            <option value="老师">老师</option>
                        </select>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="stuName" id="stuNamelabel">学生姓名</label>
                        <div class="controls">
                            <input style="margin-top: 6px;height: 30px" type="text" id="stuName" name="stuName"
                                   placeholder="姓名" required="required">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="stuNo" id="stuNolabel">学号</label>
                        <div class="controls">
                            <input style="margin-top: 6px;height: 30px" type="number" id="stuNo" name="stuNo"
                                   placeholder="学号/教工号" required="required">
                        </div>
                    </div>
                    <div class="control-group" id="majorNameDom">
                        <label class="control-label" for="majorName">所在专业</label>
                        <div class="controls">
                            <select id="majorName" name="majorName" required="required" style="height: 30px;width: 206px" onchange="queryGrade()">
                            </select>
                        </div>
                    </div>
                    <div class="control-group" id="gradeNameDom">
                        <label class="control-label" for="gradeName">所在年级</label>
                        <div class="controls">
                            <select id="gradeName"  name="gradeName" required="required" style="height: 30px;width: 206px">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input value="注册" class="btn btn btn-primary "
                           type="submit">
                    <button type="button" data-dismiss="modal" class="btn btn btn-primary ">
                        关闭
                    </button>
                </div>
            </div>
        </div>
    </div>
</form>

<div class="container" style="margin-top: 100px">

    <div class="row">
        <div class="span4 offset4">
            <form id=adminlogin method=post
                  name=adminlogin action="user">
                <div style="border-radius: 10px 10px 10px 10px;font-size: 13px;
                    background-color: #ffffff;
                    padding: 20px;">
                    <div align="center"><h1>高校教室管理系统
                    </h1></div>
                    <div class="form-group">
                        <label>用户名</label>
                        <input type="text" id="username" name="user.userName" placeholder="用户名"
                               style="width: 100%;height: 30px">
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="password" id="password" name="user.password" style="width: 100%;height: 30px"
                               placeholder="密码">
                    </div>
                    <div class="form-group">
                        <label>验证码</label>
                        <input type="text" id="imageCode" name="imageCode" style="height: 30px"
                               placeholder="验证码">
                        <img    style="margin-bottom: 10px"
                                onclick="javascript:loadimage();"
                                title="换一张试试" name="randImage"
                                id="randImage" src="image.jsp" width="90"
                                height="30" border="1"
                        >
                    </div>
                    <div align="center"><input id="btnLogin" name="btnLogin" value="登录"  style="margin-right: 30px"
                                               class="btn  btn-primary " id="login"
                                               type="button" onclick="javascript:adminlogin.submit();return false;">
                        <input class="btn btn btn-primary "
                               data-toggle="modal" type="button"
                               data-target="#register" value="注册">
                    </div>
                    <div>
                        <div class="row"></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <p>&nbsp;</p>
    <div style="text-align:center;margin:0 auto;">
        <h6 style="color:#fff;"></h6>
    </div>
</div>
</div>
</body>
</html>
<script type="text/javascript">

    if ('${error}' != '') {
        dialog();
    }</script>
