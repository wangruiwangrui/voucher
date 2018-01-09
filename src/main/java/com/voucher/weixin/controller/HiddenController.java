package com.voucher.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/mobile/hidden")
public class HiddenController {

	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	@RequestMapping("/selectAllHidden")
	public @ResponseBody Map selectAllHidden(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("Hidden.name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHidden_Jion(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.hiddenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectHiddenByGuid")
	public @ResponseBody Map selectHiddenByGuid(@RequestParam String guid,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("Hidden.GUID = ", guid);
		
		Map map=hiddenDAO.selectAllHidden_Jion(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.hiddenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		Hidden_Join hidden_Join=(Hidden_Join) list.get(0);
		
		result.put("hidden", hidden_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
}
