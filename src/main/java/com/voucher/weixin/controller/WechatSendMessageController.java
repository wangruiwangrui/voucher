package com.voucher.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;
import com.voucher.weixin.MessageTemplate.ChatTemplateProcessor;
import com.voucher.weixin.MessageTemplate.TemplateData;
import com.voucher.weixin.MessageTemplate.WxTemplate;

@Controller
@RequestMapping("/WechatSendMessage")
public class WechatSendMessageController {
	Integer campusId=1;
	
	private WeiXinService weixinService;

	@Autowired
	public void setAccessTokenService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}
	
	@RequestMapping("/send")
	public @ResponseBody String template() {
		String accessToken;
    	WeiXin weixin;
		
		weixin=weixinService.getCampusById(campusId);   	
		accessToken=weixin.getAccessToken();
    	
    	String openId="odos2s-S4k3qupgn-pDW42osG1Gg";
    	ChatTemplateProcessor wechatTemplate=new ChatTemplateProcessor();
    	
    	WxTemplate templateData=new WxTemplate();
    	
    	templateData.setUrl("/baidu.com");
    	templateData.setTouser(openId);
    	templateData.setTopcolor("#000000");
    	templateData.setTemplate_id("--Eq06xssiUIGTrVcQs5KIpwaoMg_uvZiEpqg5XTUKU");
    	Map<String,TemplateData> m = new HashMap<String,TemplateData>();
    	TemplateData first = new TemplateData();
    	first.setColor("#000000");
    	first.setValue("您好");
    	m.put("first", first);
    	TemplateData keyword1 = new TemplateData();
    	keyword1.setColor("#328392");
    	keyword1.setValue("000001");
    	m.put("keyword1", keyword1);
    	TemplateData keyword2 = new TemplateData();
    	keyword2.setColor("#328392");
    	keyword2.setValue("test");
    	m.put("keyword2", keyword2);
    	TemplateData keyword3 = new TemplateData();
    	keyword3.setColor("#328392");
    	keyword3.setValue("food");
    	m.put("keyword3", keyword3);
    	TemplateData remark = new TemplateData();
    	remark.setColor("#929232");
    	remark.setValue("请确认");
    	m.put("remark", remark);
    	templateData.setData(m);
    	
    	return wechatTemplate.sendTemplateMessage(accessToken, templateData,weixinService);
	}
	
}
