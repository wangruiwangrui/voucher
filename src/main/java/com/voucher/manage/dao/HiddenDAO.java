package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Level;
import com.voucher.manage.daoModel.Assets.Hidden_Type;
import com.voucher.manage.daoModel.Assets.Hidden_User;

public interface HiddenDAO {

	public Integer InsertIntoHiddenData(String GUID,String NAME,String TYPE,String uri);
	
	public Map<String,Object> selectAllHiddenDate(String GUID);
	
	public Map<String, Object> selectAllHidden(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer insertIntoHidden(Hidden hidden);
	
	public Integer updateHidden(Hidden hidden);
	
	public Integer deleteHidden(Hidden hidden);
	
	public List<Hidden_Level> setctAllHiddenLevel();
	
	public Integer insertHiddenLevel(Hidden_Level hidden_level);
	
	public Integer deleteHiddenLevel(Hidden_Level hidden_level);
	
	public List<Hidden_Type> selectAllHiddenType();
	
	public Integer insertHiddenType(Hidden_Type hidden_Type);
	
	public Integer deleteHiddenType(Hidden_Type hidden_Type);
	
	public Map<String, Object> selectAllHidden_Jion(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public List<Hidden_User> selectAllHiddenUser();
	
	public Integer insertHiddenUser(Hidden_User hidden_User);
	
	public Integer deleteHiddenUser(Hidden_User hidden_User);
	
}
