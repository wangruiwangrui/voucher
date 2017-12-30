package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Data;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;

public interface AssetsDAO {

	public Map findAllHidden(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer updatePosition(Position position);
	
	public Integer deletePosition(Position position);
	
	public Map findAllPosition();
	
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets);
	
	public Integer deleteHidden_Assets(Hidden_Assets hidden_Assets);
	
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer findNotHidden();
	
	public Integer findInHidden();
	
	public String findLastHidden();
	
	public String  findIgnoreHidden();
	
	public Map<String, Object> hiddenQuery(Integer hiddenLevel);
}
