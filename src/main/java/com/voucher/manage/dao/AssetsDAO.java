package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Data;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;

public interface AssetsDAO {

	public Map findAllHidden(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public String getGUIDByPosition(String lng,String lat);
	
	public Integer updatePosition(Position position);
	
	public Integer updatePositionByRoomInfo(Position position);
	
	public Integer updatePositionByCheck(Position position);
	
	public Integer updatePositionByNeaten(Position position);
	
	public Integer countPositionByGUID(Position position);
	
	public Integer updateCheckPosition(Position position); 
	
	public Integer updateNeatenPosition(Position position);
	
	public Integer deletePosition(Position position);
	
	public Map findAllPosition(String manageRegion);
	
	public Map findAllCheckPosition();
	
	public Map findAllNeatenPosition();
	
	public List findPosition(Position position);
	
	public Map findHiddenByDistance(int limit,int offset,Double lng, Double lat,Double distance,String search);
	
	public Map findAssetByDistance(int limit,int offset,Double lng, Double lat,Double distance,String search);
	
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets);
	
	public Integer deleteHidden_Assets(Hidden_Assets hidden_Assets);
	
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer findNotHidden();
	
	public Integer findInHidden();
	
	public Integer findSuccessHidden();
	
	public Integer findAllAssets();
	
	public Integer findAllAssetsHidden();
	
	public String findLastHidden();
	
	public String  findIgnoreHidden();
	
	public Map<String, Object> hiddenQuery(Integer hiddenLevel);
	
	public List selectManageRegion();
	
	public ChartInfo getChartInfoByIDNo(String IDNo);           //根据身份证查找承租户
	
	public Map findChartInfo_HireListByIDNo(String IDNo);
	
	public Map findHireListByGUID(String GUID);
	
	public List findHiddenByYear();
	
	public List findAssetByYear();
	
	public List findHiddenByMonthOfYear(String year);
	
	public List findHiddenAssetsByMonthOfYear(String year);
}
