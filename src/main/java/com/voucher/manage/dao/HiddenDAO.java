package com.voucher.manage.dao;

import java.util.Map;

import com.voucher.manage.daoModel.Hidden;

public interface HiddenDAO {

	public Map<String, Object> selectAllHidden(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer insertIntoHidden(Hidden hidden);
	
	public Integer updateHidden(Hidden hidden);
	
	public Integer deleteHidden(Hidden hidden);
}
