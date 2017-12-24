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

<%
    Object brokerid=request.getParameter("brokerid");
%>
<script>
    var brokerid=<%=brokerid%>;
    console.log(brokerid);
</script>

<body onload="load()">
<header id="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-header" style="margin-top: -50px">
                <h1>Broker</h1>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="ShowUser.jsp" style="font-size:20px">User</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="TraderUI.jsp" style="font-size:20px">Home</a></li>
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
                                document.writeln("<a href='#' class='btn btn-buynow' data-toggle='modal' data-target='#makeOrder'>Buy Now</a>");
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
                                document.writeln("<a href='#' class='btn btn-buynow btn-hightlight' data-toggle='modal' data-target='#makeOrder'>Sell Now</a>");
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
        <div class="modal-content" style="width:900px">
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
                                    <th data-field="name" data-align="left">Product</th>
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
                broker: brokerid,
                possession: buysell,
                product: swapid,
                ordertype: orderType,
                price: orderprice,
                number: orderamount
            };
            var jsonData = JSON.stringify(mydata);

            var url = "http://localhost:8081/restful/trader/makeorder";
            $.ajax({
                url: url,
                type: 'POST',
                data: jsonData,
                contentType: "application/json",
                dataType: 'json',//here
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
            var mydata = {
                cancelid: orderid
            };
            var jsonData = JSON.stringify(mydata);
            var url = "http://localhost:8081/restful/trader/cancelorder";
            $.ajax({
                url: url,
                type: 'POST',
                data: jsonData,
                contentType: "application/json",
                dataType: 'json',//here
                success: function () {
                    window.alert("success");
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
    var tabledata=[
        {
            "id":"001","name": "Iron1706","code":"123","price":40,"amount":1000,"time":"2017-6-2","broker":1,"possession":"buy","type":"Market Order"
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