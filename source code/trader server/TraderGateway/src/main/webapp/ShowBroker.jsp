<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Trader</title>
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
    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
    <link href="css/linechart.css" rel="stylesheet">



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
    Object brokerid=request.getParameter("brokerid");
    Object traderid=request.getParameter("traderid");
    Object tepjson=request.getParameter("tepjson");
    Object refresh=request.getParameter("refresh");
    String path = request.getContextPath();
    String socPath="ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
    var brokerid=<%=brokerid%>;
    var traderid=<%=traderid%>;
    var refresh=<%=refresh%>;
    console.log(brokerid);
    var firmname="trader";
</script>

<script>
    var brokerip="";
    function getBrokerIp(){
        url = "http://localhost:8082/restful/broker/getbrokerip?brokerid="+brokerid;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                brokerip=result.ip;
                console.log("ip");
                console.log(result);
                console.log(brokerip);
            }
        });
    }
    getBrokerIp();
</script>

<script>
    function getTry(){
        var url = "http://localhost:8082/restful/product/getproducts";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                var formObj=document.getElementById("passForm");
                formObj.brokerid.value=brokerid;
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

        var Types=tepjson.types;
        var products=tepjson.products;
        var swaps=tepjson.swaps;
        console.log(Types);
        console.log(products);
        console.log(swaps);
    }
</script>

<form method="post" action="ShowBroker.jsp?refresh=1" id="passForm">
    <input id="brokerid" type="hidden" name="brokerid">
    <input id="traderid" type="hidden" name="traderid">
    <input id="tepjson" type="hidden" name="tepjson">
</form>





<body onload="load()">
<header id="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-header" style="margin-top: -50px">
                <h1>Trader</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <script>
                        document.writeln("<li class='active'><a href='ShowUser.jsp?traderid="+traderid+"' style='font-size:20px'>User</a></li>");
                    </script>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <script>
                        document.writeln("<li class='active'><a href='TraderUI.jsp?refresh=0&traderid="+traderid+"' style='font-size:20px'>Home</a></li>");
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
                            var wsuri="ws://localhost:8082/websocketbot";
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
                                console.log("msg");
                                var bb=msg.buybook;
                                var b=msg.brokerid;
                                var pcode=msg.productCode;
                                //var price=msg.price;
                                var buybook=eval(bb);
                                console.log("size!!!");
                                console.log(buybook.length);
                                if(buybook.length<num)num=buybook.length;
                                if(brokerid==b){
                                    console.log("msgprice");
                                    console.log(msg.price);
                                    oldPrice = document.getElementById(pcode+"current").innerText;
                                    document.getElementById(pcode+"current").innerText=msg.price;
                                    document.getElementById(productcode+'fudong').innerText=""+(msg.price-oldPrice)/msg.Price;
                                    redraw("12:0",msg.price);
                                    for (var index=0;index<num;index++){
                                        //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                                        //console.log(swaps[i][j][k]+'sellprice'+index);
                                        console.log("buybook!!!!!");
                                        var tmp=document.getElementById(pcode+'buynum'+index).innerText;
                                        console.log(tmp);
                                        tmp=document.getElementById(pcode+'buyprice'+index).innerText;
                                        console.log(tmp);
                                        console.log("number!!!");
                                        console.log(buybook[index].number);
                                        document.getElementById(pcode+'buynum'+index).innerText=buybook[index].number;
                                        document.getElementById(pcode+'buyprice'+index).innerText="$ "+buybook[index].price;
                                        if (buybook[index].price>=currentPrice){
                                            document.getElementById(pcode+'buyprice'+index).style.color="red";
                                        }
                                        else{
                                            document.getElementById(pcode+'buyprice'+index).style.color="green";
                                        }
                                        console.log("price!!!");
                                        console.log(price);
                                        var fudong=(price-currentPrice)/currentPrice;
                                        currentPrice=price;
                                        document.getElementById(pcode+'current').innerText="Current Price : $"+price;
                                        if (fudong>=0){
                                            document.getElementById(pcode+'fudong').innerText="+"+fudong*100+"%";
                                            document.getElementById(pcode+'fodongDiv').style.color="red";
                                        }
                                        else{
                                            document.getElementById(pcode+'fudong').innerText=fudong*100+"%";
                                            document.getElementById(pcode+'fodongDiv').style.color="green";
                                        }




                                    }
                                }

                            }
                            else if(msg.type=="sellbook"){
                                console.log(msg);
                                var ss=msg.sellbook;
                                var b=msg.brokerid;
                                var pcode=msg.productCode;
                                var sellbook=eval(ss);

                                console.log("size!!!");
                                console.log(sellbook.length);
                                if(sellbook.length<num)num=sellbook.length;
                                if(brokerid==b){
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
                                        if (sellbook[index].price>=currentPrice){
                                            document.getElementById(pcode+'sellprice'+index).style.color="red";
                                        }
                                        else{
                                            document.getElementById(pcode+'sellprice'+index).style.color="green";
                                        }
                                    }
                                }
                            }
                        }
                        //end of websocket

                        function showProducts(){
                            for (var i=0;i<Types.length;i++){
                                document.writeln("<li class='dropdown'><a href='#'style='font-size:20px;'onclick='cleartable()'>"+Types[i]+"<i class='fa fa-angle-down'></i></a>");
                                document.writeln("<ul role='menu' class='sub-menu'>");
                                for (var j=0;j<products[i].length;j++){
                                    document.writeln("<li><a href='#tab"+products[i][j]+"' data-toggle='tab' style='font-size:15px'onclick='cleartable()'>"+products[i][j]+"</a></li>");
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
    var currentPrice="45200";

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

                                document.writeln("<spanm style='font-size:30px;margin-left:700px;margin-top:-100px' id='"+swaps[i][j][k]+"current'>Current Price : $"+currentPrice+" </spanm>");
                                document.writeln("<div id='"+swaps[i][j][k]+"fudongDiv' style='color:red'><spanm style='font-size:20px;margin-left:950px;margin-top:-100px' id='"+swaps[i][j][k]+"fudong'>+0% </spanm></div>");

                                document.writeln("<div class='single-price price-two' style='width:300px;margin-left:700px;margin-top:50px'>");
                                document.writeln("<a href='#' class='btn btn-buynow' data-toggle='modal' data-target='#makeOrder'>Make Order</a>");
                                document.writeln("</div>");

                                document.writeln("<div class='single-price price-three' style='width:300px;margin-left:700px;margin-top:50px'>");
                                document.writeln("<a class='btn btn-buynow' onclick='showTransactions()''>Transactions</a>");
                                document.writeln("</div>");

                                document.writeln("<div class='col-sm-6 col-md-3'><div class='single-price price-four'>");
                                getSellingPrice();
                                document.writeln("<div class='table-heading' style='margin-top:-250px;'><p class='plan-name'>Selling</p>");
                                //document.writeln("<p class='plan-price'><span class='dollar-sign'>$</span><span class='price'>"+sellPrice[0]+"</span><span class='month'>/ "+sellUnit+"</span></p>");
                                document.writeln("</div><ul>");
                                for (var index=0;index<num;index++){
                                    document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;color:#FF0000' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                                    //console.log(swaps[i][j][k]+'sellprice'+index);
                                    //var tmp=document.getElementById(swaps[i][j][k]+'sellnum'+index).innerText;
                                    //console.log(tmp);
                                }
                                document.writeln("</ul>");
                                document.writeln("</div></div>");
                                document.writeln("<div class='col-sm-6 col-md-3'><div class='single-price price-four'>");
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


<div class="modal fade" id="makeOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Make Order</h4>
            </div>
            <div class="modal-body">

                <div id="tab-container" class="">
                    <ul id="tabold" class="nav nav-pills">
                        <li class="active"><a href="#market-tab-1" data-toggle="tab" onclick="copyorder(this)">Market Order</a></li>
                        <li><a href="#market-tab-1" data-toggle="tab" onclick="copyorder(this)">Limit Order</a></li>
                        <li><a href="#market-tab-1" data-toggle="tab" onclick="copyorder(this)">Stop Order</a> </li>
                        <li><a href="#CancelOrder" data-toggle="tab" onclick="copyorder(this)">Cancel Order</a> </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="market-tab-1">
                            <div>
                                <select class="panel-body" id="selectOrder">
                                    <option value="">Buy</option>
                                    <option value="">Sell</option>
                                </select>
                                <select class='panel-body' id='selectType' onchange='changeType()'>
                                    <option value=''>Type</option>
                                </select>
                                <select class='panel-body' id='selectProduct' onchange="changeProduct()">
                                    <option value=''>Product</option>
                                </select>
                                <select class='panel-body' id='selectSwap'>
                                    <option value=''>Swap</option>
                                </select>
                            </div>
                            <p>Price:</p>
                            <input id="orderPrice" type="text" name="Price" class="form-control col-md-3" required="required" placeholder="Price">
                            <p>Amount:</p>
                            <input id="orderAmount" type="text" name="Amount" class="form-control" required="required" placeholder="Number">
                        </div>

                        <div class="tab-pane fade" id="CancelOrder">
                            <table id="orderTable" singleSelect="true" data-toggle="table" data-height="246" data-pagination="true">
                                <thead>
                                <tr>
                                    <th data-field="state" data-radio="true"></th>
                                    <th data-field="id" data-align="left">Id</th>
                                    <th data-field="code" data-align="left">Code</th>
                                    <th data-field="price" data-align="left">Price</th>
                                    <th data-field="amount" data-align="left">Amount</th>
                                    <th data-field="time" data-align="left">Time</th>
                                    <th data-field="broker" data-align="left">Broker</th>
                                    <th data-field="possession" data-align="left">Possession</th>
                                    <th data-field="type" data-align="left">Type</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div><!--/#table-container-->

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="makeorder()">OK</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script>
    var orderType="Market Order";
    function copyorder(e){
        console.log(e.innerText);
        orderType=e.innerText;
    }
    function makeorder(){
        if (orderType!="Cancel Order") {
            var buysell = selectOrder.selectedIndex; //0:buy,1:sell
            var swapid = swaps[selectType.selectedIndex - 1][selectProduct.selectedIndex - 1][selectSwap.selectedIndex - 1]; //n
            var orderprice = document.getElementById("orderPrice").value;
            var orderamount = document.getElementById("orderAmount").value;
            console.log(buysell);
            console.log(swapid);
            console.log(orderType);
            console.log(orderprice);
            console.log(orderamount);
            var mydata = {
                traderId:1,
                broker: brokerid,
                possession: buysell,
                product: swapid,
                ordertype: orderType,
                price: orderprice*1.0,
                number: orderamount*1
            };
            //var jsonData = JSON.stringify(mydata);

            var url = "http://localhost:8082/restful/trader/makeorder";
            $.ajax({
                url: url,
                type: 'POST',
                data: mydata,
                contentType: "application/x-www-form-urlencoded",
                //dataType: 'json',//here
                success: function () {
                    window.alert("success");
                    $('#makeOrder').modal("hide");
                }
            });
            //console.log(orderType+"success");
        }
        else {
            var row = $.map($("#orderTable").bootstrapTable('getSelections'), function (row) {
                return row;
            });
            var orderid = row[row.length - 1].id;
            console.log(orderid);
            var mydata = {
                cancelid: orderid
            };
            //var jsonData = JSON.stringify(mydata);
            var url = "http://localhost:8082/restful/trader/cancelorder";
            $.ajax({
                url: url,
                type: 'POST',
                data: mydata,
                contentType: "application/x-www-form-urlencoded",
                //dataType: 'json',//here
                success: function () {
                    window.alert("Cancel Order Success");
                    $('#makeOrder').modal("hide");
                }
            });
            //console.log(orderType+orderid+"success");
        }

    }
</script>

<script>
    var selectType=document.getElementById("selectType");
    var selectProduct=document.getElementById("selectProduct");
    var selectSwap=document.getElementById("selectSwap");
    var selectOrder=document.getElementById("selectOrder");

    var opt_type=new Array(new Option("Select Type",""));
    var opt_product=new Array();
    opt_product[0]=new Array(new Option("Select Product",""));
    var opt_swap=new Array();
    opt_swap[0]=new Array(new Option("Swap",""));
    for (var i=0;i<Types.length;i++){
        opt_type.push(new Option(Types[i],Types[i]));
        var tep=new Array(new Option("Select Product",""));
        opt_swap[i+1]=new Array();
        opt_swap[i+1][0]=new Array(new Option("Select Swap"));
        for (var j=0;j<products[i].length;j++){
            tep.push(new Option(products[i][j],products[i][j]));
            opt_swap[i+1][j+1]=new Array(new Option("Select Swap",""),new Option(products[i][j]+"1706",""),new Option(products[i][j]+"1709",""),new Option(products[i][j]+"1803",""));
        }
        opt_product[i+1]=tep;
    }

    function load(){
        for(var j=0;j<opt_type.length;j++){
            selectType.options[j]=opt_type[j];
        }
    }
    //选中省份之后，根据索引动态载入相应城市
    function changeType(){
        //清空上次的选项
        selectProduct.options.length=0;
        selectSwap.options.length=1;
        selectSwap.options[0]=opt_swap[0][0];
        //获取省一级的下拉列表选中的索引
        var index=selectType.selectedIndex;
        for(var i=0;i<opt_product[index].length;i++){
            selectProduct.options[i]=opt_product[index][i];
        }
    }
    function changeProduct(){
        //清空上次的选项
        selectSwap.options.length=0;
        //获取省一级的下拉列表选中的索引
        var index1=selectType.selectedIndex;
        var index2=selectProduct.selectedIndex;
        for(var i=0;i<opt_swap[index1][index2].length;i++){
            selectSwap.options[i]=opt_swap[index1][index2][i];
        }
    }

</script>


<script>
    var tabledata=new Array();
    function getOrders(){
        var url = "http://localhost:8082/restful/trader/getorders";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                tabledata=result;
                $('#orderTable').bootstrapTable("load",tabledata);
                console.log(tabledata);
            }
        });
    }
    getOrders();

    /*var tabledata=[
     {
     "id":"001","name": "Iron1706","code":"123","price":40,"amount":1000,"time":"2017-6-2","broker":1,"possession":"buy","type":"Market Order"
     },
     {
     "id":"002","name": "Iron1706","code":"123","price":40,"amount":1000,"time":"2017-6-2","broker":1,"possession":"buy","type":"Market Order"
     },
     ]
     */
    console.log(tabledata);
    $('#orderTable').bootstrapTable({
        data:tabledata
    });

</script>


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

    var productcode="";
    function copyProduct(e){
        cleartable();
        startdraw();
        var productname=e.innerText;
        productcode=productname;
        console.log(productcode);
        console.log("brokerid!!!");
        console.log(brokerid);
        //orderbookRest
        var url = "http://localhost:8082/restful/orderbook/getorderbook?code="+productcode+"&brokerid="+brokerid+"&type=sell";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                result=eval(data);
                console.log("restsize!!!");
                console.log(result.length);
                if(result.length<num)num=result.length;
                for (var index=0;index<num;index++){
                    //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                    //console.log(swaps[i][j][k]+'sellprice'+index);
                    console.log("sellbook!!!");
                    console.log(productcode+'sellprice'+index);
                    var tmp=document.getElementById(productcode+'sellprice'+index).innerText;
                    console.log(tmp);
                    tmp=document.getElementById(productcode+'sellnum'+index).innerText;
                    console.log(tmp);
                    document.getElementById(productcode+'sellnum'+index).innerText=result[index].number;
                    document.getElementById(productcode+'sellprice'+index).innerText="$ "+result[index].price;
                    if (result[index].price>=currentPrice){
                        document.getElementById(productcode+'sellprice'+index).style.color="red";
                    }
                    else{
                        document.getElementById(productcode+'sellprice'+index).style.color="green";
                    }
                }
            }
        });
        url = "http://localhost:8082/restful/orderbook/getorderbook?code="+productcode+"&brokerid="+brokerid+"&type=buy";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                result=eval(data);
                console.log("restsize!!!");
                console.log(result.length);
                if(result.length<num)num=result.length;
                for (var index=0;index<num;index++){
                    //document.writeln("<li>Sell "+index+"<spanm style='margin-left:70px;font-size:15px;' id='"+swaps[i][j][k]+"sellprice"+index+"'>$"+sellPrice[index]+"</spanm><span id='"+swaps[i][j][k]+"sellnum"+index+"'>"+sellNum[index]+"</span></li>");
                    //console.log(swaps[i][j][k]+'sellprice'+index);
                    document.getElementById(productcode+'buynum'+index).innerText=result[index].number;
                    document.getElementById(productcode+'buyprice'+index).innerText="$ "+result[index].price;
                    if (result[index].price>=currentPrice){
                        document.getElementById(productcode+'buyprice'+index).style.color="red";
                    }
                    else{
                        document.getElementById(productcode+'buyprice'+index).style.color="green";
                    }
                }
            }
        });

    }
</script>

<script>
    var transactiondata=new Array();

    $('#transactionTable').bootstrapTable({
        data:transactiondata,
    })
    $('#transactionTableDiv').css("display","none");

    function getTransactions(){
        url = "http://"+brokerip+":8081/restful/transaction/gettransactions?product="+productcode+"&firmName="+firmname;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log("transactions");
                console.log(result);
                transactiondata=result;
                $('#transactionTable').bootstrapTable("load",result);
            }
        });
    }

    function showTransactions(){
        $('#transactionTableDiv').css("display","block");
        getTransactions();
    }
    function cleartable(){
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
</script>






<script>
    var w=1000;
    var h=300;
    var iii = 0;
    var dates=["09:23","09:24","09:47","10:43","11:42"];
    var prices=[45200,45220,45200,45180,45180,45200];

    function redraw(d,p){
        iii=iii+1;
        dates.push(d+iii);
        prices.push(p);
        drawChart();
    }

    function re(){
        redraw("12:0",45320);

    }
</script>

<div class="container" id="chartDiv">

    <script src="js/linechart.js"type="text/javascript"></script>
    <p align="left">
        <!--button id="xxx" onClick="re()">Refresh</button-->
    </p>
</div>

<script>
    function getCurrentPrice(){
        var url = "http://localhost:8082/restful/orderbook/price?code="+productcode+"&brokerid="+brokerid;
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                var result = data;
                console.log(result);
                currentPrice=result;
                document.getElementById(productcode+'current').innerText=result;
                document.getElementById(productcode+'fudong').innerText="+0%";
            }
        });
    }
    getCurrentPrice();
</script>


<br><br><br><br><br><br><br><br><br><br>
<section>
    <div class="col-sm-12 text-center bottom-separator">
        <img src="images/home/footer2.png" class="img-responsive inline" alt="">
    </div>
</section>
</body>
</html>