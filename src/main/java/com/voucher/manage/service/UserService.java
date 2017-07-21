package com.voucher.manage.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.model.Users;
import com.voucher.weixin.base.SNSUserInfo;


public interface UserService {

	
	List<Users> getAllFullUser(Integer campusId,Integer limit, Integer offset, String sort,String order,String search);
	
	Users getUserInfoById(Integer campusId,String openId);
	
	Integer getUserCount(String campusAdmin,Integer campusId,String search);
	
	Integer getUserFullCount(Integer campusId,String search);
	
	Integer getOpenId(Integer campusId,String openId);
	
	Integer insertUser(SNSUserInfo snsUserInfo);
	
	Integer upUserByOpenId(SNSUserInfo snsUserInfo);
	
	Integer upsubscribeByOpenId(Map<String, Object> paramterMap);
	
	int upAtionFormatter(Map<String, Object> paramterMap);

}