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
    <link rel="stylesheet" href="css/style.css">
    <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <base href="<%=basePath%>">

    <title>Login</title>

</head>

<body>
<section class="container">

    <div class="login">
        <h1>用户登录</h1>
        <form method="post" action="DealOrderAction">
            <p><input type="text" name="user.username" value="" placeholder="用户名"></p>
            <p><input type="password" name="user.password" value="" placeholder="密码"></p>

            <p class="submit"><input type="submit" name="commit" value="登录"></p>

        </form>
    </div>
    <div class="register">
        <p class="submit"><a href="register.jsp"><input  value="注册" type="submit"  ></a></p>
    </div>
</section>

</body>
</html>