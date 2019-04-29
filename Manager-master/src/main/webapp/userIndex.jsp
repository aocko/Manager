<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Yjy
  Date: 2019/3/28
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    String mainPage = (String) request.getAttribute("mainPage");
    if (mainPage == null || mainPage.equals("")) {
        mainPage = "user/index.jsp";
    }
    User user = (User) session.getAttribute("currentUser");
    String userName = user.getUserName();
%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <title>高校教室管理系统</title>
    <link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="bootstrap-3.3.7-dist/js/jquery-3.3.1.min.js"></script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">

        function dialogA() {
            $('#myModal1').modal();
        }
        function openError() {
            $('#mymodal').modal('show');
        }



        function changePassword() {
            var oldpassword = document.getElementById('oldPassword').value;
            var newpassword = document.getElementById('newPassword').value;
            $.post("changePassword",{oldPassword:oldpassword,newPassword:newpassword},function (r) {
                var result=eval('('+r+')');
                if (result.error) {
                    alert(result.error);
                } else {
                    alert("修改成功");
                    window.location.href = "${pageContext.request.contextPath}/userIndex";
                }

            });

        }

    </script>
</head>
<body style="background: url('dynamic-style.png')">
<script type="text/javascript">
    function setDateTime(){
        var date=new Date();
        var day=date.getDay();
        var week;
        switch(day){
            case 0:week="星期日";break;
            case 1:week="星期一";break;
            case 2:week="星期二";break;
            case 3:week="星期三";break;
            case 4:week="星期四";break;
            case 5:week="星期五";break;
            case 6:week="星期六";break;
        }
        var today=date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日  "+week+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        document.getElementById("today").innerHTML=today;
    }

    window.setInterval("setDateTime()", 1000);

</script>
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel1">修改密码</h4>
            </div>
            <div class="modal-body" >
                <div class="form-group">
                    <label for="oldPassword">当前密码</label>
                    <input type="text" class="form-control" id="oldPassword" placeholder="当前密码">
                </div>
                <div class="form-group">
                    <label for="newPassword">新密码</label>
                    <input type="password" class="form-control" id="newPassword" placeholder="新密码">
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"  onclick="changePassword()">修改</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="mymodal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">您好</h4>
            </div>
            <div class="modal-body" id="dialogs">
                <p id="error" name="error">${error}</p>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn btn-default">
                    我知道了
                </button>
            </div>
        </div>
    </div>
</div>
<div class="container" >
    <div class="label label-success" id="today" style="font-size:14px;float: right;margin-right: 5px"></div>

    <div class="row well" style="background:white;min-height: 650px" >

        <nav class="navbar navbar-default " style="font-size: 20px">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#collapse" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="userIndex.jsp">首页</a>
                </div>
                <div class="collapse navbar-collapse" id="collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="rent_list">租用教室</a></li>
                        <li><a href="Myrent?userName=<%=userName%>">我的申请</a></li>
                        <li><a href="messageList?userName=<%=userName%>">留言</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-haspopup="true" aria-expanded="false">个人信息管理 <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="changeDetail" >修改资料</a></li>
                                <li><a href="#" onclick="dialogA()">修改密码</a></li>
                                <li><a href="user?status=exit" >退出登录</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <div >
        <jsp:include page="<%=mainPage %>"></jsp:include>
    </div>
    </div>
</div>
<script>

</script>
</body>
</html>
<script type="text/javascript">

    if('${error}'!=''){
    openError();
}

</script>
