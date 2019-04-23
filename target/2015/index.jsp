<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String mainPage = (String) request.getAttribute("mainPage");
    if (mainPage == null || mainPage.equals("")) {
        mainPage = "default.jsp";
    }
%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link href="${pageContext.request.contextPath}/style/studentInfo.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">

        function dialog(){
            $("#mymodal").modal();
        };

        function dialogA() {
            $('#myModal1').modal();
        }



        function changePasswordAAA() {
            var oldpassword = document.getElementById('oldPassword').value;
            var newpassword = document.getElementById('newPassword').value;
            $.post("changePassword",{oldPassword:oldpassword,newPassword:newpassword},function (r) {
                var result=eval('('+r+')');
                if (result.error) {
                    alert(result.error);
                } else {
                    alert("修改成功");
                    window.location.href = "${pageContext.request.contextPath}/index.jsp";
                }

            });

        }

    </script>
</head>
<body style="background: url('bg2.jpg')">
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
                <button type="button" class="btn btn-default"  onclick="changePasswordAAA()">修改</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div id="XS" class="container" style="max-width: 1920px">

    <%@include file="123/head.html" %>
    <%@include file="123/menu.html" %>
        <%--<jsp:include page="123/head.html"></jsp:include>--%>
        <%--<jsp:include page="123/menu.html"></jsp:include>--%>
    <jsp:include page="<%=mainPage %>"></jsp:include>


    <%@include file="123/foot.html" %>

</div>
</body>
</html>
<script type="text/javascript"> if('${error}'!=''){
    dialog();
}

</script>