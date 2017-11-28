package com.voucher.manage.dao;

import java.util.Map;

import com.voucher.manage.daoModel.Position;

public interface AssetsDAO {

	public Map findAllHidden(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer updatePosition(Position position);
}
