package com.voucher.manage.service;

import java.util.List;
import java.util.Map;

import com.voucher.manage.model.WeiXin;

public interface WeiXinService {

	WeiXin getAccessTokenById(Integer campusId);
	
	List<WeiXin> getAllCampusById(Integer cityId);
	
	WeiXin getCampusById(Integer campusId);
	
	Integer insertIntoCampus(Map<String, Object> paramMap);
	
	WeiXin getByCampusIds(Integer campusId);
	
	Integer updateCampusById(Map<String, Object> paramMap);
	
	Integer updateHomePageByCampusId(Map<String, Object> paramMap);
	
	Integer getCampusIdByUserName(String userName);
	
}
