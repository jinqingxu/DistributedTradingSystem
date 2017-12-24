<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2017/6/4
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Jump</title>
    <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>

</head>
<%
    Object brokerid=request.getParameter("brokerid");
%>
<script>
    var brokerid=<%=brokerid%>;
    console.log(brokerid);
</script>

<script>
    function getTry(){
        var url = "http://localhost:8080/restful/product/getproducts";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                var formObj=document.getElementById("passForm");
                formObj.brokerid.value=brokerid;
                var str=JSON.stringify(result);
                console.log(str);
                formObj.tepjson.value=str;
                formObj.submit();
            }
        });
    }
    getTry();
</script>

<form method="post" action="ShowBroker.jsp" id="passForm">
    <input id="brokerid" type="hidden" name="brokerid">
    <input id="tepjson" type="hidden" name="tepjson">
</form>

<body>

</body>
</html>
