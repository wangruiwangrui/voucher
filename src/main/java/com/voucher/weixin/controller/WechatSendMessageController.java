package com.voucher.weixin.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.model.Users;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.UserService;
import com.voucher.manage.service.WeiXinService;
import com.voucher.weixin.MessageTemplate.ChatTemplateProcessor;
import com.voucher.weixin.MessageTemplate.TemplateData;
import com.voucher.weixin.MessageTemplate.WxTemplate;

@Controller
@RequestMapping("/mobile/WechatSendMessage")
public class WechatSendMessageController {
	Integer campusId=1;
	
	private WeiXinService weixinService;

	private UserService userService;
	
	@Autowired
	public void setAccessTokenService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/send")
	public @ResponseBody String template(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String title,@RequestParam String reportUser,
			@RequestParam String reportContext,@RequestParam String place,@RequestParam String url) {
		String accessToken;
    	WeiXin weixin;
		
		weixin=weixinService.getCampusById(campusId);   	
		accessToken=weixin.getAccessToken();
    	
		List userlist=userService.getUserByGuidance();
		
    	ChatTemplateProcessor wechatTemplate=new ChatTemplateProcessor();
    	
    	Iterator<Users> iterator=userlist.iterator();
    	
    	String message="";
    	
    	while (iterator.hasNext()) {
		
    	Users users=iterator.next();	
    	
    	String openid=users.getOpenId();
    	
    	WxTemplate templateData=new WxTemplate();
    	System.out.println("url="+url);
    	templateData.setUrl(url);
    	templateData.setTouser(openid);
    	templateData.setTopcolor("#000000");
    	templateData.setTemplate_id("1vQfPSl4pSvi5UnmmDhVtueutq2R1w7XYRMts294URg");
    	Map<String,TemplateData> m = new HashMap<String,TemplateData>();
    	TemplateData first = new TemplateData();
    	first.setColor("#000000");
    	first.setValue("火灾事件");
    	m.put("first", first);
    	TemplateData keyword1 = new TemplateData();
    	keyword1.setColor("#328392");
    	keyword1.setValue(title);
    	m.put("keyword1", keyword1);
    	TemplateData keyword2 = new TemplateData();
    	keyword2.setColor("#328392");
    	keyword2.setValue(reportUser);
    	m.put("keyword2", keyword2);
    	TemplateData keyword3 = new TemplateData();
    	keyword3.setColor("#328392");
    	keyword3.setValue(reportContext);
    	m.put("keyword3", keyword3);
    	TemplateData keyword4 = new TemplateData();
    	keyword4.setColor("#328392");
    	keyword4.setValue(place);
    	m.put("keyword4", keyword4);
    	TemplateData remark = new TemplateData();
    	remark.setColor("#929232");
    	remark.setValue("点击查看事件位置");
    	m.put("remark", remark);
    	templateData.setData(m);
    	
    	String s=wechatTemplate.sendTemplateMessage(accessToken, templateData,weixinService);
    	
    	message=message+"  "+users.getNickname()+"  "+s;
    	
    	}
    	
    	return message;
    	
	}
	
}
