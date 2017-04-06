<!DOCTYPE html>
<html>
<head lang="zh-CN">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${articles.title}</title>
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<link rel='stylesheet' type='text/css' href='/voucher/css/templetcss/detail.css'>
<link rel='stylesheet' type='text/css' href='/voucher/css/templetcss/other.css'>
<link rel="stylesheet" href="/voucher/weixinUI/style/weui.css"/>

<script type="text/javascript">
  var foodId=${articles.id};
  var campusId=${articles.campusId};
  var campusAdmin=${articles.campusAdmin};
  var redirectUrl="${articles.redirectUrl}";
</script>

<script type='text/javascript' src="/voucher/js/jquery.js"></script>
<script type="text/javascript" src="/voucher/tpjs/userInfo.js"></script>

</head>

  <style type="text/css">
        *{margin:0; padding:0}
        table{border:1px dashed #B9B9DD;font-size:12pt}
        td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
   </style>

<!-- 微信UI -->
 <div id="toast" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_toast"></i>
        <p class="weui_toast_content">购买成功</p>
    </div>
 </div>

 <div class="weui_msg" id="msgWarn" style="display: none;">
    <div class="weui_icon_area"><i class="weui_icon_warn weui_icon_msg"></i></div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title"></h2>
        <p class="weui_msg_desc"></p>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <a href="javascript:;" class="weui_btn weui_btn_primary">确定</a>
  
        </p>
    </div>
 </div>

<body class="good-detail-page" mon="position=detail">


<article mon="deal_id=10096720" class="mainBody">

<section id="j-detail-image" class="detail-image-wrap">
<div class="detail-image">
<img src="${articles.imgUrl}" alt="${articles.title}">
</a>

<div class="album collect-icon" mon="area=deal_pic&amp;element=collect">
点击查看图文详情
</div>
</div>
</div>
<div id="j-detail-title" class="title-wrap"><h3 class="title">${articles.title}</h3>
<#if articles.content??>
<p class="desc">${articles.content}</p></div>
</#if>
</section>


<section class="panel-notice">
<div class="hd">消费提示</div>

<div class="struct-data">
<dl>
<dt>有效期</dt>
<dd>${articles.starttime} 至 ${articles.endtime}</dd>


<#if articles.useTime??>
<dt>使用时间</dt>
<dd>${articles.useTime}</dd>
</#if>

<#if articles.bespoke??>
<dt>预约提醒</dt>
<dd>${articles.bespoke}</dd>
</#if>

<#if articles.rule??>
<dt>使用规则</dt>
<dd>${articles.rule}</dd>
</#if>

<#if articles.cue??>
<dt>温馨提示</dt>
<dd>${articles.cue}</dd>
</#if>


</dl>
</div>
</section>


<section class="buy-btn-wrap" id="j-buy-segment" mon="area=topBuy&amp;action=click">

<div class="buy-segment">

<span style="padding-left:15px">
<em>剩余<span id="count"></span>件</em>
</span>

<div class="buy-wrapper   has-newcustom">
<div class="privilege-btn">
<a href="javascript:void(0);" id="buybtn"  class="buy-btn">
<span class="tip">活动价</span>

<span class="privilege"><em class="price">${articles.price}</em></span>

<span class="text">立即抢购</span>
</a>
</div>

</div>
</div>
</section>

</article>

</body>

<script type="text/javascript" src="/voucher/tpjs/purchase.js"></script>
<script type="text/javascript">
$.post("../mobileService/getFoodCountById.do", {
                 foodId:foodId
			}, function(text) {
			   $("#count").html(text);
			});
</script>			
</html>