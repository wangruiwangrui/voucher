package com.voucher.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;



@Controller
@RequestMapping("/weixinset")
public class WeinXinSetController {

private WeiXinService weixinService;
	
	@Autowired
	public void setAccessTokenService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}
	
	@RequestMapping("getCampusInfo")
	public @ResponseBody List<WeiXin>
	getCampusInfo(HttpServletRequest request){		
		String campusAdmin;
		Short type;
        Integer  campusId,cityId;
		List<WeiXin> weixins=new ArrayList<>();
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		type=(Short) session.getAttribute("type");
		cityId=(Integer) session.getAttribute("cityId");
	
		if(type==0){
		   weixins=weixinService.getAllCampusById(cityId);
		 }
		return weixins;
		
	}
	
	@RequestMapping("insertIntoCampus")
	public @ResponseBody 
	Integer insetIntoCampus(HttpServletRequest request , @RequestParam Integer campusId,@RequestParam String campusName
			,@RequestParam String customService,@RequestParam String appId , @RequestParam String appSecret) {
		Map<String, Object> paramMap=new HashMap<>();
		Integer  cityId , flag ;
		WeiXin be=null;
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		cityId=(Integer) session.getAttribute("cityId");
		
		Short type=(Short)session.getAttribute("type");
		if(type==1){
			return 0;
		}
				
		paramMap.put("campusName", campusName);
		paramMap.put("cityId", cityId);
		paramMap.put("customService", customService);
		paramMap.put("appId", appId);
		paramMap.put("appSecret", appSecret);
		
		if(campusId!=null){
		   be=weixinService.getByCampusIds(campusId);
		}
		System.out.println("be="+be+ "         campusid="+campusId);
		if(be==null){
		 flag=weixinService.insertIntoCampus(paramMap);
		}else{
		  paramMap.put("campusId", campusId);
		  flag=weixinService.updateCampusById(paramMap);
		}
		return flag;
	}
	
}
