<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2017/6/2
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>User</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/lightbox.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">

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

    <link rel="stylesheet" href="css/bootstrap-table.css">
    <link rel="stylesheet" href="css/docs.css">
    <script src="js/examples.js"></script>
    <script src="js/bootstrap-table.js"></script>
    <script src="js/docs.js"></script>

    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
</head>

<%
    Object traderid=request.getParameter("traderid");
%>
<script>
    var traderid=<%=traderid%>;
    console.log(traderid);
</script>


<script>
    function getTry(){
        var url = "http://localhost:8082/restful/order/getallorder/"+traderid;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                $('#orderTable').bootstrapTable("load",result);

            }
        });
    }
    getTry();

    function getTransaction(){
        var url = "http://localhost:8082/restful/transaction/"+traderid;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                $('#transactionTable').bootstrapTable("load",result);

            }
        });
    }
    getTransaction();
</script>




<body>
<header id="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-header">
                <h1>User</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <script>
                        document.writeln("<li class='active'><a href='TraderUI.jsp?refresh=0&traderid="+traderid+"' style='font-size:20px'>Home</a></li>");
                    </script>
                </ul>
            </div>
        </div>
    </div>
</header>

<section id="action" class="responsive">
    <div class="vertical-center">
        <div class="container">
            <div class="row">
                <div class="action take-tour">
                    <div class="col-sm-7 wow fadeInLeft" data-wow-duration="500ms" data-wow-delay="300ms">
                        <h1 class="title">Hi, User!</h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<div class="container">
    <h2>Orders</h2>
    <table id="orderTable" data-toggle="table" data-height="246" data-pagination="true">
        <thead>
        <tr>
            <th data-field="id" data-align="center">Id</th>
            <th data-field="code" data-align="center">Code</th>
            <th data-field="price" data-align="center">Price</th>
            <th data-field="amount" data-align="center">Amount</th>
            <th data-field="left_number" data-align="center">Left</th>
            <th data-field="time" data-align="center">Time</th>
            <th data-field="broker" data-align="center">Broker</th>
            <th data-field="possession" data-align="center">Possession</th>
            <th data-field="status" data-align="center">Status</th>
            <th data-field="type" data-align="center">Type</th>
        </tr>
        </thead>
    </table>
</div>

<div class="container">
    <h2>Transactions</h2>
    <table id="transactionTable" data-toggle="table" data-height="246" data-pagination="true">
        <thead>
        <tr>
            <th data-field="id" data-align="center">Id</th>
            <th data-field="code" data-align="center">Code</th>
            <th data-field="price" data-align="center">Price</th>
            <th data-field="amount" data-align="center">Amount</th>
            <th data-field="time" data-align="center">Time</th>
            <th data-field="possession" data-align="center">Possession</th>
            <th data-field="firm" data-align="center">Firm</th>
        </tr>
        </thead>
    </table>
</div>


<script>
    var tabledata=[
        {
            "id":"001","code":"123","price":40,"amount":1000,"time":"2017-6-2","broker":1,"buyerid":"555","sellerid":"666"
        },
    ]
    $('#orderTable').bootstrapTable({
        data:tabledata
    });
</script>

<br><br><br><br><br><br><br><br><br><br>
<section>
    <div class="col-sm-12 text-center bottom-separator">
        <img src="images/home/footer2.png" class="img-responsive inline" alt="">
    </div>
</section>
</body>
</html>
