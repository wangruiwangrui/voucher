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
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;
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
		
		Map result=new HashMap<>();
		
		Hidden_Join hidden_Join=(Hidden_Join) list.get(0);
		
		List fileBytes=mobileDao.allHiddenImageByGUID(request, hidden_Join);
		
		result.put("hidden", hidden_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectAllCheck")
	public @ResponseBody Map selectAllCheck(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("check_name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.checkImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden_Check", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectCheckByCheckId")
	public @ResponseBody Map selectCheckByCheckId(@RequestParam String check_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Hidden_Check].check_id = ", check_id);
		
		Map map=hiddenDAO.selectAllHiddenCheck(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Check_Join hidden_Check_Join=(Hidden_Check_Join) list.get(0);
		
		List fileBytes=mobileDao.allCheckImageByGUID(request, hidden_Check_Join);
		
		result.put("hidden_Check", hidden_Check_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	
	@RequestMapping("/selectAllNeaten")
	public @ResponseBody Map selectAllNeaten(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("[Hidden_Neaten].neaten_name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHiddenNeaten(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.neatenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden_Neaten", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectNeatenByNeatenId")
	public @ResponseBody Map selectNeatenByNeatenId(@RequestParam String neaten_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Hidden_Neaten].neaten_id = ", neaten_id);
		
		Map map=hiddenDAO.selectAllHiddenNeaten(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Neaten_Join hidden_Neaten_Join=(Hidden_Neaten_Join) list.get(0);
		
		List fileBytes=mobileDao.allNeatenImageByGUID(request, hidden_Neaten_Join);
		
		result.put("hidden_Neaten", hidden_Neaten_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	
}
