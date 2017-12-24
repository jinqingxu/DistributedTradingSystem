<%--<html>--%>
<%--<body>--%>
<%--<h2>Hello World!</h2>--%>
<%--</body>--%>
<%--</html>--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <base href="<%=basePath%>">

    <title>Login</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/lightbox.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/lightbox.min.js"></script>
    <script type="text/javascript" src="js/wow.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>

</head>

<body>
<section id="action" class="responsive">
    <div class="vertical-center">
        <div class="container">
            <div class="row">
                <div class="action take-tour">
                    <div class="col-sm-12 wow fadeInLeft" data-wow-duration="500ms" data-wow-delay="300ms">
                        <h1 class="title" style="text-align: center">Login</h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="container">



        <div class="col-md-3 text-center" style="margin-top: 50px;margin-left: 430px;">

            <div class="form-group">
                <input type="text" name="name" id="name" class="form-control" required="required" placeholder="Username">
            </div>
            <div class="form-group">
                <input type="password" name="password" id="password" class="form-control" required="required" placeholder="Password">
            </div>


                <button class="btn btn-submit" onclick="checklogin()">SUBMIT</button>


        </div>
</section>

<script>
    function checklogin(){
        var username=document.getElementById("name").value;
        var password=document.getElementById("password").value;
        console.log(username);
        console.log(password);
        var url = "http://localhost:8082/restful/trader/login?username="+username+"&password="+password;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                var ans=result.result;
                console.log(ans);
                if (ans=="fail"){
                    alert("Login Fail");
                }
                else{
                    window.location.href="TraderUI.jsp?refresh=0&traderid="+result.id;
                }
            }
        });

    }
</script>

</body>
</html>