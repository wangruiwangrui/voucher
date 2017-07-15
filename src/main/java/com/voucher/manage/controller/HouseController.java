package com.voucher.manage.controller;

import java.util.Iterator;
import java.util.List;

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
	public @ResponseBody List<Users> demo6(){
		  
    	IUserDAO dao=(IUserDAO) applicationContext.getBean("dao");
        List<Users> users=dao.findAll();
        return users;
	}
}
