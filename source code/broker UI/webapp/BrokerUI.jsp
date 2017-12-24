<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2017/6/2
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>BrokerUI</title>
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
                <h1>Broker</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="BrokerTransaction.jsp" style="font-size:20px">Transaction</a></li>
                </ul>
            </div>
        </div>
        <div class="container">
            <div class="collapse navbar-collapse" >
                <ul class="nav navbar-nav navbar-right">
                    <script>
                        var Types=new Array();
                        var products=new Array();
                        function getProducts(){
                            Types.push("Metal","Energy","Chemistry");
                            var p=["Iron","Gold","Bronze"];
                            products.push(p);
                            p=["Water","Oil"];
                            products.push(p);
                            p=["Rubber"];
                            products.push(p);
                        }
                        function showProducts(){
                            getProducts();
                            for (var i=0;i<Types.length;i++){
                                document.writeln("<li class='dropdown'><a href='#'style='font-size:20px;'>"+Types[i]+"<i class='fa fa-angle-down'></i></a>");
                                document.writeln("<ul role='menu' class='sub-menu'>");
                                for (var j=0;j<products[i].length;j++){
                                    document.writeln("<li><a href='#tab"+products[i][j]+"' data-toggle='tab' style='font-size:15px'>"+products[i][j]+"</a></li>");
                                }
                                document.writeln("</ul></li>");
                            }
                        }
                        showProducts();
                    </script>
                </ul>
            </div>
        </div>
    </div>
</header>
<!--/#header-->
<!--/#home-slider-->
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



<script>
    var num=5;
    var sellPrice=new Array();
    var sellNum=new Array();
    var sellUnit;
    function getSellingPrice(){
        for (var ii=0;ii<num;ii++){
            sellPrice.push(50+ii);
            sellNum.push(100+ii);
        }
        sellUnit="kg";
    }
    var buyPrice=new Array();
    var buyNum=new Array();
    var buyUnit;
    function getBuyingPrice(){
        for (var ii=0;ii<num;ii++){
            buyPrice.push(40+ii);
            buyNum.push(200+ii);
        }
        buyUnit="kg";
    }
</script>
<section id="bars" style="">
    <div class="container">
        <div class="tab-content">
            <script>
                var swaps=new Array();
                function showSmallProduct() {
                    for (var i = 0;i<Types.length;i++) {
                        swaps[i]=new Array();
                        for (var j=0;j<products[i].length;j++){
                            var plist=new Array();
                            plist=[products[i][j]+"1706",products[i][j]+"1709",products[i][j]+"1803"];
                            swaps[i][j]=plist;
                            document.writeln("<div class='tab-pane fade' id='tab"+products[i][j]+"'>");
                            document.writeln("<div class='col-md-12'>");
                            document.writeln("<ul id='tab0"+products[i][j]+"' class='nav nav-pills'>");
                            for (var k=0;k<plist.length;k++){
                                document.writeln("<li><a href='#tab"+plist[k]+"' data-toggle='tab' style='font-size:18px;'>"+plist[k]+"</a></li>");
                            }
                            document.writeln("</ul>");
                            document.writeln("<div class='tab-content'>");
                            for (var k=0;k<plist.length;k++){
                                document.writeln("<div class='tab-pane fade' id='tab"+plist[k]+"'>");


                                document.writeln("<section id='page-breadcrumb'><div class='vertical-center sun'><div class='container'>");
                                document.writeln("<div class='row'><div class='action'><div class='col-sm-12'>");
                                document.writeln("<h1 class='title'>"+plist[k]+"</h1>");
                                var currentPrice="45";
                                document.writeln("<p>Current Price : $"+currentPrice+"</p>");
                                document.writeln("</div></div></div></div></div></section>");

                                document.writeln("<div class='price-table'><div class='row'><div class='col-sm-6 col-md-3'><div class='single-price price-one'>");
                                getSellingPrice();
                                document.writeln("<div class='table-heading' style='margin-top:-80px;'><p class='plan-name'>Selling</p>");
                                //document.writeln("<p class='plan-price'><span class='dollar-sign'>$</span><span class='price'>"+sellPrice[0]+"</span><span class='month'>/ "+sellUnit+"</span></p>");
                                document.writeln("</div><ul>");
                                for (var index=0;index<num;index++){
                                    document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+plist[k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+plist[k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                                    //console.log(plist[k]+'sellprice'+index);
                                    //var tmp=document.getElementById(plist[k]+'sellnum'+index).innerText;
                                    //console.log(tmp);
                                }
                                document.writeln("</ul>");
                                //document.writeln("<a href='#' class='btn btn-buynow' data-toggle='modal' data-target='#makeOrder'>Buy Now</a>");
                                document.writeln("</div></div>");
                                document.writeln("<div class='col-sm-6 col-md-3'><div class='single-price price-two'>");
                                getBuyingPrice();
                                document.writeln("<div class='table-heading' style='margin-top:-80px;'><p class='plan-name'>Buying</p>");
                                //document.writeln("<p class='plan-price'><span class='dollar-sign'>$</span><span class='price'>"+buyPrice[0]+"</span><span class='month'>/ "+sellUnit+"</span></p>");
                                document.writeln("</div><ul>");
                                for (var index=0;index<num;index++){
                                    document.writeln("<li>Buy "+index+"<spanm style='margin-left:70px;font-size:15px;'>$"+buyPrice[index]+"</spanm><span>"+buyNum[index]+"</span></li>");
                                }
                                document.writeln("</ul>");
                                //document.writeln("<a href='#' class='btn btn-buynow btn-hightlight' data-toggle='modal' data-target='#makeOrder'>Sell Now</a>");
                                document.writeln("</div></div></div></div>");


                                document.writeln("</div>");
                            }
                            document.writeln("</div>");
                            document.writeln("</div>");
                            document.writeln("</div>");
                        }
                    }
                }
                showSmallProduct();
            </script>

        </div>
    </div>
</section>


<br><br><br><br><br>
<section>
    <div class="col-sm-12 text-center bottom-separator">
        <img src="images/home/footer2.png" class="img-responsive inline" alt="">
    </div>
</section>
</body>
</html>
