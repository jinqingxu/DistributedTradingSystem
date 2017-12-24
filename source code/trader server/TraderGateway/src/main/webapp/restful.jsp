<%--
  Created by IntelliJ IDEA.
  User: jinqingxu
  Date: 2017/5/29
  Time: 下午2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>
</head>
<body>
<p><label>id</label><input  type="text" id="traderid" value=""/>
    <button value="查询" onclick="bookquery()"></button></p>
<label>名字</label><input type="text" id="username" value=""/>
<label>密码</label><input type="text" id="password" value=""/>
</body>
</html>
<script>
    function bookquery() {
        var traderid = document.getElementById("traderid").value;
        var url = "http://localhost:8081/restful/trader?traderid=" + traderid;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'jsonp',//here
            jsonp: "callback",
            jsonpCallback: "success_jsonpCallback",
            success: function (data) {
                var result = data;
                console.log(result);
                document.getElementById("username").setAttribute("value",result.username);
                document.getElementById("password").setAttribute("value",result.password);
            }
        });
    }
</script>