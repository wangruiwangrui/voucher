package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;

@Controller
@RequestMapping("/roominfo")
public class RoomInfoController {

	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	@RequestMapping("/getAll")
	public @ResponseBody Map<String, Object> RoomInfo(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		
			
		if(sort!=null&&sort.equals("buildArea")){
			sort="BuildArea";
		}
		
		if(sort!=null&&sort.equals("address")){
			sort="Address";
		}
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
	
		
		

		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";              
		}		
		System.out.println("roominfocontroller sort="+sort+"   order="+order);

		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
				order,search);
		System.out.println("roominfocontroller sort="+sort+"   order="+order);
		map.put("rows", roomInfos);
	
		
		
		
		Integer total=roomInfoDao.getRoomInfoCount(search);
		
        map.put("total", total);
		
		return map;
	}
}
