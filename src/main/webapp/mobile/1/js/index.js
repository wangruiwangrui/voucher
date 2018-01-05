var campusId=1; //公众号id

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
   }


var code=getQueryString("code");
var state=getQueryString("state");

$.get("/voucher/oauth/test.do", { 
	 campusId:campusId
  }, function(data) {
	//   alert("data="+data);

 if(data=="false"){
	//   alert("false");
     if(code!=null){   	  
          $.get("/voucher/oauth/getUserInfo.do", {
             code:code,
             state:state,
              campusId:campusId
           }, function(text) {

        	  var obj = $.parseJSON(text);
           
        	 $("#brand").html(obj.campusName);
        	 $(".headimgUrl").attr("src",obj.headimgUrl);
        	 $(".nickName").html(obj.nickName);
        	  
      	    
        	 
        	url=document.location.toString();
        	
        	$.ajax({
			     url : "../../wechat/sign.do",
			     data : {
			    	 campusId:campusId,
			    	 url:url
			     },
			     async: false,
			     type : "GET",
			     success : function(data) {
			    	 var ticket=JSON.parse(data);

			    	 /*
		      	      * 此处需要两次执行相同的函数，否则回调后不能执行以下函数
		      	      */ 
			    	 
			    	 wx.config({
			    		  debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				      		appId: ticket.appId,
				      		timestamp: ticket.timestamp,
				      		nonceStr: ticket.nonceStr,
				      		signature: ticket.signature,
				      		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
				                            'onMenuShareAppMessage', 'onMenuShareQQ',
				                            'onMenuShareWeibo', 'hideMenuItems',
				                            'showMenuItems', 'hideAllNonBaseMenuItem',
				                            'showAllNonBaseMenuItem', 'translateVoice',
				                            'startRecord', 'stopRecord', 'onRecordEnd',
				                            'playVoice', 'pauseVoice', 'stopVoice',
				                            'uploadVoice', 'downloadVoice', 'chooseImage',
				                            'previewImage', 'uploadImage', 'downloadImage',
				                            'getNetworkType', 'openLocation', 'getLocation',
				                            'hideOptionMenu', 'showOptionMenu', 'closeWindow',
				                            'scanQRCode', 'chooseWXPay',
				                            'openProductSpecificView', 'addCard', 'chooseCard',
				                            'openCard' ]
				      	});
			    	 
			    	 /*
		      	      * 此处需要两次执行相同的函数，否则回调后不能执行以下函数
		      	      */ 
			    	 wx.ready(function () {
			    		 document.querySelector('#hiddentrouble').onclick =function(){
			    			 location.href="hidden/hiddenSearch.html";
			    		 }
			    		 
			    		 document.querySelector('#report').onclick =function(){
			    			 location.href="safety/a.html";
			    		 }
			    		 
			    		 document.querySelector('#userSetting').onclick =function(){
			    			 location.href="userSetting.html";
			    		 }
			    		 
			    		// 5 图片接口
			        	  // 5.1 拍照、本地选图
			        	  var images = {
			        	    localId: [],
			        	    serverId: []
			        	  };
			    		 document.querySelector('#photo').onclick = function () {
			    			 wx.chooseImage({
				        	      success: function (res) {
				        	        images.localId = res.localIds;
				        	        alert('已选择 ' + res.localIds.length + ' 张图片');
				        	        
				        	        var i = 0, length = images.localId.length;
					        	    images.serverId = [];
					        	    function upload() {
					        	      wx.uploadImage({
					        	        localId: images.localId[i].toString(),
					        	        isShowProgressTips: images.localId[i].toString(),
					        	        success: function (res) {
					        	          i++;
					        	          alert('已上传：' + i + '/' + length);
					        	        //返回图片的服务器端ID res.serverId,然后调用wxImgCallback函数进行下载图片操作
			                                wxImgCallback(res.serverId);
					        	       //   images.serverId.push(res.serverId);
					        	          if (i < length) {
					        	            upload();
					        	          }
					        	        },
					        	        fail: function (res) {
					        	          alert(JSON.stringify(res));
					        	        }
					        	      });
					        	    }
					        	    upload();
				        	      }
				        	    });
			    		 }
			    		 
			    		 function wxImgCallback(serverId) {
			        		    //将serverId传给wx_upload.php的upload方法
			        		    var url = "../../mobile/file/upload.do";
			        		    $.get(url,{
			        		    	campusId:campusId,
			        		    	serverId:serverId
			        		    }, function(data){
			        		    	alert(data);
			        		        if (data.code == 0) {
			        		            alert(data.msg);
			        		        } else if (data.code == 1) {
			        		            //存储到服务器成功后的处理
			        		            //
			        		        }
			        		    });
			        		}
			    		 
			    		 document.querySelector('#map').onclick = function () {
	
			    	            // 2. 分享接口
			    	            wx.getLocation({
			    	               success : function(res) {
			    	                    // alert(JSON.stringify(res));
			    	                    var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
			    	                    // $("#latitude").val(latitude);
			    	                    var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
			    	                    // $("#longitude").val(longitude);
			    	                    var speed = res.speed; // 速度，以米/每秒计
			    	                    // $("#speed").val(speed);
			    	                    var accuracy = res.accuracy; // 位置精度
			    	                    // $("#accuracy").val(accuracy);
			    	                    location.href="map.html?latitude="+latitude+"&longitude="+longitude;
			    	                },
			    	                cancel : function(res) {
			    	                    alert('用户拒绝授权获取地理位置');
			    	                }
			    	            });
			    		 }
			    		 
			    	// 2. 分享接口
		        	  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
		        	    wx.onMenuShareAppMessage({
		        	      title: '互联网之子',
		        	      desc: '在长大的过程中，我才慢慢发现，我身边的所有事，别人跟我说的所有事，那些所谓本来如此，注定如此的事，它们其实没有非得如此，事情是可以改变的。更重要的是，有些事既然错了，那就该做出改变。',
		        	      link: 'http://movie.douban.com/subject/25785114/',
		        	      imgUrl: 'http://img3.douban.com/view/movie_poster_cover/spst/public/p2166127561.jpg',
		        	      trigger: function (res) {
		        	        alert('用户点击发送给朋友');
		        	      },
		        	      success: function (res) {
		        	        alert('已分享');
		        	      },
		        	      cancel: function (res) {
		        	        alert('已取消');
		        	      },
		        	      fail: function (res) {
		        	        alert(JSON.stringify(res));
		        	      }
		        	    });

		        	  
		        	  
			       }); //wx.ready
		        	  
			     }
        	});
           
        });
     }else{
  	   location.href=redirectUrl;
     }
    }else{
    	$.get("/voucher/oauth/getUserInfoByOpenId.do",{
 		   campusId:campusId
 	   },function(text) {
       	  var obj = $.parseJSON(text);

       	 $("#brand").html(obj.campusName);
       	 $(".headimgUrl").attr("src",obj.headimgUrl);
     	 $(".nickName").html(obj.nickName);
       	  

 			 url=document.location.toString();

 			 $.ajax({
 			     url : "../../wechat/sign.do",
 			     data : {
 			    	 campusId:campusId,
 			    	 url:url
 			     },
 			     async: false,
 			     type : "GET",
 			     success : function(data) {
 			    	 var ticket=JSON.parse(data);
 		       	     /*
 		     	      * 此处需要两次执行相同的函数，否则返回ture时不能执行以下函数
 		     	      */  
 		       	          
 			    	 
 			    	 wx.config({
 			    		debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
 			      		appId: ticket.appId,
 			      		timestamp: ticket.timestamp,
 			      		nonceStr: ticket.nonceStr,
 			      		signature: ticket.signature,
 			      		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
 			                            'onMenuShareAppMessage', 'onMenuShareQQ',
 			                            'onMenuShareWeibo', 'hideMenuItems',
 			                            'showMenuItems', 'hideAllNonBaseMenuItem',
 			                            'showAllNonBaseMenuItem', 'translateVoice',
 			                            'startRecord', 'stopRecord', 'onRecordEnd',
 			                            'playVoice', 'pauseVoice', 'stopVoice',
 			                            'uploadVoice', 'downloadVoice', 'chooseImage',
 			                            'previewImage', 'uploadImage', 'downloadImage',
 			                            'getNetworkType', 'openLocation', 'getLocation',
 			                            'hideOptionMenu', 'showOptionMenu', 'closeWindow',
 			                            'scanQRCode', 'chooseWXPay',
 			                            'openProductSpecificView', 'addCard', 'chooseCard',
 			                            'openCard' ]
 			      	});
 			    	 
 			    	/*
		      	      * 此处需要两次执行相同的函数，否则回调后不能执行以下函数
		      	      */ 
 			    	 wx.ready(function () {
 			  
 			    		document.querySelector('#hiddentrouble').onclick =function(){
			    			 location.href="hidden/hiddenSearch.html";
			    		 }
 			    		 
 			    		document.querySelector('#report').onclick =function(){
			    			 location.href="safety/a.html";
			    		 }
 			    		 
 			    		 
 			    		document.querySelector('#userSetting').onclick =function(){
			    			 location.href="userSetting.html";
			    		 }
 			    		
 			    	// 5 图片接口
			        	  // 5.1 拍照、本地选图
			        	  var images = {
			        	    localId: [],
			        	    serverId: []
			        	  };
			    		 document.querySelector('#photo').onclick = function () {
			    			 wx.chooseImage({
				        	      success: function (res) {
				        	        images.localId = res.localIds;
				        	        alert('已选择 ' + res.localIds.length + ' 张图片');
				        	        
				        	        var i = 0, length = images.localId.length;
					        	    images.serverId = [];
					        	    function upload() {
					        	      wx.uploadImage({
					        	        localId: images.localId[i].toString(),
					        	        isShowProgressTips: images.localId[i].toString(),
					        	        success: function (res) {
					        	          i++;
					        	          alert('已上传：' + i + '/' + length);
					        	        //返回图片的服务器端ID res.serverId,然后调用wxImgCallback函数进行下载图片操作
			                                wxImgCallback(res.serverId);
					        	       //   images.serverId.push(res.serverId);
					        	          if (i < length) {
					        	            upload();
					        	          }
					        	        },
					        	        fail: function (res) {
					        	          alert(JSON.stringify(res));
					        	        }
					        	      });
					        	    }
					        	    upload();
				        	      }
				        	    });
			    		 }
			    		 
			    		 function wxImgCallback(serverId) {
			        		    //将serverId传给wx_upload.php的upload方法
			        		    var url = "../../mobile/file/upload.do";
			        		    $.get(url,{
			        		    	campusId:campusId,
			        		    	serverId:serverId
			        		    }, function(data){
			        		    	alert(data);
			        		        if (data.code == 0) {
			        		            alert(data.msg);
			        		        } else if (data.code == 1) {
			        		            //存储到服务器成功后的处理
			        		            //
			        		        }
			        		    });
			        		}	
 			    		
 			    		
                   document.querySelector('#map').onclick = function () {
                       // 2. 分享接口
                       wx.getLocation({
                            success : function(res) {
                               // alert(JSON.stringify(res));
                               var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                               // $("#latitude").val(latitude);
                               var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                               // $("#longitude").val(longitude);
                               var speed = res.speed; // 速度，以米/每秒计
                               // $("#speed").val(speed);
                               var accuracy = res.accuracy; // 位置精度
                               // $("#accuracy").val(accuracy);
                               location.href="map.html?latitude="+latitude+"&longitude="+longitude;
                           },
                           cancel : function(res) {
                               alert('用户拒绝授权获取地理位置');
                           }
                       });
			    	 } 
 			    		 
 			    	 // 2. 分享接口
		        	  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
		        	    wx.onMenuShareAppMessage({
		        	      title: '互联网之子',
		        	      desc: '在长大的过程中，我才慢慢发现，我身边的所有事，别人跟我说的所有事，那些所谓本来如此，注定如此的事，它们其实没有非得如此，事情是可以改变的。更重要的是，有些事既然错了，那就该做出改变。',
		        	      link: 'http://movie.douban.com/subject/25785114/',
		        	      imgUrl: 'http://img3.douban.com/view/movie_poster_cover/spst/public/p2166127561.jpg',
		        	      trigger: function (res) {
		        	        alert('用户点击发送给朋友');
		        	      },
		        	      success: function (res) {
		        	        alert('已分享');
		        	      },
		        	      cancel: function (res) {
		        	        alert('已取消');
		        	      },
		        	      fail: function (res) {
		        	        alert(JSON.stringify(res));
		        	      }
		        	    });

		        	  
		        	  
		        	  
 			    	 }); //wx.ready
 			     }
 			 });
       });
    }
});
