package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.IUserDAO;
import com.voucher.manage.daoModel.Users;

@Controller
@RequestMapping("/house")
public class HouseController {

	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	@RequestMapping("/get")
	public @ResponseBody Map<String, Object> demo6(){
		Map<String, Object> map = new HashMap<String, Object>();
		
    	IUserDAO dao=(IUserDAO) applicationContext.getBean("dao");
        List<Users> users=dao.findAll();
        
        map.put("rows", users);
        map.put("total", users.size());
              
        return map;
	}
}
