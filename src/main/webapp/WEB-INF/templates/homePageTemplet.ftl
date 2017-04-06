<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" charset='utf-8'>
<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../../assets/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="../../assets/bootstrap-table/bootstrap-editable.css">
<script src="../../assets/bootstrap-table/jquery.min.js"></script>
<script src="../../assets/bootstrap-table/bootstrap.min.js"></script>
<script src="../../assets/bootstrap-table/bootstrap-table.js"></script>
<script src="../../assets/bootstrap-table/bootstrap-table-export.js"></script>
<script src="../../assets/bootstrap-table/tableExport.js"></script>
<script src="../../assets/bootstrap-table/bootstrap-table-editable.js"></script>
<script src="../../assets/bootstrap-table/bootstrap-editable.js"></script>
<script src="../../js/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript">
  var campusId=${homePages.campusId};
  var redirectUrl="${homePages.redirectUrl}";
</script>

<script type="text/javascript" src="/voucher/tpjs/pageHomeUser.js"></script>

<link rel="stylesheet" href="../../weixinUI/style/weui.css"/>

<div class="mainElement">
<div class="user" style="height:44px;">
<img class="headimgUrl" src="" width="44px" height="44px">
<p class="nickName"></p>
</div>

<div  style="height:2px; background-color:#c0c0c0"></div>
</div>

<style type="text/css">
.user{
   padding:0 0 20px 5px;
   background-color:#f5f5f5;
}

.headimgUrl{
  border-radius:50%;
}

.nickName{
   position:absolute;
   display: inline-block;
   padding:2px 0 0 8px;
}

.narrate{
   position:absolute;
   display: inline-block;
   padding:25px 0 0 5px;
}

.navbar-inner{
background: -moz-linear-gradient(top,  #44b549 0%, #227700 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#227700), color-stop(100%,#44b549)); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top,  #44b549 0%,#227700 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top,  #44b549 0%,#227700 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top,  #44b549 0%,#227700 100%); /* IE10+ */
background: linear-gradient(top,  #44b549 0%,#227700 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#cc0956ae', endColorstr='#d9024a9e',GradientType=0 ); /* IE6-9 */
}

<!--Bootstrap修改 -->

 border: 1px solid #cccccc;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;

.dl-horizontal dt {
  float: left;
  clear: left;
  width: 120px;
  text-align: right;
}
.dl-horizontal dd {
  margin-left: 130px;
}
 
 blockquote small:before {
  content: '\2014 \00A0';
}
 
 blockquote {
  border-left: 5px solid #eeeeee;
}

body {
  margin: 0;
  font-family: Verdana,"Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 15px;
  line-height: 18px;
  color: #333333;
  background-color: #ffffff;
}


</style>



<title>Home</title>
</head>

<script type="text/javascript">

function actionClaim(value, row, index) {
    if(value==0){
    	return [
    			'<div  title="未消费">',
    			'<img src="../../img/mobileRight.jpg" width="15px" height="15px">',
    			'</div>'].join('');
    }else{
    	return [
    			'<div  title="已消费" style="color:red;font-size:10px">',
    			'<img src="../../img/mobileError.jpg" width="15px" height="15px">',
    			'</div>'].join('');
    }
    	

}


$(".default").click(function(){
	 $("#dialog1").attr("style","display:none"); 
	 $("#table").bootstrapTable({
			url : "/voucher/mobileConsume/getAllConsume.do"
		  });
});

$(".weui_btn_primary").click(function(){
	$("#msgWarn").attr("style","display:none");
	$("#msgSuccess").attr("style","display:none");
	$(".mainElement").attr("style","display:block");
	$("#table").bootstrapTable({
			url : "/voucher/mobileConsume/getAllConsume.do"
		  });
});

function actionRefund(value, row, index) {
	return [
			'<div onclick="detail(this);" title="点击退款">',
			'&nbsp;<img src="../../img/refund.jpg" width="25px" height="25px">&nbsp;',
			'</div>'].join('');
}
 
function detail(temp){
	var hang = $(temp.parentNode).parent().prevAll().length+1;  //jquery获取td所在的行和列
	   //var lie = $(temp.parentNode).prevAll().length+1;  
	   //alert("第"+hang+"行"+"	"+"第"+lie+"列"); 

		 var val=document.getElementById("table").rows[hang].cells[0]; //取得所在列的值
	     var id=val.innerHTML; 

	     $.post("../../mobilePay/refundment.do",{
	 		id:id
	 	},function(data){
	 		$('#loadingToast').hide();
	 		if(data==1){
	 			$("#dialog1").attr("style","display:none"); 
	 			$("#msgSuccess").attr("style","display:block");
	 			$(".mainElement").attr("style","display:none");
	 			$(".weui_msg_title").html("操作成功");
	 			$(".weui_msg_desc").html("退款申请已提交!");
	 		}else if(data==2){
	 			$("#dialog1").attr("style","display:none"); 
			    $("#msgWarn").attr("style","display:block");
			    $(".mainElement").attr("style","display:none");
			    $(".weui_msg_title").html("操作失败");
			    $(".weui_msg_desc").html("已消费的商品不能申请退款!");
	 		}else if(data==3){
	 			$("#dialog1").attr("style","display:none"); 
	 			$("#msgWarn").attr("style","display:block");
	 			$(".mainElement").attr("style","display:none");
	 			$(".weui_msg_title").html("消费失败");
	 			$(".weui_msg_desc").html("系统超时，请重试!");
	 		}else if(data==4){
	 			$("#dialog1").attr("style","display:none"); 
	 			$("#msgWarn").attr("style","display:block");
	 			$(".mainElement").attr("style","display:none");
	 			$(".weui_msg_title").html("消费失败");
	 			$(".weui_msg_desc").html("管理员已取消申请!");
	 		}else if(data==0){
	 			$("#dialog1").attr("style","display:none"); 
	 			$("#msgWarn").attr("style","display:block");
	 			$(".mainElement").attr("style","display:none");
	 			$(".weui_msg_title").html("操作失败");
	 			$(".weui_msg_desc").html("0元商品不能申请退款!");
	 		}
	 	});
		 
}
</script>
</html>