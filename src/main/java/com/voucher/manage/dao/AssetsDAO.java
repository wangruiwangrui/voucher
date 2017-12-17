package com.voucher.manage.dao;

import java.util.Map;

import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Position;

public interface AssetsDAO {

	public Map findAllHidden(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer updatePosition(Position position);
	
	public Map findAllPosition();
	
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets);
	
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
}
