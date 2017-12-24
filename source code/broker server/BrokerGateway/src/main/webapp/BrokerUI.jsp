<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Broker</title>
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
</head><!--/head-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    Object tepjson=request.getParameter("tepjson");
    Object refresh=request.getParameter("refresh");
    String path = request.getContextPath();
    String socPath="ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
    var refresh=<%=refresh%>;
</script>


<script>
    function getTry(){
        var url = "http://localhost:8081/restful/product/getproducts";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                var formObj=document.getElementById("passForm");
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

        var Types=tepjson.types;
        var products=tepjson.products;
        var swaps=tepjson.swaps;
        console.log(Types);
        console.log(products);
        console.log(swaps);
    }
</script>

<form method="post" action="BrokerUI.jsp?refresh=1" id="passForm">
    <input id="tepjson" type="hidden" name="tepjson">
</form>





<body>
<header id="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-header" style="margin-top: -50px">
                <h1>Broker Products</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <script>
                        document.writeln("<li class='active'><a href='ShowTraders.jsp?refresh=0' style='font-size:20px'>AllTraders</a></li>");
                    </script>
                </ul>
            </div>
        </div>
        <div class="container">
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-right">
                <script>
                    var wsocket;    // Websocket connection
                    var userName;   // User's name
                    var textarea;   // Chat area
                    var wsconsole;  // Websocket console area
                    var userlist;   // User list area
                    /* Connect to the Websocket endpoint
                     * Set a callback for incoming messages */
                    function connect() {
                        window.WebSocket = window.WebSocket || window.MozWebSocket;
                        if (!window.WebSocket) {    // 检测浏览器支持
                            console.log('Error: WebSocket is not supported .');
                            return;
                        }
                        //var wsuri = "<%=socPath%>websocketbot";
                        var wsuri="ws://localhost:8081/websocketbot";
                        console.log(wsuri);
                        if ('WebSocket' in window)
                            wsocket = new WebSocket(wsuri);
                        else if ('MozWebSocket' in window)
                            wsocket = new MozWebSocket(wsuri);
                        else
                            console.error("not support WebSocket!");
                        wsocket.onmessage = onMessage;
                        console.log("connected");
                    }
                    /* Call connect() when the page first loads */
                    window.addEventListener("load", connect, false);

                    function onMessage(evt) {
                        var line = "";
                        /* Parse the message into a JavaScript object */
                        var msg = JSON.parse(evt.data);
                        if (msg.type == "buybook") {
                            var bb=msg.buybook;
                            var pcode=msg.productCode;
                            var price=msg.price;
                            var buybook=eval(bb);
                            console.log("size!!!");
                            console.log(buybook.length);
                            if(buybook.length<num){
                                for(var index=num-buybook.length;index>0;index--){
                                    document.getElementById(productcode+'buynum'+index).innerText="";
                                    document.getElementById(productcode+'buyprice'+index).innerText="";
                                }
                            }
                            if(buybook.length<num)num=buybook.length;

                                for (var index=0;index<num;index++){
                                    //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                                    //console.log(swaps[i][j][k]+'sellprice'+index);
                                    console.log("websokcet!!!!!");
                                    var tmp=document.getElementById(pcode+'buynum'+index).innerText;
                                    console.log(tmp);
                                    tmp=document.getElementById(pcode+'buyprice'+index).innerText;
                                    console.log(tmp);
                                    console.log("number!!!");
                                    console.log(buybook[index].number);
                                    document.getElementById(pcode+'buynum'+index).innerText=buybook[index].number;
                                    document.getElementById(pcode+'buyprice'+index).innerText="$ "+buybook[index].price;
                                    console.log("price!!!");
                                    console.log(price);
                                    document.getElementById(pcode+'current').innerText="Current Price : $"+price;



                                }



                    }
                    else if(msg.type=="sellbook"){
                            var ss=msg.sellbook;
                            var pcode=msg.productCode;
                            var sellbook=eval(ss);

                            console.log("size!!!");
                            console.log(sellbook.length);
                            if(sellbook.length<num){
                                for(var index=num-buybook.length;index>0;index--){
                                    document.getElementById(productcode+'sellnum'+index).innerText="";
                                    document.getElementById(productcode+'sellprice'+index).innerText="";
                                }
                            }
                            if(sellbook.length<num)num=sellbook.length;

                                for (var index=0;index<num;index++){
                                    //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                                    //console.log(swaps[i][j][k]+'sellprice'+index);
                                    console.log("sellbook!!!");
                                    console.log(pcode+'sellprice'+index);
                                    var tmp=document.getElementById(pcode+'sellprice'+index).innerText;
                                    console.log(tmp);
                                    tmp=document.getElementById(pcode+'sellnum'+index).innerText;
                                    console.log(tmp);
                                    document.getElementById(pcode+'sellnum'+index).innerText=sellbook[index].number;
                                    document.getElementById(pcode+'sellprice'+index).innerText="$ "+sellbook[index].price;

                                }


                        }
                    }
                    //end of websocket

                    function showProducts(){
                        for (var i=0;i<Types.length;i++){
                            document.writeln("<li class='dropdown'><a href='#'style='font-size:20px;' onclick='cleartable()'>"+Types[i]+"<i class='fa fa-angle-down'></i></a>");
                            document.writeln("<ul role='menu' class='sub-menu'>");
                            for (var j=0;j<products[i].length;j++){
                                document.writeln("<li><a href='#tab"+products[i][j]+"' data-toggle='tab' style='font-size:15px' onclick='cleartable()'>"+products[i][j]+"</a></li>");
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

<section id="bars" style="margin-top: -80px;">
    <div class="container">
        <div class="tab-content">
            <script>
                function showSmallProduct() {
                    for (var i = 0;i<Types.length;i++) {
                        for (var j=0;j<products[i].length;j++){
                            document.writeln("<div class='tab-pane fade' id='tab"+products[i][j]+"'>");
                            document.writeln("<div class='col-md-12'>");
                            document.writeln("<ul id='tab0"+products[i][j]+"' class='nav nav-pills'>");
                            for (var k=0;k<swaps[i][j].length;k++){
                                document.writeln("<li><a onclick='copyProduct(this)' href='#tab"+swaps[i][j][k]+"' data-toggle='tab' style='font-size:18px;'>"+swaps[i][j][k]+"</a></li>");
                            }
                            document.writeln("</ul>");
                            document.writeln("<div class='tab-content'>");
                            for (var k=0;k<swaps[i][j].length;k++){
                                document.writeln("<div class='tab-pane fade' id='tab"+swaps[i][j][k]+"'>");
                                document.writeln("<section id='page-breadcrumb'><div class='vertical-center sun'><div class='container'>");
                                document.writeln("<div class='row'><div class='action'><div class='col-sm-12'>");
                                document.writeln("<h1 class='title'>"+swaps[i][j][k]+"</h1>");
                                document.writeln("</div></div></div></div></div></section>");

                                document.writeln("<div class='price-table'><div class='row'>");

                                var currentPrice="45200";
                                document.writeln("<spanm style='font-size:30px;margin-left:700px;margin-top:-100px' id='"+swaps[i][j][k]+"current'>Current Price : $"+currentPrice+"</spanm>");

                                document.writeln("<div class='single-price price-four' style='width:300px;margin-left:700px;margin-top:50px'>");
                                document.writeln("<a class='btn btn-buynow' onclick='showOrders()'>Orders</a>");
                                document.writeln("</div>");

                                document.writeln("<div class='single-price price-three' style='width:300px;margin-left:700px;margin-top:50px'>");
                                document.writeln("<a class='btn btn-buynow' onclick='showTransactions()'>Transactions</a>");
                                document.writeln("</div>");

                                document.writeln("<div class='col-sm-6 col-md-3'><div class='single-price price-one'>");
                                getSellingPrice();
                                document.writeln("<div class='table-heading' style='margin-top:-250px;'><p class='plan-name'>Selling</p>");
                                //document.writeln("<p class='plan-price'><span class='dollar-sign'>$</span><span class='price'>"+sellPrice[0]+"</span><span class='month'>/ "+sellUnit+"</span></p>");
                                document.writeln("</div><ul>");
                                for (var index=0;index<num;index++){
                                    document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                                    //console.log(swaps[i][j][k]+'sellprice'+index);
                                    //var tmp=document.getElementById(swaps[i][j][k]+'sellnum'+index).innerText;
                                    //console.log(tmp);
                                }
                                document.writeln("</ul>");
                                document.writeln("</div></div>");
                                document.writeln("<div class='col-sm-6 col-md-3'><div class='single-price price-two'>");
                                getBuyingPrice();
                                document.writeln("<div class='table-heading' style='margin-top:-250px;'><p class='plan-name'>Buying</p>");
                                //document.writeln("<p class='plan-price'><span class='dollar-sign'>$</span><span class='price'>"+buyPrice[0]+"</span><span class='month'>/ "+sellUnit+"</span></p>");
                                document.writeln("</div><ul>");
                                for (var index=0;index<num;index++){
                                    document.writeln("<li>Buy "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"buyprice"+index+"'>$"+buyPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"buynum"+index+"'>"+buyNum[index]+"</span></li>");
                                }
                                document.writeln("</ul>");
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

<div class="container" id="orderTableDiv">
    <h2>Orders</h2>
    <div id="toolbar2" class="btn-group">
        <input type="text" class="form-control" id="orSearchFirm" placeholder="Firm" style="width:180px">
        <button id="trbtn_search1" type="button" class="btn btn-default" onclick="orSearchFirm()" style="margin-top: -40px;margin-left: 150px;height:40px">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
        </button>
        <input type="text" class="form-control" id="orSearchPossession" placeholder="Possession"style="width:180px">
        <button id="trbtn_search2"type="button" class="btn btn-default" onclick="orSearchPossession()" style="margin-top: -40px;margin-left: 150px;height:40px">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
        </button>
        <input type="text" class="form-control" id="orSearchType" placeholder="Type"style="width:180px">
        <button id="trbtn_search3"type="button" class="btn btn-default" onclick="orSearchType()" style="margin-top: -40px;margin-left: 150px;height:40px">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
        </button>
    </div>
    <table id="orderTable" data-toggle="table" data-height="246" data-pagination="true">
        <thead>
        <tr>
            <th data-field="id" data-align="left">Id</th>
            <th data-field="code" data-align="left">Code</th>
            <th data-field="price" data-align="left">Price</th>
            <th data-field="amount" data-align="left">Amount</th>
            <th data-field="left_number" data-align="left">Left</th>
            <th data-field="time" data-align="left" class="col-md-1">Time</th>
            <th data-field="firm" data-align="left">Firm</th>
            <th data-field="possession" data-align="left">Possession</th>
            <th data-field="status" data-align="left">Status</th>
            <th data-field="type" data-align="left">Type</th>
        </tr>
        </thead>
    </table>
</div>
<div class="container" id="transactionTableDiv">
    <h2>Transactions</h2>
    <div id="toolbar" class="btn-group">
        <input type="text" class="form-control" id="trSearchText" placeholder="Firm">
        <button id="trbtn_search" type="button" class="btn btn-default" onclick="trSearch()" style="margin-top: -40px;margin-left: 150px;height:40px">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
        </button>
    </div>

    <table id="transactionTable" data-toggle="table" data-height="246" data-pagination="true">
        <thead>
        <tr>
            <th data-field="code" data-align="left">Code</th>
            <th data-field="price" data-align="left">Price</th>
            <th data-field="num" data-align="left">Amount</th>
            <th data-field="ask" data-align="left">Ask</th>
            <th data-field="bid" data-align="left">Bid</th>
            <th data-field="time" data-align="left">Time</th>
        </tr>
        </thead>
    </table>
</div>
<script>
    var ordertabledata=new Array();
    function getOrders(){
        var url = "http://localhost:8081/restful/order/getOrders";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                ordertabledata=result;
                var tepdata=new Array();
                for (var i=0;i<ordertabledata.length;i++){
                    if (ordertabledata[i].code==productcode){
                        tepdata.push(ordertabledata[i]);
                    }
                }
                console.log(tepdata);
                $('#orderTable').bootstrapTable("load",tepdata);

            }
        });
    }

    $('#orderTable').bootstrapTable({
        data:ordertabledata,
    });

    var transactiondata=new Array();
    function getTransactions(){
        var url = "http://localhost:8081/restful/transaction/"+productcode;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                transactiondata=result;
                $('#transactionTable').bootstrapTable("load",transactiondata);
            }
        });
    }
    $('#transactionTable').bootstrapTable({
        data:transactiondata,
    })

</script>

<br><br><br><br><br><br><br><br><br><br>
<section>
    <div class="col-sm-12 text-center bottom-separator">
        <img src="images/home/footer2.png" class="img-responsive inline" alt="">
    </div>
</section>


<script>

    var productcode="";
    function copyProduct(e){
        var productname=e.innerText;
        productcode=productname;
        console.log(productcode);
        cleartable();
        //orderbookRest
        var url = "http://localhost:8081/restful/orderbook/getorderbook?code="+productcode+"&type=sell";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
               result=eval(data);

               if(result.length<num)num=result.length;
               for (var index=0;index<num;index++){
                    //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                    //console.log(swaps[i][j][k]+'sellprice'+index);

                    var tmp=document.getElementById(productcode+'sellprice'+index).innerText;

                    tmp=document.getElementById(productcode+'sellnum'+index).innerText;

                    document.getElementById(productcode+'sellnum'+index).innerText=result[index].number;
                    document.getElementById(productcode+'sellprice'+index).innerText="$ "+result[index].price;

                }
            }
        });
         url = "http://localhost:8081/restful/orderbook/getorderbook?code="+productcode+"&type=buy";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                result=eval(data);
                if(result.length<num){
                    for(var index=num-result.length;index>0;index--){
                        document.getElementById(productcode+'buynum'+index).innerText="";
                        document.getElementById(productcode+'buyprice'+index).innerText="";
                    }
                }
                if(result.length<num)num=result.length;
                for (var index=0;index<num;index++){
                    //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                    //console.log(swaps[i][j][k]+'sellprice'+index);
                    document.getElementById(productcode+'buynum'+index).innerText=result[index].number;
                    document.getElementById(productcode+'buyprice'+index).innerText="$ "+result[index].price;

                }

            }
        });

    }
</script>

<script>
    $("#orderTableDiv").css("display","none");
    $('#transactionTableDiv').css("display","none");
    function showOrders(){
        $('#transactionTableDiv').css("display","none");
        getOrders();
        $("#orderTableDiv").css("display","block");
    }
    function showTransactions(){
        $("#orderTableDiv").css("display","none");
        getTransactions();
        $('#transactionTableDiv').css("display","block");
    }
    function cleartable(){
        $("#orderTableDiv").css("display","none");
        $('#transactionTableDiv').css("display","none");
    }
    function trSearch(){
        var text=document.getElementById("trSearchText").value;
        console.log(text);
        var tepdata=new Array();
        for (var i=0;i<transactiondata.length;i++){
            if (transactiondata[i].ask==text || transactiondata[i].bid==text){
                tepdata.push(transactiondata[i]);
            }
        }
        console.log(tepdata);
        $('#transactionTable').bootstrapTable("load",tepdata);
    }

    function orSearchFirm(){
        var text=document.getElementById("orSearchFirm").value;
        var tepdata=new Array();
        for (var i=0;i<ordertabledata.length;i++){
            if (ordertabledata[i].firm==text){
                tepdata.push(ordertabledata[i]);
            }
        }
        $('#orderTable').bootstrapTable("load",tepdata);
    }
    function orSearchPossession(){
        var text=document.getElementById("orSearchPossession").value;
        var tepdata=new Array();
        for (var i=0;i<ordertabledata.length;i++){
            if (ordertabledata[i].possession==text){
                tepdata.push(ordertabledata[i]);
            }
        }
        $('#orderTable').bootstrapTable("load",tepdata);
    }
    function orSearchType(){
        var text=document.getElementById("orSearchType").value;
        var tepdata=new Array();
        for (var i=0;i<ordertabledata.length;i++){
            if (ordertabledata[i].type==text){
                tepdata.push(ordertabledata[i]);
            }
        }
        $('#orderTable').bootstrapTable("load",tepdata);
    }
</script>

</body>
</html>