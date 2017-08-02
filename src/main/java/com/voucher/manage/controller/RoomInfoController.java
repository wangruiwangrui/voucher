package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
		String type,campusAdmin;
		Integer campusId=0;

		if(sort!=null&&sort.equals("subscribeTime")){
			sort="subscribe_time";
		}
		
		if(sort!=null&&sort.equals("totalAmount")){
			sort="total_amount";
		}
		
		if(sort!=null&&sort.equals("defaultAddress")){
			sort="default_address";
		}
		
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="id";
			order="desc";
		}
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}		
		
		Cookie[] cookies = request.getCookies();  
		if(cookies!=null){
			for(Cookie i:cookies){
				if(i.getName().equalsIgnoreCase("campusId"))
					campusId=Integer.parseInt(i.getValue());
			}
		}
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(campusId,limit,offset,sort,
				order,search);
		
		map.put("rows", roomInfos);
		
		Integer total=roomInfoDao.getRoomInfoCount(campusId, search);
		
        map.put("total", total);
		
		return map;
	}
}
