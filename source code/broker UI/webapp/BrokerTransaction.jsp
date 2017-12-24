<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2017/6/2
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>BrokerTransaction</title>
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
    <!--link href="css/bootstrap.min.css"  rel="stylesheet" type="text/css"/-->
</head>
<body>
<header id="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-header" style="margin-top: -50px">
                <h1>Transaction</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="BrokerUI.jsp" style="font-size:20px">Order Book</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>

<section id="action" class="responsive" style="margin-top: -50px">
    <div class="vertical-center">
        <div class="container">
            <div class="row">
                <div class="action take-tour">
                    <div class="col-sm-7 wow fadeInLeft" data-wow-duration="500ms" data-wow-delay="300ms">
                        <h1 class="title">Welcome to Broker0!</h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<div class="container">
    <h2>Transaction</h2>
    <table id="transactionTable" data-toggle="table" data-height="246" data-pagination="true">
        <thead>
        <tr>
            <th data-field="id" data-align="center">Id</th>
            <th data-field="name" data-align="center">Product</th>
            <th data-field="code" data-align="center">Code</th>
            <th data-field="price" data-align="center">Price</th>
            <th data-field="amount" data-align="center">Amount</th>
            <th data-field="time" data-align="center">Time</th>
            <th data-field="broker" data-align="center">Broker</th>
            <th data-field="buyerid" data-align="center">Buyer</th>
            <th data-field="sellerid" data-align="center">Seller</th>
        </tr>
        </thead>
    </table>
</div>


<script>
    var tabledata=[
        {
            "id":"001","name": "Iron1706","code":"123","price":40,"amount":1000,"time":"2017-6-2","broker":1,"buyerid":"555","sellerid":"666"
        },
    ]
    $('#transactionTable').bootstrapTable({
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
