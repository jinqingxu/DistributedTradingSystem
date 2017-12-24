<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2017/5/22
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>TraderUI</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/lightbox.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">

    <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery.fitvids.js"></script>
    <script type="text/javascript" src="js/holder.js"></script>
    <script type="text/javascript" src="js/lightbox.min.js"></script>
    <script type="text/javascript" src="js/wow.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
    <script type="text/javascript" src="js/jquery.smart-form.js"></script>
    <script type="text/javascript" src="js/global.js"></script>
</head><!--/head-->

<%
    Object traderid=request.getParameter("traderid");
    Object tepjson=request.getParameter("tepjson");
    Object refresh=request.getParameter("refresh");
%>
<script>
    var traderid=<%=traderid%>;
    var refresh=<%=refresh%>;
    console.log(traderid);
</script>

<script>
    function getTry(){
        var url = "http://localhost:8082/restful/broker/getbrokers";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                var formObj=document.getElementById("passForm");
                formObj.traderid.value=traderid;
                var str=JSON.stringify(result);
                console.log(str);
                formObj.tepjson.value=str;
                formObj.submit();

            }
        });
    }
    if (refresh==0){
        getTry();
    }
    else{
        var tepjson=<%=tepjson%>;
        console.log(tepjson);

        var brokers=tepjson.names;
        var brokerid=tepjson.ids;
        console.log(brokers);
        console.log(brokerid);
    }
</script>

<form method="post" action="TraderUI.jsp?refresh=1" id="passForm">
    <input id="traderid" type="hidden" name="traderid">
    <input id="tepjson" type="hidden" name="tepjson">
</form>

<body>
<header id="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-header">
                <h1>Trader</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <script>
                        document.writeln("<li class='active'><a href='ShowUser.jsp?traderid="+traderid+"' style='font-size:20px'>User</a></li>");
                    </script>

                </ul>
            </div>
        </div>
    </div>
</header>
<!--/#header-->
<!--/#home-slider-->
<section id="action" class="responsive">
    <div class="vertical-center">
        <div class="container">
            <div class="row">
                <div class="action take-tour">
                    <div class="col-sm-7 wow fadeInLeft" data-wow-duration="500ms" data-wow-delay="300ms">
                        <h1 class="title">Welcome to Trader!</h1>
                        <p>Please select your broker.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>



<section id="services">
    <div class="container">
        <div class="row">
            <script>
                for (var i=0;i<brokers.length;i++){
                    document.writeln("<div class='col-sm-4 text-center padding wow fadeIn' data-wow-duration='1000ms' data-wow-delay='"+300*(i+1)+"ms'>");
                    document.writeln("<div class='single-service'>");
                    document.writeln("<div class='wow scaleIn' data-wow-duration='500ms' data-wow-delay='"+300*(i+1)+"ms'>");
                    document.writeln("<img src='images/pics/Metal.jpg' alt=''>");
                    document.writeln("</div>");
                    document.writeln("<h2><a href='ShowBroker.jsp?refresh=0&brokerid="+brokerid[i]+"&traderid="+traderid+"'>"+brokers[i]+"</a></h2>");
                    document.writeln("</div></div>");
                }
            </script>
        </div>
    </div>
</section>


<br><br><br><br><br><br><br><br><br><br>
<section>
    <div class="col-sm-12 text-center bottom-separator">
        <img src="images/home/footer2.png" class="img-responsive inline" alt="">
    </div>
</section>
<!--/#footer-->


</body>
</html>
