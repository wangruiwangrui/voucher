package com.voucher.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetCheckDAO;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.FinanceDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.service.UserService;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/mobile/finance")
public class AssetFinanceController {

	ApplicationContext applicationContext=new Connect().get();
	
	FinanceDAO financeDAO=(FinanceDAO) applicationContext.getBean("financeDao");
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	AssetCheckDAO assetCheckDAO=(AssetCheckDAO) applicationContext.getBean("assetCheckdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	private UserService userService;
	
	@RequestMapping("/getoverdueChartInfo")
	public @ResponseBody Map<String, Object> overdueChartInfo(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		
		if(sort!=null&&!sort.equals("")){

		}else{
			sort="ConcludeDate";
		}
		
		if(order!=null&&!order.equals("")){
		}else{
			order="asc";
		}
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.equals("")){
			
			search="%"+search+"%";
			
			searchMap.put("RoomAddress like ", search);
		
			searchMap.put("Charter like ", search);
		
			searchMap.put("IDNo like ", search);
		
		}
		
		return financeDAO.findOverdueChartInfo( limit, offset, sort, order, searchMap);
		
	}
	
}
