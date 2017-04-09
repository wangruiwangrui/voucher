package com.voucher.weixin.dispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.voucher.weixin.message.resp.Article;
import com.voucher.weixin.message.resp.Image;
import com.voucher.weixin.message.resp.ImageMessage;
import com.voucher.weixin.message.resp.NewsMessage;
import com.voucher.weixin.message.resp.TextMessage;
import com.voucher.weixin.util.HttpPostUploadUtil;
import com.voucher.weixin.util.MessageUtil;

public class EventDispatcher {
    public static String processEvent(Map<String, String> map) {
        	String openid = map.get("FromUserName"); // 用户 openid
        	String mpid = map.get("ToUserName"); // 公众号原始 ID
        	ImageMessage imgmsg = new ImageMessage();

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
        	    System.out.println("==============这是关注事件！");
        	   /*
        	    Image img = new Image();
        	    String access_token=map.get("access_token");
        	    HttpPostUploadUtil util=new HttpPostUploadUtil(access_token);
        	    String filepath="C:\\Users\\WangJing\\Desktop\\aaaaaaaaaaaa\\logo.png";  
        	    Map<String, String> textMap = new HashMap<String, String>();  
        	    textMap.put("name", "testname");  
        	    Map<String, String> fileMap = new HashMap<String, String>();  
        	    fileMap.put("userfile", filepath); 
        	    String mediaidrs = util.formUpload(textMap, fileMap);
        	    System.out.println(mediaidrs);
        	    JSONObject jsonObject=JSONObject.parseObject(mediaidrs);
        	    String mediaid=jsonObject.getString("media_id");
        	    img.setMediaId(mediaid);
        	    imgmsg.setImage(img);
        	    System.out.println("meidaid="+mediaid);
        	    return MessageUtil.imageMessageToXml(imgmsg);
        	    */
        	    //普通文本消息

                TextMessage txtmsg=new TextMessage();
                txtmsg.setToUserName(openid);
                txtmsg.setFromUserName(mpid);
                txtmsg.setCreateTime(new Date().getTime());
                txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                String nickName=map.get("nickName");
                
           
                    txtmsg.setContent("你好，"+nickName);
                    return MessageUtil.textMessageToXml(txtmsg);

        	
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            System.out.println("==============这是取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            System.out.println("==============这是扫描二维码事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            System.out.println("==============这是位置上报事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
            NewsMessage newmsg=new NewsMessage();
            newmsg.setToUserName(openid);
            newmsg.setFromUserName(mpid);
            newmsg.setCreateTime(new Date().getTime());
            newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            Article article=new Article();
            article.setDescription("这是图文消息 1"); //图文消息的描述
            article.setPicUrl("http://res.cuiyongzhi.com/2016/03/201603086749_6850.png"); //图文消息图片地址
            article.setTitle("图文消息 1");  //图文消息标题
            article.setUrl("http://www.cuiyongzhi.com");  //图文 url 链接
            List<Article> list=new ArrayList<Article>();
            list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
            newmsg.setArticleCount(list.size());
            newmsg.setArticles(list);
            return MessageUtil.newsMessageToXml(newmsg);

        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单 View 事件
            System.out.println("==============这是自定义菜单 View 事件！");
        }

        return null;
    }
}
